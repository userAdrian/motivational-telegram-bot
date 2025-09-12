package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatMemberUpdated {

    private Chat chat;
    private User from;
    private String date;

    @JsonProperty("old_chat_member")
    private ChatMember oldChatMember;

    @JsonProperty("new_chat_member")
    private ChatMember newChatMember;

    @JsonProperty("invite_link")
    private ChatInviteLink inviteLink;

    @JsonProperty("via_chat_folder_invite_link")
    private boolean viaChatFolderInviteLink;
}