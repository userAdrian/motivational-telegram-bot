package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMemberAdministrator extends ChatMember {

    @JsonProperty("can_be_edited")
    private boolean canBeEdited;

    @JsonProperty("is_anonymous")
    private boolean isAnonymous;

    @JsonProperty("can_manage_chat")
    private boolean canManageChat;

    @JsonProperty("can_delete_messages")
    private boolean canDeleteMessages;

    @JsonProperty("can_manage_video_chats")
    private boolean canManageVideoChats;

    @JsonProperty("can_restrict_members")
    private boolean canRestrictMembers;

    @JsonProperty("can_promote_members")
    private boolean canPromoteMembers;

    @JsonProperty("can_change_info")
    private boolean canChangeInfo;

    @JsonProperty("can_invite_users")
    private boolean canInviteUsers;

    @JsonProperty("can_post_messages")
    private boolean canPostMessages;

    @JsonProperty("can_edit_messages")
    private boolean canEditMessages;

    @JsonProperty("can_pin_messages")
    private boolean canPinMessages;

    @JsonProperty("can_manage_topics")
    private boolean canManageTopics;

    @JsonProperty("custom_title")
    private String customTitle;
}