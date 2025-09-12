package it.vrad.motivational.telegram.bot.core.exception;

import lombok.Getter;

@Getter
public class UnrecognizedCommandException extends RuntimeException {
    private Long chatId;
    private String command;

    public UnrecognizedCommandException(Long chatId, String command){
        super();

        this.chatId = chatId;
        this.command = command;
    }

}
