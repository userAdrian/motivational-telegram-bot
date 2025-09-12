package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Update {
    @JsonProperty("update_id")
    private Integer updateId;

    private Message message;

    @JsonProperty("edited_message")
    private Message editedMessage;

    @JsonProperty("channel_post")
    private Message channelPost;

    @JsonProperty("edited_channel_post")
    private Message editedChannelPost;

    @JsonProperty("inline_query")
    private InlineQuery inlineQuery;

    @JsonProperty("chosen_inline_result")
    private ChosenInlineResult chosenInlineResult;

    @JsonProperty("callback_query")
    private CallbackQuery callbackQuery;

    private ShippingQuery shippingQuery;

    @JsonProperty("pre_checkout_query")
    private PreCheckoutQuery preCheckoutQuery;

    private Poll poll;

    @JsonProperty("poll_answer")
    private PollAnswer pollAnswer;

    @JsonProperty("my_chat_member")
    private ChatMemberUpdated myChatMember;

    @JsonProperty("chat_member")
    private ChatMemberUpdated chatMember;

    @JsonProperty("chat_join_request")
    private ChatJoinRequest chatJoinRequest;
}
