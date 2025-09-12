package it.vrad.motivational.telegram.bot.core.exception;

import lombok.Getter;

@Getter
public class MotivationalTelegramBotException extends RuntimeException {
    private final Long chatId;

    public MotivationalTelegramBotException(String message, Throwable throwable){
        super(message, throwable);

        chatId = null;
    }

    public MotivationalTelegramBotException(String message, Long chatId, Throwable throwable) {
        super(message, throwable);

        this.chatId = chatId;
    }
}
