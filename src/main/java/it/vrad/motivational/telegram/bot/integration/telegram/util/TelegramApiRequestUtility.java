package it.vrad.motivational.telegram.bot.integration.telegram.util;


import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.shared.util.FileUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.CallbackQueryAnswerRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.EditMessageMediaRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.GetFileRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.InputMedia;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.InputMediaPhoto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.SendPhotoRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

/**
 * Utility class for building and preparing HTTP requests and request objects for the Telegram Bot API.
 */
public class TelegramApiRequestUtility {

    /**
     * Creates HTTP headers with the specified content type.
     *
     * @param mediaType the media type to set as Content-Type
     * @return the configured {@link HttpHeaders}
     */
    public static HttpHeaders getHeaders(MediaType mediaType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaType);
        return httpHeaders;
    }

    /**
     * Builds an HTTP entity with JSON content type.
     *
     * @param request the request body
     * @return the {@link HttpEntity} with JSON headers
     */
    public static <T> HttpEntity<T> getHttpEntityJson(T request) {
        return new HttpEntity<>(request, getHeaders(MediaType.APPLICATION_JSON));
    }

    /**
     * Builds an HTTP entity with multipart/form-data content type.
     *
     * @param map the multipart data
     * @return the {@link HttpEntity} with multipart headers
     */
    public static HttpEntity<MultiValueMap<String, Object>> getHttpEntityMultipart(MultiValueMap<String, Object> map) {
        return new HttpEntity<>(map, getHeaders(MediaType.MULTIPART_FORM_DATA));
    }

    /**
     * Constructs a {@link SendPhotoRequest} from the original and processed message.
     *
     * @param originalMessage  the original Telegram message
     * @param processedMessage the processed message DTO
     * @return the {@link SendPhotoRequest}
     */
    public static SendPhotoRequest getSendPhotoRequest(Message originalMessage, MessageDto processedMessage) {
        return SendPhotoRequest.builder()
                .chatId(MessageUtility.getChatId(originalMessage))
                .telegramFileId(processedMessage.getTelegramFileId())
                .photo(processedMessage.getPhoto())
                .caption(processedMessage.getText())
                .replyMarkup(processedMessage.getInlineKeyboardMarkup())
                .build();
    }

    /**
     * Builds an {@link EditMessageMediaRequest} for editing a photo in a message.
     *
     * @param originalMessage  the original Telegram message
     * @param processedMessage the processed message DTO
     * @return the {@link EditMessageMediaRequest} for a photo
     */
    public static EditMessageMediaRequest getEditMessageMediaPhotoRequest(Message originalMessage, MessageDto processedMessage) {
        // Build the input media photo object
        InputMedia inputMediaPhoto = TelegramApiRequestUtility.buildInputMediaPhoto(processedMessage);
        // Delegate to the generic edit message media request builder
        return getEditMessageMediaRequest(originalMessage, processedMessage, inputMediaPhoto);
    }

    /**
     * Builds an {@link EditMessageMediaRequest} with the specified input media.
     *
     * @param originalMessage  the original Telegram message
     * @param processedMessage the processed message DTO
     * @param inputMedia       the input media to use
     * @return the {@link EditMessageMediaRequest}
     */
    public static EditMessageMediaRequest getEditMessageMediaRequest(Message originalMessage, MessageDto processedMessage,
                                                                     InputMedia inputMedia) {
        Objects.requireNonNull(inputMedia);

        return EditMessageMediaRequest.builder()
                .chatId(MessageUtility.getChatId(originalMessage))
                .messageId(originalMessage.getMessageId())
                .inputMedia(inputMedia)
                .fileToSend(processedMessage.getPhoto())
                .replyMarkup(processedMessage.getInlineKeyboardMarkup())
                .build();
    }

    /**
     * Builds an {@link InputMediaPhoto} from the given message DTO.
     *
     * @param messageDto the message DTO
     * @return the {@link InputMediaPhoto}
     */
    public static InputMediaPhoto buildInputMediaPhoto(MessageDto messageDto) {
        // Determine the media value (file ID, URL, or file)
        String media = FileUtility.getMediaValue(messageDto.getTelegramFileId(), messageDto.getPhoto(), null);
        return InputMediaPhoto.builder()
                .type(messageDto.getMediaType())
                .media(media)
                .caption(messageDto.getText())
                .build();
    }

    /**
     * Builds a {@link CallbackQueryAnswerRequest} for answering a callback query.
     *
     * @param callbackQueryId the callback query ID
     * @param chatId          the chat ID
     * @return the {@link CallbackQueryAnswerRequest}
     */
    public static CallbackQueryAnswerRequest buildCallbackQueryAnswerRequest(String callbackQueryId, Long chatId) {
        return CallbackQueryAnswerRequest.builder()
                .callbackQueryId(callbackQueryId)
                .chatId(chatId)
                .build();
    }

    /**
     * Builds a {@link GetFileRequest} for retrieving a file from Telegram.
     *
     * @param chatId the chat ID
     * @param fileId the file ID
     * @return the {@link GetFileRequest}
     */
    public static GetFileRequest buildGetFileRequest(Long chatId, String fileId) {
        return GetFileRequest.builder()
                .chatId(chatId)
                .fileId(fileId)
                .build();
    }

    /**
     * Composes the full download URL for a Telegram file.
     *
     * @param baseFileUrl the base file URL
     * @param path        the file path
     * @return the complete download URL
     */
    public static String composeDownloadFileUrl(String baseFileUrl, String path) {
        return baseFileUrl + "/" + path;
    }

}
