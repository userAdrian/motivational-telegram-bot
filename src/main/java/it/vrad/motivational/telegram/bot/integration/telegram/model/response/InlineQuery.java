package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InlineQuery {

    private String id;
    private User from;
    private String query;
    private String offset;

    @JsonProperty("chat_type")
    private String chatType;

    private Location location;
}