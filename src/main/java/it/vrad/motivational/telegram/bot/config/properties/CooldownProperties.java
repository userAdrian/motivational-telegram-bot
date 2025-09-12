package it.vrad.motivational.telegram.bot.config.properties;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.Map;

/**
 * Configuration properties for cooldown durations by type.
 * Used to configure cooldown periods for different actions or commands.
 */
@Validated
@Data
@ConfigurationProperties(prefix = "motivational.telegram.bot.configuration.cooldown")
@Component
public class CooldownProperties {

    @NotNull
    private final Map<CooldownType, Duration> cooldownDurationMap;

    /**
     * Retrieves the cooldown duration for the specified type.
     *
     * @param cooldownType the cooldown type
     * @return the duration for the given cooldown type
     */
    public Duration getCooldownDurationByType(CooldownType cooldownType) {
        return cooldownDurationMap.get(cooldownType);
    }
}
