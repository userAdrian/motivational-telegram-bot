package it.vrad.motivational.telegram.bot.core.exception;

import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionLogMessageHelper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoSuchUserException extends Exception implements ContextAwareException {
    private Long chatId;
    private Long telegramId;

    public NoSuchUserException(Long telegramId) {
        super(ExceptionLogMessageHelper.getUserNotFoundMessage(telegramId));

        this.telegramId = telegramId;
    }
}
