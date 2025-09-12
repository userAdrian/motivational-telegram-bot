package it.vrad.motivational.telegram.bot.core.service.callback.impl;

import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingCallbackContext;
import it.vrad.motivational.telegram.bot.core.model.dto.CallbackQueryDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.core.service.callback.AbstractCallbackService;
import it.vrad.motivational.telegram.bot.core.service.callback.AdminPageCallbackService;
import it.vrad.motivational.telegram.bot.core.service.message.AdminMessageService;
import it.vrad.motivational.telegram.bot.integration.telegram.util.CallbackUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import it.vrad.motivational.telegram.bot.core.model.factory.ObjectsFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Implementation of {@link AdminPageCallbackService}
 * for handling admin page callback actions.
 */
@Service
public class AdminPageCallbackServiceImpl extends AbstractCallbackService implements AdminPageCallbackService {
    private final AdminMessageService adminMessageService;

    public AdminPageCallbackServiceImpl(
            AdminMessageService adminMessageService,
            TelegramIntegrationApi telegramIntegrationApi
    ) {
        super(telegramIntegrationApi);
        this.adminMessageService = adminMessageService;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingCallbackContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public CallbackQueryDto processCallbackQuery(IncomingCallbackContext incomingCallbackContext) {
        Objects.requireNonNull(incomingCallbackContext);

        // Extract callback query and update message sender
        CallbackQuery callbackQuery = incomingCallbackContext.getCallbackQuery();
        Message message = CallbackUtility.updateMessageSenderFromCallback(callbackQuery);

        // Answer the callback query to Telegram
        answerCallbackQuery(callbackQuery, MessageUtility.getChatId(message));

        // Forward to admin page
        adminMessageService.forwardToAdminPage(ObjectsFactory.buildMessageParameterDto(message));
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingCallbackContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public CallbackQueryDto processLoadPhrasesFileButton(IncomingCallbackContext incomingCallbackContext) {
        Objects.requireNonNull(incomingCallbackContext);

        // Extract callback query and update message sender
        CallbackQuery callbackQuery = incomingCallbackContext.getCallbackQuery();
        Message message = CallbackUtility.updateMessageSenderFromCallback(callbackQuery);

        // Answer the callback query to Telegram
        answerCallbackQuery(callbackQuery, MessageUtility.getChatId(message));

        // Hook into the process of loadFilePhrases command
        adminMessageService.processLoadFilePhrasesCommand(ObjectsFactory.buildMessageParameterDto(message));

        return null;
    }
}
