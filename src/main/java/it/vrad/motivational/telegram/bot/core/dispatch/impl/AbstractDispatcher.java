package it.vrad.motivational.telegram.bot.core.dispatch.impl;

import it.vrad.motivational.telegram.bot.core.dispatch.Dispatcher;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Abstract base class for dispatchers.
 * Handles registration, removal, and dispatching of processors based on keys and predicates.
 *
 * @param <K> the processor key type
 * @param <I> the input type
 */
@Slf4j
public abstract class AbstractDispatcher<K, I>
        implements Dispatcher<K, I> {

    // Map of processor key to processor logic
    private final Map<K, Consumer<I>> processors = new HashMap<>();

    // Map of processor key to predicate for input matching
    private final Map<K, Predicate<I>> processorLookup = new HashMap<>();

    /**
     * Registers a processor and its predicate for a given key.
     */
    @Override
    public void registerProcessor(K key, Consumer<I> processor, Predicate<I> predicate) {
        processors.put(key, processor);
        processorLookup.put(key, predicate);
    }

    /**
     * Removes the processor and predicate for the given key.
     */
    @Override
    public boolean removeProcessor(K key) {
        processorLookup.remove(key);
        return Objects.nonNull(processors.remove(key));
    }

    /**
     * Dispatches the input to the first matching processor.
     * Throws if no matching processor is found.
     */
    @Override
    public void dispatch(I input) {
        getProcessorTypeValues()
                .filter(value -> processorLookup.get(value).test(input))
                .filter(value -> additionalFilterForHandleMethod(value, input))
                .findAny()
                .ifPresentOrElse(
                        type -> {
                            Consumer<I> updateProcessor = processors.get(type);
                            if(updateProcessor != null){
                                updateProcessor.accept(input);
                            }else{
                                throw new IllegalStateException("No processor registered for type " + type);
                            }
                        },
                        () -> {
                            loggingNoMatchingProcessor(input);
                            throw new NoSuchElementException("No matching processor found for input: " + input);
                        });
    }

    /**
     * Returns a stream of all processor key values.
     */
    protected abstract Stream<K> getProcessorTypeValues();

    /**
     * Logs when no matching processor is found.
     */
    protected void loggingNoMatchingProcessor(I input){
        log.error("No matching processor found for input: {}", input);
    }

    /**
     * Additional filter for processor selection. Default is always true.
     */
    protected boolean additionalFilterForHandleMethod(K key, I input){
        return true;
    }

}
