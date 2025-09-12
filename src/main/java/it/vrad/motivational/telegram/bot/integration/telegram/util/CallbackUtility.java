package it.vrad.motivational.telegram.bot.integration.telegram.util;

import it.vrad.motivational.telegram.bot.core.model.constants.CallbackConstants;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionUtility;
import it.vrad.motivational.telegram.bot.core.model.CallbackDataDetails;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.CallbackQuery;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * Utility class for Telegram callback-related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CallbackUtility {
    private static final String CALLBACK_QUERY_TYPE_NAME = "callbackQuery";

    /**
     * Gets the chat ID from a CallbackQuery.
     *
     * @param callbackQuery the callback query
     * @return chat ID
     * @throws java.util.NoSuchElementException if chat ID is not found
     */
    public static Long getChatId(CallbackQuery callbackQuery) {
        return Optional.ofNullable(callbackQuery)
                .map(CallbackQuery::getMessage)
                .map(MessageUtility::getChatId)
                .orElseThrow(() -> ExceptionUtility.createNoSuchElementException("Chat ID", CALLBACK_QUERY_TYPE_NAME));
    }

    /**
     * Builds CallbackDataDetails for callback handling.
     *
     * @param key       callback key
     * @param stepIndex step index
     * @param data      callback data
     * @return CallbackDataDetails object
     */
    public static CallbackDataDetails buildDataDetails(String key, int stepIndex, String data) {
        return CallbackDataDetails.builder()
                .key(key)
                .stepIndex(stepIndex)
                .data(data)
                .build();
    }

    /**
     * Creates a page reference string for callback.
     *
     * @param pageReference page reference
     * @return formatted page reference
     */
    public static String createPageReference(String pageReference) {
        return CallbackConstants.PAGE_REFERENCE_PREFIX + pageReference;
    }

    /**
     * Creates a button reference string for callback.
     *
     * @param commandReference command reference
     * @return formatted button reference
     */
    public static String createButtonReference(String commandReference) {
        return CallbackConstants.BUTTON_REFERENCE_PREFIX + commandReference;
    }

    /**
     * Updates the sender of a message from a callback query.
     *
     * @param callbackQuery the callback query
     * @return updated message
     */
    public static Message updateMessageSenderFromCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        message.setFrom(callbackQuery.getFrom());
        return message;
    }
}
