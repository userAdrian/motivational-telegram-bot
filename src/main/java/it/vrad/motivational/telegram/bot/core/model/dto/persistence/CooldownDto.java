package it.vrad.motivational.telegram.bot.core.model.dto.persistence;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Builder
@Data
public class CooldownDto {
    private Long id;
    private CooldownType type;
    private OffsetDateTime endingTime;
    private UserDto userDto;
}
