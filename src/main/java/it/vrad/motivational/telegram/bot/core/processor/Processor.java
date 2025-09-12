package it.vrad.motivational.telegram.bot.core.processor;

import java.util.function.Predicate;

/**
 * Generic processor interface for handling objects of type T.
 * Provides a method to retrieve a predicate for processor selection.
 *
 * @param <T> the type of object to process
 */
public interface Processor<T> {

    /**
     * Returns a predicate used to determine if this processor should handle the given object.
     *
     * @return a predicate for processor selection
     */
    Predicate<T> getProcessorFinder();
}
