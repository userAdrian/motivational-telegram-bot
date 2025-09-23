package it.vrad.motivational.telegram.bot.core.processor.util;

import it.vrad.motivational.telegram.bot.core.exception.ContextAwareException;
import it.vrad.motivational.telegram.bot.core.exception.MotivationalTelegramBotException;
import it.vrad.motivational.telegram.bot.core.exception.UncheckedWrapperException;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;

/**
 * Utility class for handling exceptions in processors.
 * Converts exceptions to appropriate runtime exceptions for further handling.
 */
public class ExceptionProcessorUtility {

    /**
     * Handles the given exception in a context-aware manner.
     * Sets chatId for context-aware exceptions and wraps others as needed.
     *
     * @param ex     the exception to handle
     * @param chatId the chat ID for context
     */
    public static void handleUpdateProcessorException(Exception ex, Long chatId) {
        // Unwrap the exception to set chatId if required
        ex = ExceptionUtility.unwrapException(ex);

        // Wrap checked exceptions in UncheckedWrapperException if they need to be propagated
        switch (ex) {
            case IntegrationApiException integrationApiEx -> throw integrationApiEx;
            case ContextAwareException contextAwareEx -> {
                // Set the chat ID for context-aware exceptions and rethrow
                contextAwareEx.setChatId(chatId);
                throw new UncheckedWrapperException(ex);
            }
            default -> throw new MotivationalTelegramBotException(ex.getMessage(), chatId, ex);
        }
    }
}
