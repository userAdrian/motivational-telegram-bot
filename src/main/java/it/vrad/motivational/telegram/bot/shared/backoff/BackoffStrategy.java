package it.vrad.motivational.telegram.bot.shared.backoff;

import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;

/**
 * Strategy interface for implementing different backoff/delay policies
 * to be applied after failures or retryable errors.
 */
public interface BackoffStrategy {

    /**
     * Resets the internal delay state, typically after a successful operation.
     */
    void resetValue();

    /**
     * Applies the backoff/delay before the next operation, typically after an error or failure event.
     * <p>
     * This delay is applied before the next attempt or operation, not as a retry for the current entity. If the delay exceeds
     * the configured maximum value, the provided exception is thrown to signal that the maximum wait duration has been reached.
     *
     * @param ex the exception to be thrown if the maximum delay duration is exceeded
     * @throws IntegrationApiException if the maximum delay duration is exceeded
     */
    void wait(IntegrationApiException ex);
}
