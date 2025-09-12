package it.vrad.motivational.telegram.bot.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.ChatMember;
import it.vrad.motivational.telegram.bot.infrastructure.json.deserializer.ChatMemberDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing the Jackson {@link ObjectMapper} used in the application.
 */
@Configuration
public class JacksonConfig {

    /**
     * Configures and provides a customized {@link ObjectMapper} bean.
     *
     * @return the configured {@link ObjectMapper}
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Do not fail when unknown properties are encountered during deserialization
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Register custom deserializer for ChatMember
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(ChatMember.class, new ChatMemberDeserializer(objectMapper));

        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }

}
