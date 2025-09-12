package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KeyboardButtonRequestUser {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("user_is_bot")
    private boolean userIsBot;

    @JsonProperty("user_is_premium")
    private boolean userIsPremium;
}