package it.vrad.motivational.telegram.bot.infrastructure.web.controller.testconfig;

import it.vrad.motivational.telegram.bot.config.properties.LogProperties;
import it.vrad.motivational.telegram.bot.core.dispatch.impl.UpdateDispatcherImpl;
import it.vrad.motivational.telegram.bot.integration.telegram.api.impl.TelegramIntegrationApiImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
@EnableConfigurationProperties(LogProperties.class)
public class TelegramApiImplTestConfig{

    @Bean
    public UpdateDispatcherImpl updateDispatcherImpl(){
        return mock(UpdateDispatcherImpl.class);
    }

    @Bean
    public TelegramIntegrationApiImpl telegramIntegrationApiImpl(){
        return mock(TelegramIntegrationApiImpl.class);
    }
}
