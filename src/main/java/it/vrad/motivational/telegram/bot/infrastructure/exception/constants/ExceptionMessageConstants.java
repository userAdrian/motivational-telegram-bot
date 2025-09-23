package it.vrad.motivational.telegram.bot.infrastructure.exception.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Centralized constants for exception and error messages used throughout the application.
 * Includes templates for logging, frontend messages, error codes, and descriptions.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessageConstants {

    // --- Generic Error Messages ---
    public static final String LOG_GENERIC_ERROR_MESSAGE = "Error occurred";
    public static final String LOG_GENERIC_ERROR_MESSAGE_TEMPLATE = "Error occurred with trackingErrorCode: %s";
    public static final String FE_GENERIC_ERROR_MESSAGE = "Please try again or contact assistance.\nError code: %s";
    public static final String GENERIC_ERROR_CODE = "500";

    // --- API Exception Messages ---
    public static final String LOG_API_EXCEPTION_MESSAGE_TEMPLATE =
            "\n\tError occurred during api call: %s.\n" +
            "\t\tTracking error code: %s\n" +
            "\t\tError message: %s\n" +
            "\t\tError code: %s";
    public static final String INTERNAL_SERVER_ERROR_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION = HttpStatus.INTERNAL_SERVER_ERROR.name();

    // --- Command & User Messages ---
    public static final String LOG_UNRECOGNIZED_COMMAND_MESSAGE = "Unrecognized command. ChatId: %s, Command: %s";
    public static final String FE_UNRECOGNIZED_COMMAND_MESSAGE = "Unrecognized command. Say what?";

    public static final String LOG_USER_NOT_FOUND_MESSAGE_TEMPLATE = "User with telegram ID %d not found";
    public static final String FE_USER_NOT_FOUND_MESSAGE = "Please send the command %s and try again";

    public static final String LOG_USER_NOT_VALID_TEMPLATE = "User with telegram ID (%d) has a not valid role (%s)";
    public static final String FE_USER_BANNED_MESSAGE = "You are banned from this bot. Go away!";

    // --- Cooldown & Job Messages ---
    public static final String LOG_COOLDOWN_NOT_FOUND_TEMPLATE = "Cooldown with ID (%s) not found";
    public static final String LOG_FAILED_JOB_EXECUTION_TEMPLATE = "Failed to execute the job '%s': error acquiring distributed lock or running notification task";
    public static final String FE_COOLDOWN_MESSAGE_TEMPLATE = "The command is on cooldown until\n%s";

    // --- Reserved Command Messages ---
    public static final String LOG_RESERVED_COMMAND_MESSAGE_TEMPLATE = "The command %s is reserved to roles: %s";
    public static final String FE_RESERVED_COMMAND_MESSAGE = "Only a select few people can use this command.";

    // --- Element Not Found Messages ---
    public static final String LOG_ELEMENT_NOT_FOUND_TEMPLATE = "%s not found in the %s";

    // --- Phrase Messages ---
    public static final String LOG_NO_PHRASES_FOUND_MESSAGE = "No phrases found";
}
