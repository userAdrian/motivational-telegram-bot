package it.vrad.motivational.telegram.bot.core.service.message.impl;

import it.vrad.motivational.telegram.bot.config.properties.CommandProperties;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.AdminPage;
import it.vrad.motivational.telegram.bot.core.service.message.AbstractMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.AdminMessageService;
import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.MessageConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.infrastructure.cache.CacheService;
import it.vrad.motivational.telegram.bot.infrastructure.cache.factory.StepDetailFactory;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.infrastructure.util.FilesUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Document;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;


/**
 * Implementation of {@link AdminMessageService}
 * for admin message operations.
 */
@Service
public class AdminMessageServiceImpl extends AbstractMessageService implements AdminMessageService {
    private final CacheService cacheService;
    private final PhraseService phraseService;
    private final CommandProperties commandProperties;

    public AdminMessageServiceImpl(
            MessageTemplate messageTemplate,
            TelegramFileDao telegramFileDao,
            PageConfigurationService pageConfigurationService,
            TelegramIntegrationApi telegramIntegrationApi,
            UserService userService,
            CacheService cacheService,
            PhraseService phraseService, CommandProperties commandProperties
    ) {
        super(messageTemplate, telegramFileDao, pageConfigurationService, telegramIntegrationApi, userService);
        this.cacheService = cacheService;
        this.phraseService = phraseService;
        this.commandProperties = commandProperties;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto forwardToAdminPage(IncomingMessageContext incomingMessageContext) {
        Objects.requireNonNull(incomingMessageContext);
        Message message = incomingMessageContext.getMessageSent();

        // Retrieve user
        UserDto user = getUserDto(incomingMessageContext, message);

        // Build and send admin page message
        MessageDto messageDto = generateMessageDto(user);
        Message sentMessage = telegramIntegrationApi.editMessageMedia(TelegramApiRequestUtility.getEditMessageMediaPhotoRequest(message, messageDto));

        // Persist Telegram file info after sending
        persistTelegramFile(PageConstants.AdminPage.MESSAGE_PHOTO_CLASSPATH, sentMessage, messageDto.getTelegramFileId());

        return null;
    }

    private MessageDto generateMessageDto(UserDto user) {
        return buildMessageDto(
                PageConstants.AdminPage.MESSAGE_PHOTO_CLASSPATH,
                createTextMessageSupplier(),
                buildInlineKeyboardMarkup(Locale.ITALIAN, AdminPage.values(), user)
        );
    }

    private Supplier<String> createTextMessageSupplier() {
        return () -> messageTemplate.getAdminPageMessage(Locale.ITALIAN);
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto processLoadFilePhrasesCommand(IncomingMessageContext incomingMessageContext) {
        Objects.requireNonNull(incomingMessageContext);
        Message message = incomingMessageContext.getMessageSent();

        // Validate user roles for loading file phrases command
        validateUserForLoadFilePhrases(incomingMessageContext);

        Long chatId = MessageUtility.getChatId(message);

        // Send instructions to user for loading file phrases
        telegramIntegrationApi.sendSimpleMessage(
                chatId,
                messageTemplate.getLoadFilePhrasesCommandMessage(MessageConstants.DEFAULT_LOCALE)
        );

        // Set step detail in cache for next step
        cacheService.saveStepDetail(chatId, StepDetailFactory.loadFilePhrasesStepOne());

        return null;
    }

    private void validateUserForLoadFilePhrases(IncomingMessageContext incomingMessageContext) {
        Set<UserRole> commandAuthRoles = commandProperties.getAuthorizedRolesForCommand(CommandConstants.LoadFilePhrases.TEXT);
        validateAndGetUserDto(incomingMessageContext, commandAuthRoles);
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto processLoadFilePhrasesCommandStepOne(IncomingMessageContext incomingMessageContext) {
        Objects.requireNonNull(incomingMessageContext);

        Message message = incomingMessageContext.getMessageSent();
        // Validate user roles for loading file phrases command
        validateUserForLoadFilePhrases(incomingMessageContext);

        Long chatId = MessageUtility.getChatId(message);

        // Download phrases file, parse and persist
        extractAndPersistPhrasesFromFile(chatId, message);

        // Notify user of successful upload
        sendFileUploadSuccessNotification(chatId);

        // Remove step detail from cache after completion
        cacheService.removeStepDetail(chatId);

        return null;
    }

    private void extractAndPersistPhrasesFromFile(Long chatId, Message message) {
        Document document = message.getDocument();

        // Download the file from Telegram
        byte[] downloadedFile = telegramIntegrationApi.downloadFile(
                TelegramApiRequestUtility.buildGetFileRequest(chatId, document.getFileId()));

        // Parse phrases from the CSV file and save them
        Set<PhraseDto> phrases = FilesUtility.getPhrasesFromCSVFile(downloadedFile);
        phraseService.save(phrases);
    }

    private void sendFileUploadSuccessNotification(Long chatId) {
        telegramIntegrationApi.sendSimpleMessage(
                chatId,
                messageTemplate.getLoadFilePhrasesCommandStepOneMessage(Locale.ITALIAN)
        );
    }

}
