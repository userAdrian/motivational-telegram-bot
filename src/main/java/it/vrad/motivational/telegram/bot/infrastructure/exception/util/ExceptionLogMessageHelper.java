package it.vrad.motivational.telegram.bot.infrastructure.exception.util;

import it.vrad.motivational.telegram.bot.infrastructure.exception.constants.ExceptionMessageConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;

import java.util.Objects;
import java.util.Set;

/**
 * Helper class for formatting and generating exception and error messages
 * used throughout the application for logging
 */
public class ExceptionLogMessageHelper {

    /**
     * Formats an integration API error message for logging.
     *
     * @param exception         the integration API exception
     * @param trackingErrorCode the tracking error code
     * @return the formatted error message
     */
    public static String getIntegrationApiErrorMessage(IntegrationApiException exception, String trackingErrorCode) {
        // Format the API exception message using the template and exception details
        return String.format(ExceptionMessageConstants.LOG_API_EXCEPTION_MESSAGE_TEMPLATE,
                exception.getApi(), trackingErrorCode, exception.getDescription(), exception.getErrorCode());
    }

    /**
     * Formats a generic error message for logging.
     *
     * @param trackingErrorCode the tracking error code
     * @return the formatted log message
     */
    public static String getGenericErrorMessage(String trackingErrorCode) {
        return String.format(ExceptionMessageConstants.LOG_GENERIC_ERROR_MESSAGE_TEMPLATE, trackingErrorCode);
    }

    /**
     * Formats a message for an unrecognized command.
     *
     * @param chatId  the chat ID
     * @param command the unrecognized command
     * @return the formatted message
     */
    public static String getUnrecognizedCommandMessage(Long chatId, String command) {
        return String.format(ExceptionMessageConstants.LOG_UNRECOGNIZED_COMMAND_MESSAGE, chatId, command);
    }

    /**
     * Formats a message indicating a user was not found.
     *
     * @param telegramUserId the Telegram user ID
     * @return the formatted message
     */
    public static String getUserNotFoundMessage(Long telegramUserId) {
        return String.format(ExceptionMessageConstants.LOG_USER_NOT_FOUND_MESSAGE_TEMPLATE, telegramUserId);
    }

    /**
     * Formats a message for a failed job execution.
     *
     * @param jobName the job name
     * @return the formatted log message
     */
    public static String getJobExecutionMessage(String jobName) {
        return String.format(ExceptionMessageConstants.LOG_FAILED_JOB_EXECUTION_TEMPLATE, jobName);
    }

    /**
     * Formats a message indicating a user is not valid.
     *
     * @param userTelegramId the user's Telegram ID
     * @param userRole       the user's role
     * @return the formatted log message
     */
    public static String getUserNotValidMessage(Long userTelegramId, UserRole userRole) {
        Objects.requireNonNull(userRole);
        return String.format(ExceptionMessageConstants.LOG_USER_NOT_VALID_TEMPLATE, userTelegramId, userRole.name());
    }

    /**
     * Formats a message for a reserved command.
     *
     * @param command   the command
     * @param userRoles the set of user roles
     * @return the formatted log message
     */
    public static String getReservedCommandMessage(String command, Set<UserRole> userRoles) {
        return String.format(ExceptionMessageConstants.LOG_RESERVED_COMMAND_MESSAGE_TEMPLATE, command, userRoles);
    }

    /**
     * Formats a message indicating an element was not found.
     *
     * @param typeNotFound the type of element not found
     * @param source       the source or context
     * @return the formatted log message
     */
    public static String getElementNotFoundMessage(String typeNotFound, String source) {
        return String.format(ExceptionMessageConstants.LOG_ELEMENT_NOT_FOUND_TEMPLATE, typeNotFound, source);
    }

    /**
     * Formats a message indicating a cooldown was not found.
     *
     * @param id the cooldown ID
     * @return the formatted log message
     */
    public static String getCooldownNotFound(Long id) {
        return String.format(ExceptionMessageConstants.LOG_COOLDOWN_NOT_FOUND_TEMPLATE, id);
    }

}
