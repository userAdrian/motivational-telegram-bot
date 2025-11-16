package it.vrad.motivational.telegram.bot.infrastructure.web.controller.impl;

import it.vrad.motivational.telegram.bot.core.dispatch.impl.UpdateDispatcherImpl;
import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.UncheckedWrapperException;
import it.vrad.motivational.telegram.bot.infrastructure.testutil.FileTestUtility;
import it.vrad.motivational.telegram.bot.infrastructure.web.controller.testconfig.TelegramApiImplTestConfig;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static it.vrad.motivational.telegram.bot.infrastructure.web.controller.ApiTestConstants.TelegramApiImpl.WEBHOOK_REQUEST_200_PATH;
import static it.vrad.motivational.telegram.bot.infrastructure.web.controller.ApiTestConstants.TelegramApiImpl.WEBHOOK_RESPONSE_500_PATH;
import static it.vrad.motivational.telegram.bot.infrastructure.web.controller.ApiTestConstants.USER_TELEGRAM_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Slice tests for the {@link TelegramApiImpl} REST controller.
 * <p>
 * These tests verify the behavior of the Telegram webhook endpoint, ensuring that
 * valid requests are processed and dispatched correctly, and invalid requests are handled as expected.
 * <p>
 * Uses {@link TelegramApiImplTestConfig} to provide required test beans and mocks.
 */
@Import(TelegramApiImplTestConfig.class)
@WebMvcTest(TelegramApiImpl.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TelegramApiImplIT {
    public static final String WEBHOOK_ENDPOINT = "/api/telegram/webhook";

    private final MockMvc mockMvc;
    private final UpdateDispatcherImpl updateDispatcher;

    @BeforeEach
    public void resetMock() {
        reset(updateDispatcher);
    }

    @DisplayName("POST " + WEBHOOK_ENDPOINT)
    @Nested
    class Webhook {
        @Test
        @DisplayName("should return true and dispatch update when the request is valid")
        public void whenValidRequest_returnsTrue() throws Exception {
            String validRequest = readSuccessRequestBody();

            performWebhookPost(validRequest)
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));

            verify(updateDispatcher).dispatch(any(Update.class));
        }

        @Test
        @DisplayName("should return 500 and not dispatch when body is missing")
        public void whenBodyMissing_throwsException() throws Exception {
            String expectedResponse = readGenericErrorResponse();

            performWebhookPost(StringUtils.EMPTY)
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json(expectedResponse));

            verify(updateDispatcher, never()).dispatch(any(Update.class));
        }

        @Test
        @DisplayName("should return 204, unwrap exception and throw NoSuchUserException when user not found")
        public void whenUserNotFound_throwNoSuchUserException() throws Exception {
            NoSuchUserException noSuchUserException = new NoSuchUserException(USER_TELEGRAM_ID);

            doThrow(new UncheckedWrapperException(noSuchUserException))
                    .when(updateDispatcher).dispatch(any(Update.class));

            String validRequest = readSuccessRequestBody();
            performWebhookPost(validRequest)
                    .andExpect(status().isNoContent())
                    .andExpect(result ->
                            assertThat(result.getResolvedException())
                                    .isInstanceOf(NoSuchUserException.class)
                                    .hasFieldOrPropertyWithValue("telegramId", USER_TELEGRAM_ID)
                    );

            verify(updateDispatcher).dispatch(any(Update.class));
        }

        // --------------------------------------
        // Webhook Test helpers
        // --------------------------------------

        private String readSuccessRequestBody() {
            return FileTestUtility.readResourceAsString(WEBHOOK_REQUEST_200_PATH);
        }

        private String readGenericErrorResponse() {
            return FileTestUtility.readResourceAsString(WEBHOOK_RESPONSE_500_PATH);
        }

        private ResultActions performWebhookPost(String body) throws Exception {
            return mockMvc.perform(
                    post(WEBHOOK_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body)
            );
        }
    }


}
