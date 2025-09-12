package it.vrad.motivational.telegram.bot.core.model.enums.persistence;

import it.vrad.motivational.telegram.bot.integration.telegram.TelegramConstants;
import lombok.Getter;

@Getter
public enum UserRole {
    ALL(null), //any role
    BANNED(null), //admin ban the user
    KICKED(TelegramConstants.CHAT_MEMBER_KICKED_STATUS), //user block the bot
    MEMBER(TelegramConstants.CHAT_MEMBER_MEMBER_STATUS), //user unblock the bot
    ADMIN(TelegramConstants.CHAT_MEMBER_ADMINISTRATOR_STATUS);

    private final String telegramValue;

    UserRole(String telegramValue) {
        this.telegramValue = telegramValue;
    }

    public static UserRole fromTelegramValue(String telegramValue) {
        for (UserRole role : UserRole.values()) {
            if (role.getTelegramValue() != null && role.getTelegramValue().equals(telegramValue)) {
                return role;
            }
        }

        throw new IllegalArgumentException("No UserRole found for value: " + telegramValue);
    }
}
