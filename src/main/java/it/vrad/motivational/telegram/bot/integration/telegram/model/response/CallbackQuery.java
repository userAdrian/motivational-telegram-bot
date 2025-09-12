package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CallbackQuery {

    private String id;
    private User from;
    private Message message;

    @JsonProperty("inline_message_id")
    private String inlineMessageId;

    @JsonProperty("chat_instance")
    private String chatInstance;

    private String data;

    @JsonProperty("game_short_name")
    private String gameShortName;
}