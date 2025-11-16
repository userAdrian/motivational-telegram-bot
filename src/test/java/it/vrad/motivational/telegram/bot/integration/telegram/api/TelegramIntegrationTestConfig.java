package it.vrad.motivational.telegram.bot.integration.telegram.api;

import it.vrad.motivational.telegram.bot.config.ApiClientConfig;
import it.vrad.motivational.telegram.bot.config.JacksonConfig;
import it.vrad.motivational.telegram.bot.config.properties.LogProperties;
import it.vrad.motivational.telegram.bot.config.properties.TelegramProperties;
import it.vrad.motivational.telegram.bot.infrastructure.http.ApiClientFeatureProvider;
import it.vrad.motivational.telegram.bot.infrastructure.http.interceptor.LogInterceptor;
import it.vrad.motivational.telegram.bot.integration.telegram.client.TelegramRestTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@EnableConfigurationProperties(TelegramProperties.class)
@Import({
        TelegramRestTemplate.class,
        JacksonConfig.class,
        ApiClientConfig.class,
        ApiClientFeatureProvider.class,
        LogInterceptor.class,
        LogProperties.class
})
public class TelegramIntegrationTestConfig {
}
