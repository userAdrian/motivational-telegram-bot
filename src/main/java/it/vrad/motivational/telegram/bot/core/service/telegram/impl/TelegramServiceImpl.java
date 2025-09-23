package it.vrad.motivational.telegram.bot.core.service.telegram.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Document;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility.buildCallbackQueryAnswerRequest;
import static it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility.buildGetFileRequest;
import static it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility.getEditMessageMediaPhotoRequest;
import static it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility.getSendPhotoRequest;

@RequiredArgsConstructor
@Service
public class TelegramServiceImpl implements TelegramService {
    private final TelegramIntegrationApi telegramIntegrationApi;

    @Override
    public Message sendPhoto(Message receivedMessage, MessageDto processedMessage) {
        return telegramIntegrationApi.sendPhoto(getSendPhotoRequest(receivedMessage, processedMessage));
    }

    @Override
    public Message editMessageMedia(Message receivedMessage, MessageDto processedMessage) {
        return telegramIntegrationApi.editMessageMedia(getEditMessageMediaPhotoRequest(receivedMessage, processedMessage));
    }

    /**
     * {@inheritDoc}
     *
     * @param callbackQuery {@inheritDoc}
     * @param chatId        {@inheritDoc}
     */
    @Override
    public void answerCallbackQuery(CallbackQuery callbackQuery, Long chatId) {
        telegramIntegrationApi.answerCallbackQuery(buildCallbackQueryAnswerRequest(callbackQuery.getId(), chatId));
    }

    @Override
    public byte[] downloadFile(Document document, Long chatId) {
        return telegramIntegrationApi.downloadFile(buildGetFileRequest(chatId, document.getFileId()));
    }

}
