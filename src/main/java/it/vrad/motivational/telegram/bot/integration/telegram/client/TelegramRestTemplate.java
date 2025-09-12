package it.vrad.motivational.telegram.bot.integration.telegram.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.TelegramErrorResponse;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.TelegramResponse;

import it.vrad.motivational.telegram.bot.integration.telegram.exception.factory.IntegrationExceptionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Utility component for performing HTTP requests to the Telegram Bot API using {@link RestTemplate}.
 * Handles response parsing and error handling for integration with Telegram.
 */
@Slf4j
@Component
public class TelegramRestTemplate {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public TelegramRestTemplate(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Performs an HTTP exchange with the Telegram API and parses the response as a {@link TelegramResponse}.
     *
     * @param httpMethod   the HTTP method to use
     * @param url          the endpoint URL
     * @param request      the HTTP request entity
     * @param responseType the expected response type reference
     * @param chatId       the chat ID for logging or error context
     * @return the result object parsed from the Telegram response, or {@code null} if not present
     */
    public <T> T performExchange(HttpMethod httpMethod, String url, HttpEntity<?> request,
                                 ParameterizedTypeReference<TelegramResponse<T>> responseType, Long chatId) {
        try {
            // Execute the HTTP request and extract the result from the Telegram response
            TelegramResponse<T> response = restTemplate.exchange(
                    url,
                    httpMethod,
                    request,
                    responseType).getBody();

            return response != null ? response.getResult() : null;
        } catch (Exception exception) {
            // Handle and wrap exceptions as integration API exceptions
            throw integrationApiException(exception, chatId, url);
        }
    }

    /**
     * Performs an HTTP exchange with the Telegram API and parses the response as the given class type.
     *
     * @param httpMethod   the HTTP method to use
     * @param url          the endpoint URL
     * @param request      the HTTP request entity
     * @param responseType the expected response class
     * @param chatId       the chat ID for logging or error context
     * @return the response body parsed as the given type, or {@code null} if not present
     */
    public <T> T performExchange(HttpMethod httpMethod, String url, HttpEntity<?> request, Class<T> responseType, Long chatId) {
        try {
            // Execute the HTTP request and return the response body
            return restTemplate.exchange(
                    url,
                    httpMethod,
                    request,
                    responseType).getBody();
        } catch (Exception exception) {
            // Handle and wrap exceptions as integration API exceptions
            throw integrationApiException(exception, chatId, url);
        }
    }

    /**
     * Builds an appropriate {@link IntegrationApiException} for integration errors.
     *
     * @param exception the thrown exception
     * @param chatId    the chat ID for context
     * @param url       the endpoint URL
     * @return a wrapped integration exception
     */
    private IntegrationApiException integrationApiException(Exception exception, Long chatId, String url) {
        return switch (exception) {
            case HttpStatusCodeException httpStatusCodeException ->
                    createIntegrationApiException(chatId, url, httpStatusCodeException);
            default -> IntegrationExceptionFactory.buildGenericIntegrationApiException(chatId, url, exception);
        };
    }

    /**
     * Creates an {@link IntegrationApiException} from an HTTP status exception, extracting Telegram error details if possible.
     *
     * @param chatId the chat ID for context
     * @param url    the endpoint URL
     * @param ex     the HTTP status code exception
     * @return an integration API exception with Telegram error details if available
     */
    private IntegrationApiException createIntegrationApiException(Long chatId, String url, HttpStatusCodeException ex) {
        Optional<TelegramErrorResponse> errorResponse = getTelegramErrorResponse(ex);
        if (errorResponse.isPresent()) {
            // Build exception with Telegram error details
            return IntegrationExceptionFactory.buildIntegrationApiException(errorResponse.get(), chatId, url, ex);
        }

        // Fallback to a generic integration exception
        return IntegrationExceptionFactory.buildGenericIntegrationApiException(chatId, url, ex);
    }

    /**
     * Attempts to parse a {@link TelegramErrorResponse} from an HTTP status exception.
     *
     * @param httpStatusCodeException the HTTP status code exception
     * @return an optional containing the parsed error response, or empty if parsing fails
     */
    private Optional<TelegramErrorResponse> getTelegramErrorResponse(HttpStatusCodeException httpStatusCodeException) {
        try {
            // Parse the error response body as TelegramErrorResponse
            return Optional.of(
                    objectMapper.readValue(httpStatusCodeException.getResponseBodyAsString(), TelegramErrorResponse.class)
            );
        } catch (JsonProcessingException ex) {
            log.error("Error parsing Telegram error response: {}", ex.getMessage(), ex);
        }

        return Optional.empty();
    }

}
