package it.vrad.motivational.telegram.bot.core.exception;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
public class CooldownException extends Exception implements ContextAwareException {
    private Long chatId;
    private final CooldownType type;
    private final OffsetDateTime endingTime;

    public CooldownException(CooldownType type, OffsetDateTime endingTime) {
        super(String.format("Cooldown of type %s until %s", type, endingTime));
        this.type = type;
        this.endingTime = endingTime;
    }

}
