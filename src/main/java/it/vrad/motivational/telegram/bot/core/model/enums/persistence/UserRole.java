package it.vrad.motivational.telegram.bot.core.model.enums.persistence;

import it.vrad.motivational.telegram.bot.core.model.constants.EnumConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.TelegramValued;
import it.vrad.motivational.telegram.bot.core.model.enums.util.EnumValueMapper;
import it.vrad.motivational.telegram.bot.integration.telegram.TelegramConstants;
import lombok.Getter;

@Getter
public enum UserRole implements TelegramValued {
    ALL(EnumConstants.INTERNAL_STATUS), //any role
    BANNED(EnumConstants.INTERNAL_STATUS), //admin ban the user
    KICKED(TelegramConstants.CHAT_MEMBER_KICKED_STATUS), //user block the bot
    MEMBER(TelegramConstants.CHAT_MEMBER_MEMBER_STATUS), //user unblock the bot
    ADMIN(TelegramConstants.CHAT_MEMBER_ADMINISTRATOR_STATUS);

    private final String telegramValue;

    UserRole(String telegramValue) {
        this.telegramValue = telegramValue;
    }

    public static UserRole fromTelegramValue(String telegramValue) {
        return EnumValueMapper.fromTelegramValue(UserRole.class, telegramValue);
    }
}
