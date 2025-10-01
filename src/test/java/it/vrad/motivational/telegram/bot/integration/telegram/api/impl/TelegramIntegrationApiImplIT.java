package it.vrad.motivational.telegram.bot.integration.telegram.api.impl;

import it.vrad.motivational.telegram.bot.config.properties.TelegramProperties;
import it.vrad.motivational.telegram.bot.infrastructure.exception.constants.ExceptionMessageConstants;
import it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants;
import it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory;
import it.vrad.motivational.telegram.bot.infrastructure.testutil.FileTestUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramApiRequestTestFactory;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationTestConfig;
import it.vrad.motivational.telegram.bot.integration.telegram.api.enums.MediaTestType;
import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.CallbackQueryAnswerRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.EditMessageMediaRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.GetFileRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.SendMessageRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.request.SendPhotoRequest;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.File;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.TelegramApiRequestUtility;
import it.vrad.motivational.telegram.bot.integration.util.BaseRestMockTest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;

import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.ANSWER_CALLBACK_QUERY_REQUEST_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.ANSWER_CALLBACK_QUERY_RESPONSE_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.CHAT_ID;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.EDIT_MESSAGE_MEDIA_RESPONSE_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.GET_FILE_REQUEST_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.GET_FILE_RESPONSE_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.SEND_MESSAGE_REQUEST_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.SEND_MESSAGE_RESPONSE_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.SEND_PHOTO_RESPONSE_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.SEND_PHRASE_REQUEST_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.SEND_PHRASE_RESPONSE_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.SEND_SIMPLE_MESSAGE_REQUEST_400_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.SEND_SIMPLE_MESSAGE_REQUEST_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.SEND_SIMPLE_MESSAGE_RESPONSE_200_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.SEND_SIMPLE_MESSAGE_RESPONSE_400_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.TELEGRAM_FILE_ID;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.TELEGRAM_FILE_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.TEST_PHOTO_PATH;
import static it.vrad.motivational.telegram.bot.integration.telegram.api.constants.TelegramTestConstants.TEXT_MESSAGE;
import static it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility.getChatId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for the TelegramIntegrationApiImpl.
 * <p>
 * These tests cover the main Telegram Bot API integration points, including:
 * <ul>
 *   <li>Sending messages and simple messages</li>
 *   <li>Sending phrases</li>
 *   <li>Sending photos</li>
 *   <li>Editing message media</li>
 *   <li>Answering callback queries</li>
 *   <li>Getting and downloading files</li>
 * </ul>
 * <p>
 * Each section is grouped and commented for clarity. Helper methods are at the bottom.
 */
