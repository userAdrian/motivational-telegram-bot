package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserShared {
    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("user_id")
    private String userId;
}
