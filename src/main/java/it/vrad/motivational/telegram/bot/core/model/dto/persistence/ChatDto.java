package it.vrad.motivational.telegram.bot.core.model.dto.persistence;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.ChatType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatDto {
    private Long id;
    private Long telegramId;
    private ChatType type;
    private UserDto userDto;

}

