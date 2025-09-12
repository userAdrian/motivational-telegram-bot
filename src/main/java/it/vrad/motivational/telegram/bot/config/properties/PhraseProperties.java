package it.vrad.motivational.telegram.bot.config.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Configuration properties for phrase scheduling and time zone settings.
 * Used to configure when and how phrases are sent by the bot.
 */
@Validated
@Data
@ConfigurationProperties(prefix = "motivational.telegram.bot.configuration.phrase")
@Component
public class PhraseProperties {
    @NotEmpty
    private List<LocalTime> sendingTimes;

    @NotNull
    private ZoneId timeZone;

    private int schedulerRetryInterval;
    private int schedulerMaxRetryDuration;
    private long schedulerLockLeaseTime;
}
