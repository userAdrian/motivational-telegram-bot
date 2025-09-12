package it.vrad.motivational.telegram.bot.config;

import it.vrad.motivational.telegram.bot.infrastructure.config.converter.ButtonCoordinateConverter;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for registering custom converters.
 */
@Configuration
public class ConversionConfig {

    /**
     * Registers the {@link ButtonCoordinateConverter} as a bean for property binding conversion.
     *
     * @return a new instance of {@link ButtonCoordinateConverter}
     */
    @Bean
    @ConfigurationPropertiesBinding
    public static ButtonCoordinateConverter buttonCoordinateConverter() {
        return new ButtonCoordinateConverter();
    }
}