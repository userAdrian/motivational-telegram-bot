package it.vrad.motivational.telegram.bot.integration.telegram.api.impl;

import it.vrad.motivational.telegram.bot.config.properties.TelegramProperties;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.client.TelegramRestTemplate;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.CallbackQueryAnswerRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.EditMessageMediaRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.GetFileRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.SendMessageRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.SendPhotoRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.File;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link TelegramIntegrationApi} for interacting with the Telegram Bot API.
 */
@Slf4j
@Component
public class TelegramIntegrationApiImpl implements TelegramIntegrationApi {

    private final TelegramRestTemplate telegramRestTemplate;
    private final TelegramProperties telegramProperties;

    public TelegramIntegrationApiImpl(
            TelegramRestTemplate telegramRestTemplate,
            TelegramProperties telegramProperties
    ) {
        this.telegramRestTemplate = telegramRestTemplate;
        this.telegramProperties = telegramProperties;
    }

    /**
     * Helper method for sending message-related POST requests
     */
    private Message messagePostRequest(String url, HttpEntity<?> httpEntity, Long chatId) {
        return telegramRestTemplate.performExchange(
                HttpMethod.POST,
                url,
                httpEntity,
                new ParameterizedTypeReference<>() {
                },
                chatId
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param request {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Message sendMessage(SendMessageRequest request) {
        // Send a message using the Telegram API endpoint
        return messagePostRequest(
                telegramProperties.getUrlSendMessage(),
                TelegramApiRequestUtility.getHttpEntityJson(request),
                request.getChatId()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param chatId {@inheritDoc}
     * @param text   {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Message sendSimpleMessage(Long chatId, String text) {
        // Build and send a simple text message
        return sendMessage(
                SendMessageRequest.builder()
                        .chatId(chatId)
                        .text(text)
                        .build()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param chatId    {@inheritDoc}
     * @param phraseDto {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Message sendPhrase(Long chatId, PhraseDto phraseDto) {
        // Format and send a phrase as a message
        return sendSimpleMessage(chatId, MessageUtility.formatPhrase(phraseDto));
    }

    /**
     * {@inheritDoc}
     *
     * @param request {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Message sendPhoto(SendPhotoRequest request) {
        // Send a photo using the Telegram API endpoint
        return messagePostRequest(
                telegramProperties.getUrlSendPhoto(),
                TelegramApiRequestUtility.getHttpEntityMultipart(request.asMultiValueMap()),
                request.getChatId()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param request {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Message editMessageMedia(EditMessageMediaRequest request) {
        // Edit the media content of a message
        return messagePostRequest(
                telegramProperties.getUrlEditMessageMedia(),
                TelegramApiRequestUtility.getHttpEntityMultipart(request.asMultiValueMap()),
                request.getChatId()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param callbackQueryAnswerRequest {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Boolean answerCallbackQuery(CallbackQueryAnswerRequest callbackQueryAnswerRequest) {
        // Answer a callback query using the Telegram API endpoint
        return telegramRestTemplate.performExchange(
                HttpMethod.POST,
                telegramProperties.getUrlAnswerCallbackQuery(),
                TelegramApiRequestUtility.getHttpEntityJson(callbackQueryAnswerRequest),
                new ParameterizedTypeReference<>() {
                },
                callbackQueryAnswerRequest.getChatId()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param getFileRequest {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public File getFile(GetFileRequest getFileRequest) {
        // Retrieve a file from Telegram using the API endpoint
        return telegramRestTemplate.performExchange(
                HttpMethod.POST,
                telegramProperties.getUrlGetFile(),
                TelegramApiRequestUtility.getHttpEntityJson(getFileRequest),
                new ParameterizedTypeReference<>() {
                },
                getFileRequest.getChatId()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param path   {@inheritDoc}
     * @param chatId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public byte[] downloadFile(String path, Long chatId) {
        // Download a file from Telegram using its path
        return telegramRestTemplate.performExchange(
                HttpMethod.GET,
                TelegramApiRequestUtility.composeDownloadFileUrl(telegramProperties.getBaseFileUrl(), path),
                null,
                byte[].class,
                chatId
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param request {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public byte[] downloadFile(GetFileRequest request) {
        // Retrieve file info and then download the file
        File file = getFile(request);
        return downloadFile(file.getFilePath(), request.getChatId());
    }

}
