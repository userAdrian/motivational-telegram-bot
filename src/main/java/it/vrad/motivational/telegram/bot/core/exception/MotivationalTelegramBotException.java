package it.vrad.motivational.telegram.bot.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MotivationalTelegramBotException extends RuntimeException implements ContextAwareException {
    private Long chatId;

    public MotivationalTelegramBotException(String message, Throwable throwable) {
        super(message, throwable);

        chatId = null;
    }

    public MotivationalTelegramBotException(String message, Long chatId, Throwable throwable) {
        super(message, throwable);

        this.chatId = chatId;
    }
}
