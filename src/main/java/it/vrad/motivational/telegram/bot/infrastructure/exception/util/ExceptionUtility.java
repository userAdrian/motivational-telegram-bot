package it.vrad.motivational.telegram.bot.infrastructure.exception.util;

import it.vrad.motivational.telegram.bot.core.exception.MotivationalTelegramBotException;
import it.vrad.motivational.telegram.bot.core.exception.UncheckedWrapperException;
import it.vrad.motivational.telegram.bot.integration.telegram.TelegramConstants;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Utility class for exception handling and validation helpers.
 * Provides methods for generating tracking codes, validating arguments,
 * and creating exception instances with formatted messages.
 */
public class ExceptionUtility {

    /**
     * Generates a tracking error code using the chat ID and a random alphanumeric string.
     *
     * @param chatId the chat ID
     * @return the generated tracking error code
     */
    public static String generateTrackingErrorCode(Long chatId) {
        // Use the first 5 digits of chatId and append a random string
        return String.valueOf(chatId).substring(0, 5) + "-" + RandomStringUtils.insecure().nextAlphanumeric(15);
    }

    /**
     * Ensures the provided string is not null or blank.
     *
     * @param string the string to check
     * @return the original string if not blank
     * @throws IllegalArgumentException if the string is null or blank
     */
    public static String requireNonBlank(String string) {
        if (string == null || string.isBlank()) {
            throw new IllegalArgumentException("String must not be blank");
        }
        return string;
    }

    /**
     * Ensures the provided collection is not null or empty.
     *
     * @param collection the collection to check
     * @param <T>        the type of elements in the collection
     * @return the original collection if not empty
     * @throws IllegalArgumentException if the collection is null or empty
     */
    public static <T> Collection<T> requireNonEmpty(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Collection must not be empty");
        }
        return collection;
    }

    /**
     * Ensures the provided map is not null or empty.
     *
     * @param map the map to check
     * @param <K> the type of keys in the map
     * @param <V> the type of values in the map
     * @return the original map if not empty
     * @throws IllegalArgumentException if the map is null or empty
     */
    public static <K, V> Map<K, V> requireNonEmpty(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException("Map must not be empty");
        }
        return map;
    }

    /**
     * Creates a {@link NoSuchElementException} with a formatted message for a missing element.
     *
     * @param typeNotFound the type of element not found
     * @param source       the source or context
     * @return the created {@link NoSuchElementException}
     */
    public static NoSuchElementException createNoSuchElementException(String typeNotFound, String source) {
        // Format the message using the helper
        String message = ExceptionLogMessageHelper.getElementNotFoundMessage(typeNotFound, source);
        return new NoSuchElementException(message);
    }

    /**
     * Checks if the given message indicates a callback query already processed
     *
     * @param message the message to check
     * @return true if the message contains the <br>
     * {@link TelegramConstants#MESSAGE_NOT_MODIFIED_ERROR} text, false otherwise
     */
    public static boolean isCallbackQueryProcessed(String message) {
        return message != null && message.contains(TelegramConstants.MESSAGE_NOT_MODIFIED_ERROR);
    }

    /**
     * Unwraps an exception that may be wrapped in an {@link UncheckedWrapperException}.
     * <p>
     * If the given exception is an {@code UncheckedWrapperException}, recursively unwraps its cause until
     * an {@code Exception} is found or a non-{@code Exception} cause is encountered. If the cause is not an
     * {@code Exception}, wraps it in a {@link MotivationalTelegramBotException}. If the input is not an
     * {@code UncheckedWrapperException}, returns the input as-is.
     *
     * @param ex the exception to unwrap
     * @return the unwrapped exception, or a wrapped {@link MotivationalTelegramBotException} if the cause is not an Exception
     */
    public static Exception unwrapException(Exception ex) {
        Exception current = ex;
        while (current instanceof UncheckedWrapperException uw) {
            Throwable cause = uw.getCause();
            if (cause == null) {
                return new MotivationalTelegramBotException("Unknown cause (null) in UncheckedWrapperException", uw);
            }
            if (cause instanceof Exception exception) {
                current = exception;
            } else {
                return new MotivationalTelegramBotException(
                        "Non-Exception cause in UncheckedWrapperException: " + cause.getClass().getName(),
                        cause
                );
            }
        }
        return current;
    }

}
