package it.vrad.motivational.telegram.bot.core.exception;

/**
 * Runtime exception used to wrap checked exceptions as unchecked exceptions.
 * <p>
 * This is typically used when an API requires a RuntimeException but the underlying exception is checked,
 * allowing the checked exception to be propagated without changing method signatures.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     try {
 *         // code that throws checked exception
 *     } catch (SomeCheckedException e) {
 *         throw new UncheckedWrapperException(e);
 *     }
 * </pre>
 */
public class UncheckedWrapperException extends RuntimeException {
    public UncheckedWrapperException(Throwable throwable) {
        super(throwable);
    }
}
