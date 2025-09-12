package it.vrad.motivational.telegram.bot.infrastructure.exception.util;

import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.infrastructure.exception.constants.ExceptionMessageConstants;
import it.vrad.motivational.telegram.bot.infrastructure.util.DateUtility;

import java.time.OffsetDateTime;

/**
 * Helper class for formatting and generating exception and error messages
 * used throughout the application for user feedback.
 */
public class ExceptionFEMessageHelper {

    /**
     * Formats a generic error message for the frontend.
     *
     * @param errorCode the error code
     * @return the formatted frontend error message
     */
    public static String getFEGenericErrorMessage(String errorCode) {
        return String.format(ExceptionMessageConstants.FE_GENERIC_ERROR_MESSAGE, errorCode);
    }


    /**
     * Returns a frontend message with information when the user is not found
     *
     * @return the formatted frontend message
     */
    public static String getFEUserNotFoundMessage() {
        return String.format(ExceptionMessageConstants.FE_USER_NOT_FOUND_MESSAGE, CommandConstants.Initial.TEXT);
    }

    /**
     * Formats a frontend cooldown message with the ending time.
     *
     * @param endingTime the cooldown ending time
     * @return the formatted cooldown message
     */
    public static String getFECooldownMessage(OffsetDateTime endingTime) {
        // Format the ending time using the cooldown time formatter
        String endingTimeFormatted = endingTime.format(DateUtility.COOLDOWN_TIME_FORMATTER);
        return String.format(ExceptionMessageConstants.FE_COOLDOWN_MESSAGE_TEMPLATE, endingTimeFormatted);
    }

    /**
     * Returns a frontend message for an invalid user based on their role.
     *
     * @param userRole the user's role
     * @param chatId   the chat ID
     * @return the formatted frontend message
     */
    public static String getFEUserNotValidMessage(UserRole userRole, Long chatId) {
        // Return a banned message for banned users, otherwise a generic error message
        return switch (userRole) {
            case BANNED -> ExceptionMessageConstants.FE_USER_BANNED_MESSAGE;
            default -> getFEGenericErrorMessage(ExceptionUtility.generateTrackingErrorCode(chatId));
        };
    }


}
