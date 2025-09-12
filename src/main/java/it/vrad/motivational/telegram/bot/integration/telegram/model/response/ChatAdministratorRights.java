package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatAdministratorRights {

    @JsonProperty("is_anonymous")
    private boolean isAnonymous;

    @JsonProperty("can_manage_chat")
    private boolean canManageChat;

    @JsonProperty("can_manage_messages")
    private boolean canManageMessages;

    @JsonProperty("can_delete_messages")
    private boolean canDeleteMessages;

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
}