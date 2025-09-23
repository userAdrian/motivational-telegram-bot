package it.vrad.motivational.telegram.bot.integration.telegram.api;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.testutil.FileTestUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.TelegramConstants;
import it.vrad.motivational.telegram.bot.integration.telegram.api.enums.MediaTestType;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.CallbackQueryAnswerRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.EditMessageMediaRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.GetFileRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.InputMediaPhoto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.SendMessageRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.SendPhotoRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardButton;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardMarkup;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.CALLBACK_QUERY_ID;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.CHAT_ID;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.TEST_PHOTO_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.TELEGRAM_FILE_ID;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.TEXT_MESSAGE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramApiRequestTestFactory {

    public static SendPhotoRequest createSendPhotoRequest(boolean withFile) {
        SendPhotoRequest.SendPhotoRequestBuilder builder = SendPhotoRequest.builder();

        return builder
                .chatId(CHAT_ID)
                .telegramFileId(withFile ? null : TELEGRAM_FILE_ID)
                .photo(withFile ? FileTestUtility.getFileFromClassPath(TEST_PHOTO_PATH) : null)
                .replyMarkup(createReplyMarkup())
                .build();
    }

    public static SendMessageRequest createSendMessageRequest() {
        return SendMessageRequest.builder()
                .chatId(CHAT_ID)
                .text(TEXT_MESSAGE)
                .build();
    }

    public static EditMessageMediaRequest createEditMessageMediaPhotoRequest(MediaTestType mediaType) {
        File fileToSend = MediaTestType.FILE.equals(mediaType)
                ? FileTestUtility.getFileFromClassPath(TEST_PHOTO_PATH) : null;

        return EditMessageMediaRequest.builder()
                .chatId(CHAT_ID)
                .inputMedia(createInputMediaPhoto(mediaType, fileToSend))
                .fileToSend(fileToSend)
                .replyMarkup(createReplyMarkup())
                .build();
    }

    private static InputMediaPhoto createInputMediaPhoto(MediaTestType mediaType, File fileToSend) {
        return InputMediaPhoto.builder()
                .caption(TEXT_MESSAGE)
                .media(getMedia(mediaType, fileToSend))
                .type(TelegramConstants.TELEGRAM_PHOTO_MEDIA_TYPE_NAME)
                .build();
    }

    private static String getMedia(MediaTestType mediaType, File fileToSend) {
        return switch (mediaType) {
            case TELEGRAM_ID -> TELEGRAM_FILE_ID;
            case FILE -> TelegramConstants.TELEGRAM_INPUT_MEDIA_ATTACH_PREFIX + fileToSend.getName();
            default -> throw new IllegalArgumentException("Enum not managed: " + mediaType);
        };
    }

    public static CallbackQueryAnswerRequest createCallbackQueryAnswerRequest() {
        return CallbackQueryAnswerRequest.builder()
                .chatId(CHAT_ID)
                .callbackQueryId(CALLBACK_QUERY_ID)
                .build();
    }

    public static GetFileRequest createGetFileRequest() {
        return GetFileRequest.builder()
                .chatId(CHAT_ID)
                .fileId(TELEGRAM_FILE_ID)
                .build();
    }

    private static InlineKeyboardMarkup createReplyMarkup() {
        return new InlineKeyboardMarkup(createInlineKeyboardButtonMatrix());
    }

    private static InlineKeyboardButton[][] createInlineKeyboardButtonMatrix() {
        InlineKeyboardButton[][] matrix = new InlineKeyboardButton[2][];

        matrix[0] = new InlineKeyboardButton[1];
        matrix[0][0] = createButton("button1", "callbackData1");

        matrix[1] = new InlineKeyboardButton[2];
        matrix[1][0] = createButton("button2", "callbackData2");
        matrix[1][1] = createButton("button3", "callbackData3");

        return matrix;
    }

    private static InlineKeyboardButton createButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText(text);
        button.setCallbackData(callbackData);

        return button;
    }
}
