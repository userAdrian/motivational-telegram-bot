package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatJoinRequest {

    private Chat chat;
    private User from;

    @JsonProperty("user_chat_id")
    private String userChatId;

    private String date;
    private String bio;

    @JsonProperty("invite_link")
    private ChatInviteLink inviteLink;
}