package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TelegramErrorResponse(
        boolean ok,
        @JsonProperty("error_code") String errorCode,
        String description) {
}
