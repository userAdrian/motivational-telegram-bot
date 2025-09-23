package it.vrad.motivational.telegram.bot.core.service.message.impl;

import it.vrad.motivational.telegram.bot.core.model.constants.MessageConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.InitialPageButton;
import it.vrad.motivational.telegram.bot.core.service.message.AbstractMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.InitialMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.User;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/**
 * Implementation of {@link InitialMessageService}
 */
@Service
public class InitialMessageServiceImpl extends AbstractMessageService implements InitialMessageService {

    public InitialMessageServiceImpl(
            MessageTemplate messageTemplate,
            TelegramFileDao telegramFileDao,
            PageConfigurationService pageConfigurationService
    ) {
        super(messageTemplate, telegramFileDao, pageConfigurationService);
    }

    /**
     * {@inheritDoc}
     *
     * @param user       {@inheritDoc}
     * @param userFromDB {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto generateInitialMessageDto(User user, UserDto userFromDB) {
        return buildMessageDto(
                PageConstants.InitialPage.MESSAGE_PHOTO_CLASSPATH,
                composeTextMessage(user),
                buildInlineKeyboardMarkup(MessageConstants.DEFAULT_LOCALE, InitialPageButton.values(), userFromDB)
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param sentMessage        {@inheritDoc}
     * @param telegramFileIdSent {@inheritDoc}
     */
    @Override
    public void persistTelegramFile(Message sentMessage, String telegramFileIdSent) {
        persistTelegramFile(PageConstants.InitialPage.MESSAGE_PHOTO_CLASSPATH, sentMessage, telegramFileIdSent);
    }

    private Supplier<String> composeTextMessage(User user) {
        return () -> messageTemplate.generateInitialMessage(MessageConstants.DEFAULT_LOCALE, user.getFirstName());
    }
}
