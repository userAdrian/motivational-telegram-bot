package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class Message {

    @JsonProperty("message_id")
    private Long messageId;

    @JsonProperty("message_thread_id")
    private String messageThreadId;

    private User from;

    @JsonProperty("sender_chat")
    private Chat senderChat;

    private String date;
    private Chat chat;

    @JsonProperty("forward_from")
    private User forwardFrom;

    @JsonProperty("forward_from_chat")
    private Chat forwardFromChat;

    @JsonProperty("forward_from_message_id")
    private String forwardFromMessageId;

    @JsonProperty("forward_signature")
    private String forwardSignature;

    @JsonProperty("forward_sender_name")
    private String forwardSenderName;

    @JsonProperty("forward_date")
    private String forwardDate;

    @JsonProperty("is_topic_message")
    private boolean isTopicMessage;

    @JsonProperty("is_automatic_forward")
    private boolean isAutomaticForward;

    @JsonProperty("reply_to_message")
    private Message replyToMessage;

    @JsonProperty("via_bot")
    private User viaBot;

    @JsonProperty("has_protected_content")
    private boolean hasProtectedContent;

    @JsonProperty("media_group_id")
    private String mediaGroupId;

    @JsonProperty("author_signature")
    private String authorSignature;

    private String text;
    private List<MessageEntity> entities;
    private Animation animation;
    private Audio audio;
    private Document document;
    private List<PhotoSize> photo;
    private Sticker sticker;
    private Story story;
    private Video video;

    @JsonProperty("video_note")
    private VideoNote videoNote;

    private Voice voice;
    private String caption;

    @JsonProperty("caption_entities")
    private List<MessageEntity> captionEntities;

    @JsonProperty("has_media_spoiler")
    private boolean hasMediaSpoiler;

    private Contact contact;
    private Dice dice;
    private Game game;
    private Poll poll;
    private Venue venue;
    private Location location;

    @JsonProperty("new_chat_members")
    private List<User> newChatMembers;

    @JsonProperty("left_chat_member")
    private User leftChatMember;

    @JsonProperty("new_chat_title")
    private String newChatTitle;

    @JsonProperty("new_chat_photo")
    private List<PhotoSize> newChatPhoto;

    @JsonProperty("delete_chat_photo")
    private boolean deleteChatPhoto;

    @JsonProperty("group_chat_created")
    private boolean groupChatCreated;

    @JsonProperty("supergroup_chat_created")
    private boolean supergroupChatCreated;

    @JsonProperty("channel_chat_created")
    private boolean channelChatCreated;

    @JsonProperty("message_auto_delete_timer_changed")
    private MessageAutoDeleteTimerChanged messageAutoDeleteTimerChanged;

    @JsonProperty("migrate_to_chat_id")
    private String migrateToChatId;

    @JsonProperty("migrate_from_chat_id")
    private String migrateFromChatId;

    @JsonProperty("pinned_message")
    private Message pinnedMessage;

    private Invoice invoice;

    @JsonProperty("successful_payment")
    private SuccessfulPayment successfulPayment;

    @JsonProperty("user_shared")
    private UserShared userShared;

    @JsonProperty("chat_shared")
    private ChatShared chatShared;

    @JsonProperty("connected_website")
    private String connectedWebsite;

    @JsonProperty("write_access_allowed")
    private WriteAccessAllowed writeAccessAllowed;

    @JsonProperty("passport_data")
    private PassportData passportData;

    @JsonProperty("proximity_alert_triggered")
    private ProximityAlertTriggered proximityAlertTriggered;

    @JsonProperty("forum_topic_edited")
    private ForumTopicEdited forumTopicEdited;

    @JsonProperty("forum_topic_closed")
    private ForumTopicClosed forumTopicClosed;

    @JsonProperty("forum_topic_reopened")
    private ForumTopicReopened forumTopicReopened;

    @JsonProperty("general_forum_topic_hidden")
    private GeneralForumTopicHidden generalForumTopicHidden;

    @JsonProperty("general_forum_topic_unhidden")
    private GeneralForumTopicUnhidden generalForumTopicUnhidden;

    @JsonProperty("video_chat_scheduled")
    private VideoChatScheduled videoChatScheduled;

    @JsonProperty("video_chat_started")
    private VideoChatStarted videoChatStarted;

    @JsonProperty("video_chat_ended")
    private VideoChatEnded videoChatEnded;

    @JsonProperty("video_chat_participants_invited")
    private VideoChatParticipantsInvited videoChatParticipantsInvited;

    @JsonProperty("web_app_data")
    private WebAppData webAppData;

    @JsonProperty("reply_markup")
    private InlineKeyboardMarkup replyMarkup;
}