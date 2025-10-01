package it.vrad.motivational.telegram.bot.core.model.enums.persistence;

import it.vrad.motivational.telegram.bot.core.model.enums.TelegramValued;
import it.vrad.motivational.telegram.bot.core.model.enums.util.EnumValueMapper;
import lombok.Getter;

@Getter
public enum ChatType implements TelegramValued {
    PRIVATE("private");

    private final String telegramValue; //telegram value

    ChatType(String telegramValue) {
        this.telegramValue = telegramValue;
    }

    public static ChatType fromTelegramValue(String value) {
        return EnumValueMapper.fromTelegramValue(ChatType.class, value);
    }

}
