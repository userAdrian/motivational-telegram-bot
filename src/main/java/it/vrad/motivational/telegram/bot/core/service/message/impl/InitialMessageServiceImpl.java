package it.vrad.motivational.telegram.bot.core.service.message.impl;

import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.InitialPage;
import it.vrad.motivational.telegram.bot.core.model.factory.PersistenceDtoFactory;
import it.vrad.motivational.telegram.bot.core.service.message.AbstractMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.InitialMessageService;
import it.vrad.motivational.telegram.bot.core.model.constants.MessageConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.UserDao;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Chat;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.User;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;


/**
 * Implementation of {@link InitialMessageService}
 * for initial message operations.
 */
@Service
@Slf4j
public class InitialMessageServiceImpl extends AbstractMessageService implements InitialMessageService {
    private final UserDao userDao;

    public InitialMessageServiceImpl(
            MessageTemplate messageTemplate,
            TelegramFileDao telegramFileDao,
            PageConfigurationService pageConfigurationService,
            TelegramIntegrationApi telegramIntegrationApi,
            UserService userService,
            UserDao userDao
    ) {
        super(messageTemplate, telegramFileDao, pageConfigurationService, telegramIntegrationApi, userService);
        this.userDao = userDao;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto processInitialMessage(IncomingMessageContext incomingMessageContext) {
        Objects.requireNonNull(incomingMessageContext);
        Message message = incomingMessageContext.getMessageSent();

        // Save or validate the user based on the incoming message
        UserDto userFromDB = saveOrValidateUser(message);

        // Generate the welcome message DTO
        MessageDto messageDto = generateWelcomeMessageDto(message.getFrom(), userFromDB);

        // Send the welcome photo message
        Message sentMessage = telegramIntegrationApi.sendPhoto(TelegramApiRequestUtility.getSendPhotoRequest(message, messageDto));

        // Persist Telegram file info after sending
        persistTelegramFile(PageConstants.InitialPage.MESSAGE_PHOTO_CLASSPATH, sentMessage, messageDto.getTelegramFileId());

        return null;
    }

    /**
     * Saves the user if not present in the database, otherwise validates the existing user.
     *
     * @param message the Telegram message
     * @return the saved or validated UserDto
     */
    private UserDto saveOrValidateUser(Message message) {
        Long telegramUserId = MessageUtility.getUserId(message);

        Optional<UserDto> userOpt = userDao.findUserByTelegramId(telegramUserId);
        return userOpt.map(UserDtoUtility::validateUser)
                .orElseGet(() -> saveUser(telegramUserId, message.getChat()));
    }

    private UserDto saveUser(Long telegramUserId, Chat chat) {
        return userDao.saveUser(PersistenceDtoFactory.buildInitialUserDto(telegramUserId, chat));
    }

    /**
     * {@inheritDoc}
     *
     * @param user       {@inheritDoc}
     * @param userFromDB {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto generateWelcomeMessageDto(User user, UserDto userFromDB) {
        return buildMessageDto(
                PageConstants.InitialPage.MESSAGE_PHOTO_CLASSPATH,
                composeTextMessage(user),
                buildInlineKeyboardMarkup(MessageConstants.DEFAULT_LOCALE, InitialPage.values(), userFromDB)
        );
    }

    private Supplier<String> composeTextMessage(User user) {
        return () -> messageTemplate.generateInitialMessage(MessageConstants.DEFAULT_LOCALE, user.getFirstName());
    }

}
