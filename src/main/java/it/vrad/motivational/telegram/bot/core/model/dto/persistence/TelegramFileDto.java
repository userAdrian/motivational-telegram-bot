package it.vrad.motivational.telegram.bot.core.model.dto.persistence;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TelegramFileDto {

    private Long id;
    private String name;
    private String telegramId;

}
