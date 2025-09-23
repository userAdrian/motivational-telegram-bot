package it.vrad.motivational.telegram.bot.shared.backoff.impl;

import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;
import it.vrad.motivational.telegram.bot.shared.backoff.BackoffStrategy;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * Implements a linear incremental backoff strategy.
 * The delay increases by a fixed increment on each failure up to a maximum value,
 * and is reset after a successful operation.
 */
@Slf4j
public class IncrementalBackoff implements BackoffStrategy {
    private final int initialValue;
    private final int increment;
    private final int maxValue;

    private int currentValue;

    /**
     * Constructs an IncrementalBackoff.
     *
     * @param initialValue initial delay value in seconds
     * @param increment    increment in seconds after each failure
     * @param maxValue     maximum delay in seconds
     */
    public IncrementalBackoff(int initialValue, int increment, int maxValue) {
        this.initialValue = currentValue;
        this.increment = increment;
        this.maxValue = maxValue;

        this.currentValue = initialValue;
    }

    /**
     * Increments the current delay value by the configured increment.
     */
    private void incrementValue() {
        currentValue += increment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetValue() {
        currentValue = initialValue;
    }

    /**
     * {@inheritDoc}
     *
     * @param ex {@inheritDoc}
     * @throws IntegrationApiException {@inheritDoc}
     */
    @Override
    public void wait(IntegrationApiException ex) {
        incrementValue();
        if (currentValue > maxValue) {
            throw ex;
        }

        sleep(currentValue);
    }

    /**
     * Sleeps for the specified delay in seconds.
     *
     * @param delay the number of seconds to sleep
     */
    private static void sleep(int delay) {
        log.info("Sleeping {} seconds", delay);
        try {
            Thread.sleep(Duration.ofSeconds(delay));
        } catch (InterruptedException interruptedException) {
            log.error("Error occurred during thread sleeping", interruptedException);
        }
    }
}
