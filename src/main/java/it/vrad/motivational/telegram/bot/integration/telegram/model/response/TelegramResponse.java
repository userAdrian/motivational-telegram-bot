package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import lombok.Data;

@Data
public class TelegramResponse<T> {
    private boolean ok;
    private T result;
}
