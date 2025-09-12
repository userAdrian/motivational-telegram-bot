package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChosenInlineResult {

    @JsonProperty("result_id")
    private String resultId;

    private User from;
    private Location location;

    @JsonProperty("inline_message_id")
    private String inlineMessageId;

    private String query;
}