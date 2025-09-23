package it.vrad.motivational.telegram.bot.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnrecognizedCommandException extends Exception implements ContextAwareException {
    private Long chatId;
    private String command;

    public UnrecognizedCommandException(String command){
        super();

        this.command = command;
    }

}
