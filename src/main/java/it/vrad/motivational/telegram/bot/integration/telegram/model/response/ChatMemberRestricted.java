package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMemberRestricted extends ChatMember {

    @JsonProperty("is_member")
    private boolean isMember;

    @JsonProperty("can_send_messages")
    private boolean canSendMessages;

    @JsonProperty("can_send_audios")
    private boolean canSendAudios;

    @JsonProperty("can_send_documents")
    private boolean canSendDocuments;

    @JsonProperty("can_send_photos")
    private boolean canSendPhotos;

    @JsonProperty("can_send_videos")
    private boolean canSendVideos;

    @JsonProperty("can_send_video_notes")
    private boolean canSendVideoNotes;

    @JsonProperty("can_send_voice_notes")
    private boolean canSendVoiceNotes;

    @JsonProperty("can_send_polls")
    private boolean canSendPolls;

    @JsonProperty("can_send_other_messages")
    private boolean canSendOtherMessages;

    @JsonProperty("can_send_web_page_previews")
    private boolean canSendWebPagePreviews;

    @JsonProperty("can_change_info")
    private boolean canChangeInfo;

    @JsonProperty("can_invite_users")
    private boolean canInviteUsers;

    @JsonProperty("can_pin_messages")
    private boolean canPinMessages;

    @JsonProperty("can_manage_topics")
    private boolean canManageTopics;

    @JsonProperty("until_date")
    private Integer untilDate;
}