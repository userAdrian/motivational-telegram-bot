package it.vrad.motivational.telegram.bot.config;

import it.vrad.motivational.telegram.bot.core.dispatch.impl.UpdateDispatcherImpl;
import it.vrad.motivational.telegram.bot.core.processor.update.UpdateProcessor;
import it.vrad.motivational.telegram.bot.core.model.enums.UpdateProcessorType;
import it.vrad.motivational.telegram.bot.core.dispatch.Dispatcher;
import it.vrad.motivational.telegram.bot.core.dispatch.UpdateDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Configuration class for setting up the {@link Dispatcher} beans
 */
@Configuration
public class DispatcherConfig {

    /**
     * Creates and configures the {@link UpdateDispatcher} bean.
     * Registers each {@link UpdateProcessor} from the provided map using its processor type.
     *
     * @param updateProcessorMap a map (provided by spring) of processor type names to {@link UpdateProcessor} instances
     * @return the configured {@link UpdateDispatcher} instance
     */
    @Bean
    public UpdateDispatcher updateDispatcher(Map<String, UpdateProcessor> updateProcessorMap) {
        // Create a new dispatcher instance
        UpdateDispatcherImpl dispatcher = new UpdateDispatcherImpl();

        // Register each processor with its corresponding type and finder
        updateProcessorMap.forEach((processorTypeName, processor) ->
                dispatcher.registerProcessor(
                        UpdateProcessorType.valueOf(processorTypeName),
                        processor,
                        processor.getProcessorFinder())
        );

        return dispatcher;
    }
}
