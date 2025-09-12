package it.vrad.motivational.telegram.bot.integration.telegram;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants used for Telegram Bot API integration
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class TelegramConstants {
    public static final String TELEGRAM_INPUT_MEDIA_ATTACH_PREFIX = "attach://";
    public static final String TELEGRAM_PHOTO_MEDIA_TYPE_NAME = "photo";

    /**
     * Telegram API error message returned with HTTP 400 when attempting to edit a message
     * without making any actual changes to its content.
     */
    public static final String MESSAGE_NOT_MODIFIED_ERROR = "message is not modified";

    // ChatMember status values
    public static final String CHAT_MEMBER_KICKED_STATUS = "kicked";
    public static final String CHAT_MEMBER_RESTRICTED_STATUS = "restricted";
    public static final String CHAT_MEMBER_LEFT_STATUS = "left";
    public static final String CHAT_MEMBER_MEMBER_STATUS = "member";
    public static final String CHAT_MEMBER_CREATOR_STATUS = "creator";
    public static final String CHAT_MEMBER_ADMINISTRATOR_STATUS = "administrator";

}
