package it.vrad.motivational.telegram.bot.integration.telegram.api;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.CallbackQueryAnswerRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.EditMessageMediaRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.GetFileRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.SendMessageRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.SendPhotoRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.File;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Integration API for interacting with the Telegram Bot API.
 */
@Validated
public interface TelegramIntegrationApi {

    /**
     * Sends a message using the Telegram Bot API.
     *
     * @param request the message request
     * @return the sent {@link Message}
     */
    Message sendMessage(@NotNull @Valid SendMessageRequest request);

    /**
     * Sends a simple text message to a chat.
     *
     * @param chatId the chat ID
     * @param text   the message text
     * @return the sent {@link Message}
     */
    Message sendSimpleMessage(Long chatId, String text);

    /**
     * Sends a phrase to a chat.
     *
     * @param chatId    the chat ID
     * @param phraseDto the phrase DTO
     * @return the sent {@link Message}
     */
    Message sendPhrase(Long chatId, PhraseDto phraseDto);

    /**
     * Sends a photo using the Telegram Bot API.
     *
     * @param sendPhotoRequest the photo request
     * @return the sent {@link Message}
     */
    Message sendPhoto(@NotNull @Valid SendPhotoRequest sendPhotoRequest);

    /**
     * Edits the media content of a message.
     *
     * @param request the edit message media request
     * @return the edited {@link Message}
     */
    Message editMessageMedia(@NotNull @Valid EditMessageMediaRequest request);

    /**
     * Answers a callback query.
     *
     * @param callbackQueryAnswerRequest the callback query answer request
     * @return {@code true} if the callback query was answered successfully, otherwise {@code false}
     */
    Boolean answerCallbackQuery(@NotNull @Valid CallbackQueryAnswerRequest callbackQueryAnswerRequest);

    /**
     * Retrieves a file from Telegram.
     *
     * @param request the get file request
     * @return the {@link File} object
     */
    File getFile(@NotNull @Valid GetFileRequest request);

    /**
     * Downloads a file from Telegram using its path.
     *
     * @param path   the file path
     * @param chatId the chat ID
     * @return the file as a byte array
     */
    byte[] downloadFile(String path, Long chatId);

    /**
     * Downloads a file from Telegram using a {@link GetFileRequest}.
     *
     * @param request the get file request
     * @return the file as a byte array
     */
    byte[] downloadFile(@NotNull @Valid GetFileRequest request);
}
