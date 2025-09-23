package it.vrad.motivational.telegram.bot.core.exception;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionLogMessageHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ReservedCommandException extends Exception implements ContextAwareException {
    private Long chatId;
    private final String command;
    private final Set<UserRole> userRoles;

    public ReservedCommandException(String command, Set<UserRole> userRoles) {
        super(ExceptionLogMessageHelper.getReservedCommandMessage(command, userRoles));

        this.command = command;
        this.userRoles = userRoles;
    }
}
