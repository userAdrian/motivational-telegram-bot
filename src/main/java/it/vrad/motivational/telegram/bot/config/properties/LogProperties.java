package it.vrad.motivational.telegram.bot.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for logging settings.
 */
@ConfigurationProperties(prefix = "motivational.telegram.bot.configuration.log")
@Data
@Component
public class LogProperties {
    private int maxRequestLength;
    private int maxResponseLength;
}
