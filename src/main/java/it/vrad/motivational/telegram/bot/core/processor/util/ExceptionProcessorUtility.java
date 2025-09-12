package it.vrad.motivational.telegram.bot.core.processor.util;

import it.vrad.motivational.telegram.bot.core.exception.ContextAwareException;
import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;
import it.vrad.motivational.telegram.bot.core.exception.MotivationalTelegramBotException;
import it.vrad.motivational.telegram.bot.core.exception.UnrecognizedCommandException;

/**
 * Utility class for handling exceptions in processors.
 * Converts exceptions to appropriate runtime exceptions for further handling.
 */
public class ExceptionProcessorUtility {

    /**
     * Handles the given exception in a context-aware manner.
     * Sets chatId for context-aware exceptions and wraps others as needed.
     *
     * @param ex the exception to handle
     * @param chatId the chat ID for context
     */
    public static void handleUpdateProcessorException(Exception ex, Long chatId) {
        switch (ex) {
            case ContextAwareException chatAwareEx ->{
                // Set the chat ID for context-aware exceptions and rethrow
                chatAwareEx.setChatId(chatId);
                throw (RuntimeException) chatAwareEx;
            }
            case IntegrationApiException integrationApiEx -> throw integrationApiEx;
            case UnrecognizedCommandException unrecognizedEx -> throw unrecognizedEx;
            default -> throw new MotivationalTelegramBotException(ex.getMessage(), chatId, ex);
        }
    }
}
