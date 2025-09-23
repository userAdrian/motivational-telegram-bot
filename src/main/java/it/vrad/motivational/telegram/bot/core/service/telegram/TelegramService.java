package it.vrad.motivational.telegram.bot.core.service.telegram;

import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.CallbackQueryAnswerRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Document;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility;

public interface TelegramService {

    Message sendPhoto(Message receivedMessage, MessageDto processedMessage);

    Message editMessageMedia(Message receivedMessage, MessageDto processedMessage);


    /**
     * Answers a callback query by sending a response to Telegram.
     *
     * @param callbackQuery the callback query to answer
     * @param chatId        the chat ID associated with the callback
     */
    void answerCallbackQuery(CallbackQuery callbackQuery, Long chatId);

    byte[] downloadFile(Document document, Long chatId);

}
