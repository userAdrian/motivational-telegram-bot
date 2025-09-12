package it.vrad.motivational.telegram.bot.config;

import it.vrad.motivational.telegram.bot.core.processor.update.actions.function.CallbackActionFunction;
import it.vrad.motivational.telegram.bot.core.processor.update.actions.function.MessageActionFunction;
import it.vrad.motivational.telegram.bot.core.processor.update.actions.CallbackActionMapProvider;
import it.vrad.motivational.telegram.bot.core.processor.update.actions.MessageActionMapProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Configuration class for registering beans related to action maps.
 * These maps are used to associate action functions with specific message or callback types.
 */
@Configuration
public class ActionsConfig {

    /**
     * Provides a map associating message text with their corresponding action functions.
     *
     * @param messageActionMapProvider the provider for message action mappings
     * @return a map from message text to a list of {@link MessageActionFunction}
     */
    @Bean
    public Map<String, List<MessageActionFunction>> messageActionMap(MessageActionMapProvider messageActionMapProvider) {
        return messageActionMapProvider.buildMessageActionMap();
    }

    /**
     * Provides a map associating callback data with their corresponding action functions.
     *
     * @param callbackActionMapProvider the provider for callback action mappings
     * @return a map from callback data to a list of {@link CallbackActionFunction}
     */
    @Bean
    public Map<String, List<CallbackActionFunction>> callbackActionMap(CallbackActionMapProvider callbackActionMapProvider) {
        return callbackActionMapProvider.buildCallbackActionMap();
    }
}
