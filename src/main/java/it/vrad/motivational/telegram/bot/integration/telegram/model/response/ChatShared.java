package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatShared {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("chat_id")
    private String chatId;
}