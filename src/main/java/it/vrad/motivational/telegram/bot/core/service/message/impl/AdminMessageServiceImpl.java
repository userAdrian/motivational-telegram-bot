package it.vrad.motivational.telegram.bot.core.service.message.impl;

import it.vrad.motivational.telegram.bot.core.model.constants.MessageConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.pages.types.AdminPageButton;
import it.vrad.motivational.telegram.bot.core.service.message.AbstractMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.AdminMessageService;
import it.vrad.motivational.telegram.bot.core.service.message.template.MessageTemplate;
import it.vrad.motivational.telegram.bot.core.service.page.PageConfigurationService;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;


/**
 * Implementation of {@link AdminMessageService}
 * for admin message operations.
 */
@Service
public class AdminMessageServiceImpl extends AbstractMessageService implements AdminMessageService {
    private final TelegramIntegrationApi telegramIntegrationApi;

    public AdminMessageServiceImpl(
            MessageTemplate messageTemplate,
            TelegramFileDao telegramFileDao,
            PageConfigurationService pageConfigurationService,
            TelegramIntegrationApi telegramIntegrationApi1
    ) {
        super(messageTemplate, telegramFileDao, pageConfigurationService);

        this.telegramIntegrationApi = telegramIntegrationApi1;
    }

    /**
     * {@inheritDoc}
     *
     * @param user {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public MessageDto generateAdminPageMessageDto(UserDto user) {
        return buildMessageDto(
                PageConstants.AdminPage.MESSAGE_PHOTO_CLASSPATH,
                createTextMessageSupplier(),
                buildInlineKeyboardMarkup(Locale.ITALIAN, AdminPageButton.values(), user)
        );
    }

    private Supplier<String> createTextMessageSupplier() {
        return () -> messageTemplate.getAdminPageMessage(Locale.ITALIAN);
    }

    /**
     * {@inheritDoc}
     *
     * @param sentMessage        {@inheritDoc}
     * @param telegramFileIdSent {@inheritDoc}
     */
    @Override
    public void persistTelegramFile(Message sentMessage, String telegramFileIdSent) {
        persistTelegramFile(PageConstants.AdminPage.MESSAGE_PHOTO_CLASSPATH, sentMessage, telegramFileIdSent);
    }

    /**
     * {@inheritDoc}
     *
     * @param chatId {@inheritDoc}
     */
    @Override
    public void sendLoadFilePhrasesCommandMessage(Long chatId) {
        Objects.requireNonNull(chatId);

        // Send instructions to user for loading file phrases
        telegramIntegrationApi.sendSimpleMessage(
                chatId,
                messageTemplate.getLoadFilePhrasesCommandMessage(MessageConstants.DEFAULT_LOCALE)
        );

    }

    /**
     * {@inheritDoc}
     *
     * @param chatId {@inheritDoc}
     */
    @Override
    public void sendFileUploadSuccessNotification(Long chatId) {
        Objects.requireNonNull(chatId);

        telegramIntegrationApi.sendSimpleMessage(
                chatId,
                messageTemplate.getLoadFilePhrasesCommandStepOneMessage(Locale.ITALIAN)
        );
    }

}
