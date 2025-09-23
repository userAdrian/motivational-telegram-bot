package it.vrad.motivational.telegram.bot.core.dispatch.impl;

import it.vrad.motivational.telegram.bot.core.dispatch.UpdateDispatcher;
import it.vrad.motivational.telegram.bot.core.model.enums.UpdateProcessorType;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Update;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Dispatcher implementation for handling Telegram Update objects.
 */
@Slf4j
public class UpdateDispatcherImpl extends AbstractDispatcher<UpdateProcessorType, Update> implements UpdateDispatcher {

    /**
     * Returns a stream of all UpdateProcessorType values.
     */
    @Override
    protected Stream<UpdateProcessorType> getProcessorTypeValues() {
        return Arrays.stream(UpdateProcessorType.values());
    }

    /**
     * Logs when no matching processor is found for an update.
     */
    @Override
    protected void loggingNoMatchingProcessor(Update input) {
        log.error("No matching processor found for update {}", input);
    }
}
