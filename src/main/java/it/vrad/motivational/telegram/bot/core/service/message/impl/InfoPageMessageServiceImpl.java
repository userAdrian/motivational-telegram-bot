package it.vrad.motivational.telegram.bot.core.service.message.impl;

import it.vrad.motivational.telegram.bot.config.properties.PhraseProperties;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.InfoPage;
import it.vrad.motivational.telegram.bot.core.service.message.AbstractMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.InfoPageMessageService;
import it.vrad.motivational.telegram.bot.core.model.constants.MessageConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Implementation of {@link InfoPageMessageService}
 * for info page message operations.
 */
@Component
public class InfoPageMessageServiceImpl extends AbstractMessageService implements InfoPageMessageService {
    private final PhraseProperties phraseProperties;

    protected InfoPageMessageServiceImpl(
            MessageTemplate messageTemplate,
            TelegramFileDao telegramFileDao,
            PageConfigurationService pageConfigurationService,
            TelegramIntegrationApi telegramIntegrationApi,
            UserService userService,
            PhraseProperties phraseProperties
    ) {
        super(messageTemplate, telegramFileDao, pageConfigurationService, telegramIntegrationApi, userService);
        this.phraseProperties = phraseProperties;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto forwardToInfoPage(IncomingMessageContext incomingMessageContext) {
        Objects.requireNonNull(incomingMessageContext);
        Message message = incomingMessageContext.getMessageSent();

        // Retrieve user
        UserDto user = getUserDto(incomingMessageContext, message);

        // Build and send info page message
        MessageDto messageDto = generateMessageDto(user);
        Message sentMessage = telegramIntegrationApi.editMessageMedia(
                TelegramApiRequestUtility.getEditMessageMediaPhotoRequest(message, messageDto));

        // Persist Telegram file info after sending
        persistTelegramFile(PageConstants.InfoPage.MESSAGE_PHOTO_CLASSPATH, sentMessage, messageDto.getTelegramFileId());

        return null;
    }

    private MessageDto generateMessageDto(UserDto user) {
        return buildMessageDto(
                PageConstants.InfoPage.MESSAGE_PHOTO_CLASSPATH,
                createTextMessageSupplier(),
                buildInlineKeyboardMarkup(MessageConstants.DEFAULT_LOCALE, InfoPage.values(), user)
        );
    }

    private Supplier<String> createTextMessageSupplier() {
        return () -> messageTemplate.generateInfoPageMessage(
                MessageConstants.DEFAULT_LOCALE, phraseProperties.getSendingTimes(), phraseProperties.getTimeZone());
    }
}
