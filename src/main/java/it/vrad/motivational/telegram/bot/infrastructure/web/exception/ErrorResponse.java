package it.vrad.motivational.telegram.bot.infrastructure.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String errorMessage;
    private String errorCode;

}
