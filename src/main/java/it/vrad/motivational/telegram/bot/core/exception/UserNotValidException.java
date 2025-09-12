package it.vrad.motivational.telegram.bot.core.exception;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionLogMessageHelper;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotValidException extends RuntimeException implements ContextAwareException {
    private Long chatId;
    private final Long telegramUserId;
    private final UserRole userRole;

    public UserNotValidException(Long telegramUserId, @NotNull UserRole userRole) {
        super(ExceptionLogMessageHelper.getUserNotValidMessage(telegramUserId, userRole));

        this.telegramUserId = telegramUserId;
        this.userRole = userRole;
    }
}
