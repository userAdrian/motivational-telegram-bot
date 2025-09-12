package it.vrad.motivational.telegram.bot.integration.telegram.exception.factory;

import it.vrad.motivational.telegram.bot.infrastructure.exception.constants.ExceptionMessageConstants;
import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.TelegramErrorResponse;


/**
 * Utility class for building {@link IntegrationApiException} instances for Telegram integration errors.
 */
public class IntegrationExceptionFactory {

    /**
     * Builds an {@link IntegrationApiException} using details from a {@link TelegramErrorResponse}.
     *
     * @param telegramErrorResponse the Telegram error response
     * @param chatId                the chat ID for context
     * @param api                   the API endpoint
     * @param throwable             the original exception
     * @return the constructed {@link IntegrationApiException}
     */
    public static IntegrationApiException buildIntegrationApiException(TelegramErrorResponse telegramErrorResponse,
                                                                       Long chatId, String api, Throwable throwable) {
        // Delegate to the detailed builder with error code and description from the response
        return buildIntegrationApiException(chatId, api, telegramErrorResponse.errorCode(),
                telegramErrorResponse.description(), throwable);
    }

    /**
     * Builds a generic {@link IntegrationApiException} with internal server error details.
     *
     * @param chatId    the chat ID for context
     * @param api       the API endpoint or method
     * @param throwable the original exception
     * @return the constructed {@link IntegrationApiException}
     */
    public static IntegrationApiException buildGenericIntegrationApiException(Long chatId, String api, Throwable throwable) {
        // Use internal server error code and description for generic errors
        return buildIntegrationApiException(chatId, api, ExceptionMessageConstants.INTERNAL_SERVER_ERROR_CODE,
                ExceptionMessageConstants.INTERNAL_SERVER_ERROR_DESCRIPTION, throwable);
    }

    /**
     * Builds an {@link IntegrationApiException} with explicit error code and description.
     *
     * @param chatId      the chat ID for context
     * @param api         the API endpoint or method
     * @param errorCode   the error code
     * @param description the error description
     * @param throwable   the original exception
     * @return the constructed {@link IntegrationApiException}
     */
    private static IntegrationApiException buildIntegrationApiException(Long chatId, String api, String errorCode,
                                                                        String description, Throwable throwable) {
        return IntegrationApiException.builder()
                .chatId(chatId)
                .api(api)
                .errorCode(errorCode)
                .description(description)
                .throwable(throwable)
                .build();
    }

}
