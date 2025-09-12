package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KeyboardButtonRequestChat {

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("chat_is_channel")
    private boolean chatIsChannel;

    @JsonProperty("chat_is_forum")
    private boolean chatIsForum;

    @JsonProperty("chat_has_username")
    private boolean chatHasUsername;

    @JsonProperty("chat_is_created")
    private boolean chatIsCreated;

    @JsonProperty("user_administrator_rights")
    private ChatAdministratorRights userAdministratorRights;

    @JsonProperty("bot_administrator_rights")
    private ChatAdministratorRights botAdministratorRights;

    @JsonProperty("bot_is_member")
    private boolean botIsMember;
}