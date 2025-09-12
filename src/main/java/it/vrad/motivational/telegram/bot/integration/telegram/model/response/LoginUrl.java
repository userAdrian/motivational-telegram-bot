package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginUrl {

    private String url;

    @JsonProperty("forward_text")
    private String forwardText;

    @JsonProperty("bot_username")
    private String botUsername;

    @JsonProperty("request_write_access")
    private boolean requestWriteAccess;
}