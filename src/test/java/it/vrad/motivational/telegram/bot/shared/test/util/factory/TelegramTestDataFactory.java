package it.vrad.motivational.telegram.bot.shared.test.util.factory;

import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Chat;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.CHAT_TELEGRAM_ID;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.CHAT_TYPE;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.SAVE_CHAT_TELEGRAM_ID;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.SAVE_CHAT_TYPE;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.SAVE_USER_TELEGRAM_ID;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.USER_TELEGRAM_ID;

/**
 * Factory utility for creating Telegram-related test objects used across unit tests.
 * <p>
 * Provides simple builders for Message, User and Chat instances with predefined
 * values suitable for tests.
 * <p>
 * The class is non-instantiable and exposes only static helper methods.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TelegramTestDataFactory {

    // === Message ===
    public static Message createGenericMessage() {
        Message message = new Message();

        message.setFrom(createGenericUser());
        message.setChat(createGenericChat());

        return message;
    }

    public static Message createGenericMessageNewUser() {
        Message message = new Message();

        message.setFrom(createGenericNewUser());
        message.setChat(createGenericNewChat());

        return message;
    }

    // === User ===
    public static User createGenericUser() {
        User user = new User();

        user.setId(USER_TELEGRAM_ID);

        return user;
    }

    public static User createGenericNewUser() {
        User user = new User();

        user.setId(SAVE_USER_TELEGRAM_ID);

        return user;
    }

    // === Chat ===
    public static Chat createGenericChat() {
        Chat chat = new Chat();

        chat.setId(CHAT_TELEGRAM_ID);
        chat.setType(CHAT_TYPE.getTelegramValue());

        return chat;
    }

    public static Chat createGenericNewChat() {
        Chat chat = new Chat();

        chat.setId(SAVE_CHAT_TELEGRAM_ID);
        chat.setType(SAVE_CHAT_TYPE.getTelegramValue());

        return chat;
    }
}