@SpringBootTest
@ContextConfiguration(classes = TelegramIntegrationTestConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TelegramIntegrationApiImplIT extends BaseRestMockTest {

    private final TelegramProperties telegramProperties;
    private final TelegramIntegrationApiImpl telegramIntegrationApi;

    // --------------------------------------
    // SendMessage and SendSimpleMessage API tests
    // --------------------------------------

    /**
     * Tests for SendMessage and SendSimpleMessage API endpoints.
     * <p>
     * Covers both successful and failure scenarios for sending messages.
     */
    @Nested
    class SendMessageTests {
        @Test
        @DisplayName("sendMessage should return a Message when the request is valid")
        void sendMessage_whenValidRequest_returnsMessage() {
            SendMessageRequest request = TelegramApiRequestTestFactory.createSendMessageRequest();

            mockSuccessfulJsonResponse(
                    telegramProperties.getUrlSendMessage(),
                    HttpMethod.POST,
                    SEND_MESSAGE_REQUEST_200_PATH,
                    SEND_MESSAGE_RESPONSE_200_PATH
            );

            Message message = telegramIntegrationApi.sendMessage(request);
            assertThat(getChatId(message)).isEqualTo(CHAT_ID);
            assertThat(message.getText()).isEqualTo(request.getText());
            verifyHttpRequestCount(1, telegramProperties.getUrlSendMessage());
        }

        @Test
        @DisplayName("sendSimpleMessage should return a Message when the request is valid")
        void sendSimpleMessage_whenValidRequest_returnsMessage() {
            mockSuccessfulJsonResponse(
                    telegramProperties.getUrlSendMessage(),
                    HttpMethod.POST,
                    SEND_SIMPLE_MESSAGE_REQUEST_200_PATH,
                    SEND_SIMPLE_MESSAGE_RESPONSE_200_PATH
            );

            Message message = telegramIntegrationApi.sendSimpleMessage(CHAT_ID, TEXT_MESSAGE);

            assertThat(getChatId(message)).isEqualTo(CHAT_ID);
            assertThat(message.getText()).isEqualTo(TEXT_MESSAGE);
            verifyHttpRequestCount(1, telegramProperties.getUrlSendMessage());
        }

        @Test
        @DisplayName("sendSimpleMessage should throw IntegrationApiException when HTTP fails")
        void sendSimpleMessage_whenHttpFails_throwsIntegrationApiException() {
            String api = telegramProperties.getUrlSendMessage();
            mockFailedJsonResponse(
                    api,
                    HttpMethod.POST,
                    SEND_SIMPLE_MESSAGE_REQUEST_400_PATH,
                    SEND_SIMPLE_MESSAGE_RESPONSE_400_PATH
            );

            IntegrationApiException ex = assertThrows(
                    IntegrationApiException.class,
                    () -> telegramIntegrationApi.sendSimpleMessage(CHAT_ID, null)
            );

            assertThat(ex.getApi()).isEqualTo(api);
            assertThat(ex.getChatId()).isEqualTo(CHAT_ID);
            assertThat(ex.getDescription()).isEqualTo("Bad Request: text is empty");
            assertThat(ex.getErrorCode()).isEqualTo("400");
            verifyHttpRequestCount(1, api);
        }

        @Test
        @DisplayName("sendSimpleMessage should throw IntegrationApiException on generic failure")
        void sendSimpleMessage_whenGenericFail_throwsIntegrationApiException() {
            String api = telegramProperties.getUrlSendMessage();
            mockFailedResponse(
                    api,
                    HttpMethod.POST,
                    SEND_SIMPLE_MESSAGE_REQUEST_200_PATH
            );

            IntegrationApiException ex = assertThrows(
                    IntegrationApiException.class,
                    () -> telegramIntegrationApi.sendSimpleMessage(CHAT_ID, TEXT_MESSAGE)
            );
            assertThat(ex.getApi()).isEqualTo(api);
            assertThat(ex.getChatId()).isEqualTo(CHAT_ID);
            assertThat(ex.getDescription()).isEqualTo(ExceptionMessageConstants.INTERNAL_SERVER_ERROR_DESCRIPTION);
            assertThat(ex.getErrorCode()).isEqualTo(ExceptionMessageConstants.INTERNAL_SERVER_ERROR_CODE);
            verifyHttpRequestCount(1, api);
        }
    }

    // --------------------------------------
    // Phrase API tests
    // --------------------------------------
    @Test
    @DisplayName("sendPhrase should return a Message when the request is valid")
    public void sendPhrase_whenValidRequest_returnsMessage() {
        // Test sending a phrase with a valid request
        mockSuccessfulJsonResponse(
                telegramProperties.getUrlSendMessage(),
                HttpMethod.POST,
                SEND_PHRASE_REQUEST_200_PATH,
                SEND_PHRASE_RESPONSE_200_PATH
        );
        Message message = telegramIntegrationApi.sendPhrase(CHAT_ID, PersistenceTestFactory.createGenericPhraseDto());
        assertThat(getChatId(message)).isEqualTo(CHAT_ID);
        assertThat(message.getText()).isEqualTo(PersistenceTestConstants.PHRASE_TEXT);
        verifyHttpRequestCount(1, telegramProperties.getUrlSendMessage());
    }

    // --------------------------------------
    // Photo API tests
    // --------------------------------------
    @Test
    @DisplayName("sendPhoto should return a Message when using Telegram file id")
    public void sendPhoto_whenTelegramFileId_returnsMessage() {
        // Test sending a photo using a Telegram file id
        sendPhotoSuccessTest(false);
    }

    @Test
    @DisplayName("sendPhoto should return a Message when using file upload")
    public void sendPhoto_whenFile_returnsMessage() {
        // Test sending a photo using a file upload
        sendPhotoSuccessTest(true);
    }

    private void sendPhotoSuccessTest(boolean withFile) {
        SendPhotoRequest request = TelegramApiRequestTestFactory.createSendPhotoRequest(withFile);
        mockSuccessfulJsonResponse(
                telegramProperties.getUrlSendPhoto(),
                HttpMethod.POST,
                request.asMultiValueMap(),
                SEND_PHOTO_RESPONSE_200_PATH
        );
        Message message = telegramIntegrationApi.sendPhoto(request);
        assertThat(getChatId(message)).isEqualTo(CHAT_ID);
        assertThat(message.getCaption()).isEqualTo(TEXT_MESSAGE);
        assertThat(message.getReplyMarkup()).isEqualTo(request.getReplyMarkup());
        assertTrue(CollectionUtils.isNotEmpty(message.getPhoto()), "Return photo array should be filled");
        verifyHttpRequestCount(1, telegramProperties.getUrlSendPhoto());
    }

    // --------------------------------------
    // EditMessageMedia API tests
    // --------------------------------------
    @ParameterizedTest
    @EnumSource(MediaTestType.class)
    @DisplayName("editMessageMedia should return a Message when the request is valid")
    public void editMessageMedia_whenValidRequest_returnsMessage(MediaTestType mediaType) {
        EditMessageMediaRequest request = TelegramApiRequestTestFactory.createEditMessageMediaPhotoRequest(mediaType);

        mockSuccessfulJsonResponse(
                telegramProperties.getUrlEditMessageMedia(),
                HttpMethod.POST,
                request.asMultiValueMap(),
                EDIT_MESSAGE_MEDIA_RESPONSE_200_PATH
        );

        Message message = telegramIntegrationApi.editMessageMedia(request);
        assertThat(getChatId(message)).isEqualTo(CHAT_ID);
        assertThat(message.getCaption()).isEqualTo(TEXT_MESSAGE);
        assertTrue(CollectionUtils.isNotEmpty(message.getPhoto()), "Return photo array should be filled");
        verifyHttpRequestCount(1, telegramProperties.getUrlEditMessageMedia());
    }

    // --------------------------------------
    // CallbackQuery API tests
    // --------------------------------------
    @Test
    @DisplayName("answerCallbackQuery should return a true when the request is valid")
    public void answerCallbackQuery_whenValidRequest_returnsTrue() {
        mockSuccessfulJsonResponse(
                telegramProperties.getUrlAnswerCallbackQuery(),
                HttpMethod.POST,
                ANSWER_CALLBACK_QUERY_REQUEST_200_PATH,
                ANSWER_CALLBACK_QUERY_RESPONSE_200_PATH
        );

        CallbackQueryAnswerRequest request = TelegramApiRequestTestFactory.createCallbackQueryAnswerRequest();

        Boolean result = telegramIntegrationApi.answerCallbackQuery(request);
        assertTrue(result);
        verifyHttpRequestCount(1, telegramProperties.getUrlAnswerCallbackQuery());
    }

    // --------------------------------------
    // GetFile API tests
    // --------------------------------------
    @Test
    @DisplayName("getFile should return a File when the request is valid")
    public void getFile_whenValidRequest_returnsFile() {
        GetFileRequest request = TelegramApiRequestTestFactory.createGetFileRequest();
        mockSuccessfulGetFile();

        File file = telegramIntegrationApi.getFile(request);
        assertThat(file.getFileId()).isEqualTo(TELEGRAM_FILE_ID);
        verifyHttpRequestCount(1, telegramProperties.getUrlGetFile());
    }

    // --------------------------------------
    // DownloadFile API tests
    // --------------------------------------
    @Test
    @DisplayName("downloadFile should return a bytes when the request is valid")
    public void downloadFile_whenValidRequest_returnsBytes() {
        mockSuccessfulDownloadFile();

        byte[] result = telegramIntegrationApi.downloadFile(TELEGRAM_FILE_PATH, CHAT_ID);
        assertThat(result).isEqualTo(FileTestUtility.readBytesFromResource(TEST_PHOTO_PATH));
        verifyHttpRequestCount(1, composeDownloadFileUrl());
    }

    @Test
    @DisplayName("downloadFile should return a bytes when using GetFileRequest")
    public void downloadFile_whenGetFileRequest_returnsBytes() {
        mockSuccessfulGetFile();
        mockSuccessfulDownloadFile();

        byte[] result = telegramIntegrationApi.downloadFile(TelegramApiRequestTestFactory.createGetFileRequest());
        assertThat(result).isEqualTo(FileTestUtility.readBytesFromResource(TEST_PHOTO_PATH));
        verifyHttpRequestCount(1, telegramProperties.getUrlGetFile());
        verifyHttpRequestCount(1, composeDownloadFileUrl());
    }

    // --------------------------------------
    // Test helpers
    // --------------------------------------

    private void mockSuccessfulGetFile() {
        mockSuccessfulJsonResponse(
                telegramProperties.getUrlGetFile(),
                HttpMethod.POST,
                GET_FILE_REQUEST_200_PATH,
                GET_FILE_RESPONSE_200_PATH
        );
    }

    private void mockSuccessfulDownloadFile() {
        mockSuccessfulBinaryResponse(
                composeDownloadFileUrl(),
                HttpMethod.GET,
                null,
                TEST_PHOTO_PATH
        );
    }

    private String composeDownloadFileUrl() {
        return TelegramApiRequestUtility.composeDownloadFileUrl(telegramProperties.getBaseFileUrl(), TELEGRAM_FILE_PATH);
    }
}
