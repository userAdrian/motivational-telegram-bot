package it.vrad.motivational.telegram.bot.integration.telegram.util;


import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.MessageConstants;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Chat;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.PhotoSize;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

/**
 * Utility class for Telegram message-related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtility {
    private static final String MESSAGE_TYPE_NAME = "message";

    /**
     * Gets the chat ID from a message.
     *
     * @param message the Telegram message
     * @return chat ID
     * @throws NoSuchElementException if chat or chat ID is not present
     */
    public static Long getChatId(Message message) {
        return Optional.ofNullable(message)
                .map(Message::getChat)
                .map(Chat::getId)
                .orElseThrow(() -> ExceptionUtility.createNoSuchElementException("Chat ID", MESSAGE_TYPE_NAME));
    }

    /**
     * Gets the user ID from a message.
     *
     * @param message the Telegram message
     * @return user ID
     * @throws NoSuchElementException if user or user ID is not present
     */
    public static Long getUserId(Message message) {
        return Optional.ofNullable(message)
                .map(Message::getFrom)
                .map(User::getId)
                .orElseThrow(() -> ExceptionUtility.createNoSuchElementException("User ID", MESSAGE_TYPE_NAME));
    }

    /**
     * Gets the file ID of the last photo in a message. It is the one with the biggest resolution
     *
     * @param message the Telegram message
     * @return photo file ID
     * @throws NoSuchElementException if photo or file ID is not present
     */
    public static String getLastPhotoId(Message message) {
        return Optional.ofNullable(message)
                .map(Message::getPhoto)
                .filter(CollectionUtils::isNotEmpty)
                .map(List::getLast)
                .map(PhotoSize::getFileId)
                .orElseThrow(() -> ExceptionUtility.createNoSuchElementException("Photo ID", MESSAGE_TYPE_NAME));
    }

    /**
     * Formats a phrase for display.
     *
     * @param phraseDto the phrase DTO
     * @return formatted phrase string
     * @throws NullPointerException if phraseDto is null
     */
    public static String formatPhrase(PhraseDto phraseDto) {
        Objects.requireNonNull(phraseDto);

        return String.format(getPhraseTemplate(phraseDto), phraseDto.getText(), phraseDto.getAuthorFullName());
    }

    /**
     * Gets the phrase template based on biography flag.
     *
     * @param phraseDto the phrase DTO
     * @return template string
     */
    private static String getPhraseTemplate(PhraseDto phraseDto) {
        return BooleanUtils.isTrue(phraseDto.isBiography())
                ? MessageConstants.PHRASE_BIOGRAPHY_TEMPLATE
                : MessageConstants.PHRASE_TEMPLATE;
    }

    /**
     * Checks if the given text is a bot command.
     *
     * @param text the text to check
     * @return true if command, false otherwise
     */
    public static boolean isACommand(String text) {
        if (text != null) {
            return text.startsWith(CommandConstants.BOT_COMMAND_PREFIX) && !text.contains(StringUtils.SPACE);
        }

        return false;
    }

    /**
     * Check if the text is a valid command and return it, otherwise return a default value.
     * See also {@link MessageUtility#isACommand(String)}
     *
     * @param text the text to check
     * @return the {@code text} if it is a valid command, otherwise {@link CommandConstants#NOT_AVAILABLE}
     */
    public static String resolveCommandIfPresent(String text) {
        if (isACommand(text)) {
            return text;
        }

        return CommandConstants.NOT_AVAILABLE;
    }

}
