package it.vrad.motivational.telegram.bot.core.dispatch;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Generic dispatcher interface for registering, removing, and dispatching processors.
 *
 * @param <K> the processor key type (e.g., enum)
 * @param <I> the input type to be processed
 */
public interface Dispatcher<K, I> {

    /**
     * Registers a processor for a given key with a predicate to match input.
     *
     * @param key the processor key
     * @param processor the processor logic
     * @param processorFinder predicate to match input for this processor
     */
    void registerProcessor(K key, Consumer<I> processor, Predicate<I> processorFinder);

    /**
     * Removes the processor for the given key.
     *
     * @param key the processor key
     * @return true if processor was removed, false otherwise
     */
    boolean removeProcessor(K key);

    /**
     * Dispatches the input to the matching processor.
     *
     * @param input the input to process
     */
    void dispatch(I input);
}
