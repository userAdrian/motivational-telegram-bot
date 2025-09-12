package it.vrad.motivational.telegram.bot.core.exception;

public class NoSuchPhraseException extends RuntimeException {

    public NoSuchPhraseException() {
        super();
    }

    public NoSuchPhraseException(String message) {
        super(message);
    }
}
