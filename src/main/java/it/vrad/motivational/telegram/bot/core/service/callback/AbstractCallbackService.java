package it.vrad.motivational.telegram.bot.core.service.callback;

import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.CallbackQueryAnswerRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;

/**
 * Abstract base class for callback services.
 * <p>
 * Provides common logic for answering callback queries using the Telegram integration API.
 */
public abstract class AbstractCallbackService {
    protected final TelegramIntegrationApi telegramIntegrationApi;

    protected AbstractCallbackService(TelegramIntegrationApi telegramIntegrationApi) {
        this.telegramIntegrationApi = telegramIntegrationApi;
    }

    /**
     * Answers a callback query by sending a response to Telegram.
     *
     * @param callbackQuery the callback query to answer
     * @param chatId        the chat ID associated with the callback
     */
    protected void answerCallbackQuery(CallbackQuery callbackQuery, Long chatId) {
        // Build the answer request for the callback query
        CallbackQueryAnswerRequest request =
                TelegramApiRequestUtility.buildCallbackQueryAnswerRequest(callbackQuery.getId(), chatId);

        // Send the answer to Telegram
        telegramIntegrationApi.answerCallbackQuery(request);
    }
}
