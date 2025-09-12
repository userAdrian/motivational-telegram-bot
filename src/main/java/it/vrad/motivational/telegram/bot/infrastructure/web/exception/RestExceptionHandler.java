package it.vrad.motivational.telegram.bot.infrastructure.web.exception;

import it.vrad.motivational.telegram.bot.infrastructure.exception.constants.ExceptionMessageConstants;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionUtility;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionFEMessageHelper;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionLogMessageHelper;
import it.vrad.motivational.telegram.bot.core.exception.CooldownException;
import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;
import it.vrad.motivational.telegram.bot.core.exception.MotivationalTelegramBotException;
import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.ReservedCommandException;
import it.vrad.motivational.telegram.bot.core.exception.UnrecognizedCommandException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Global exception handler for REST controllers.
 * Handles various custom exceptions and sends appropriate frontend messages via Telegram,
 * as well as logging error details for diagnostics.
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    private final TelegramIntegrationApi telegramIntegrationApi;

    public RestExceptionHandler(TelegramIntegrationApi telegramIntegrationApi) {
        this.telegramIntegrationApi = telegramIntegrationApi;
    }

    /**
     * Handles integration API exceptions, logs the error, and sends a generic frontend error message.
     *
     * @param integrationApiException the exception thrown during Telegram API integration
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(IntegrationApiException.class)
    public void handleIntegrationApiException(IntegrationApiException integrationApiException) {
        Long chatId = integrationApiException.getChatId();
        String trackingErrorCode = ExceptionUtility.generateTrackingErrorCode(chatId);

        // Prepare log and frontend messages
        String logMessage = ExceptionLogMessageHelper.getIntegrationApiErrorMessage(integrationApiException, trackingErrorCode);
        String feMessage = ExceptionFEMessageHelper.getFEGenericErrorMessage(trackingErrorCode);

        // If the callback query was already processed, do not send a message to the user
        if (ExceptionUtility.isCallbackQueryProcessed(integrationApiException.getMessage())) {
            chatId = null;
        }

        logAndSend(LogLevel.ERROR, chatId, logMessage, integrationApiException, feMessage);
    }

    /**
     * Handles generic bot exceptions, logs the error, and sends a generic frontend error message.
     *
     * @param ex the custom bot exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(MotivationalTelegramBotException.class)
    public void handleMotivationalTelegramBotException(MotivationalTelegramBotException ex) {
        Long chatId = ex.getChatId();
        String trackingErrorCode = chatId != null ? ExceptionUtility.generateTrackingErrorCode(chatId) : StringUtils.EMPTY;

        String logMessage = ExceptionLogMessageHelper.getGenericErrorMessage(trackingErrorCode);
        String feMessage = ExceptionFEMessageHelper.getFEGenericErrorMessage(trackingErrorCode);

        logAndSend(LogLevel.ERROR, chatId, logMessage, ex, feMessage);
    }

    /**
     * Handles cases where a user is not found, logs the error, and sends a frontend message.
     *
     * @param ex the exception indicating no such user exists
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoSuchUserException.class)
    public void handleNoSuchUserException(NoSuchUserException ex) {
        logAndSend(LogLevel.ERROR, ex.getChatId(), ex.getMessage(), ex, ExceptionFEMessageHelper.getFEUserNotFoundMessage());
    }

    /**
     * Handles cases where a user is not valid, logs the error, and sends a frontend not-valid message.
     *
     * @param ex the exception indicating the user is not valid
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(UserNotValidException.class)
    public void handleUserNotValidException(UserNotValidException ex) {
        logAndSend(LogLevel.ERROR, ex.getChatId(), ex.getMessage(), ex,
                ExceptionFEMessageHelper.getFEUserNotValidMessage(ex.getUserRole(), ex.getChatId()));
    }

    /**
     * Handles cooldown exceptions, logs a warning, and sends a frontend cooldown message.
     *
     * @param ex the cooldown exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(CooldownException.class)
    public void handleCooldownException(CooldownException ex) {
        String feMessage = ExceptionFEMessageHelper.getFECooldownMessage(ex.getEndingTime());
        logAndSend(LogLevel.WARN, ex.getChatId(), ex.toString(), ex, feMessage);
    }

    /**
     * Handles unrecognized command exceptions, logs a warning, and sends a frontend message.
     *
     * @param ex the unrecognized command exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(UnrecognizedCommandException.class)
    public void handleUnrecognizedCommandException(UnrecognizedCommandException ex) {
        Long chatId = ex.getChatId();

        // Prepare a log message for the unrecognized command
        String logMessage = ExceptionLogMessageHelper.getUnrecognizedCommandMessage(chatId, ex.getCommand());

        logAndSend(LogLevel.WARN, chatId, logMessage, ex, ExceptionMessageConstants.FE_UNRECOGNIZED_COMMAND_MESSAGE);
    }

    /**
     * Handles reserved command exceptions, logs a warning, and sends a frontend reserved command message.
     *
     * @param ex the reserved command exception
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(ReservedCommandException.class)
    public void handleReservedCommandException(ReservedCommandException ex) {
        Long chatId = ex.getChatId();

        logAndSend(LogLevel.WARN, chatId, ex.getMessage(), ex, ExceptionMessageConstants.FE_RESERVED_COMMAND_MESSAGE);
    }

    /**
     * Handles all uncaught exceptions, logs the error, and returns a generic error response.
     *
     * @param throwable the uncaught exception
     * @return an {@link ErrorResponse} with a generic error message and code
     */
    @ResponseStatus
    @ExceptionHandler(Throwable.class)
    public ErrorResponse handleException(Throwable throwable) {
        String errorMessage = ExceptionMessageConstants.LOG_GENERIC_ERROR_MESSAGE;
        log.error(errorMessage, throwable);

        return new ErrorResponse(errorMessage, ExceptionMessageConstants.GENERIC_ERROR_CODE);
    }

    /**
     * Logs the error and sends a frontend message to the user if chatId is present.
     *
     * @param logLevel   the log level (WARN or ERROR)
     * @param chatId     the chat ID to send the message to
     * @param logMessage the message to log
     * @param ex         the exception to log
     * @param feMessage  the frontend message to send
     */
    private void logAndSend(LogLevel logLevel, Long chatId, String logMessage, Exception ex, String feMessage) {
        log(logLevel, logMessage, ex);
        // Only send a message if chatId is present
        if (chatId != null) {
            telegramIntegrationApi.sendSimpleMessage(chatId, feMessage);
        }
    }

    /**
     * Logs the message at the specified log level.
     *
     * @param logLevel   the log level (WARN or ERROR)
     * @param logMessage the message to log
     * @param ex         the exception to log
     */
    private void log(LogLevel logLevel, String logMessage, Exception ex) {
        if (LogLevel.WARN.equals(logLevel)) {
            log.warn(logMessage, ex);
        } else {
            log.error(logMessage, ex);
        }
    }
}
