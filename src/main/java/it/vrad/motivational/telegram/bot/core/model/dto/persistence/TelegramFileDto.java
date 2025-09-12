package it.vrad.motivational.telegram.bot.core.model.dto.persistence;

import lombok.Data;

@Data
public class TelegramFileDto {

    private Long id;
    private String name;
    private String telegramId;

    public TelegramFileDto(){};

    public TelegramFileDto(String name, String telegramId){
        this.name = name;
        this.telegramId = telegramId;
    }
}
