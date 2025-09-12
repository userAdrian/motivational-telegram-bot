package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwitchInlineQueryChosenChat {
    private String query;

    @JsonProperty("allow_user_chats")
    private boolean allowUserChats;

    @JsonProperty("allow_bot_chats")
    private boolean allowBotChats;

    @JsonProperty("allow_group_chats")
    private boolean allowGroupChats;

    @JsonProperty("allow_channel_chats")
    private boolean allowChannelChats;
}
