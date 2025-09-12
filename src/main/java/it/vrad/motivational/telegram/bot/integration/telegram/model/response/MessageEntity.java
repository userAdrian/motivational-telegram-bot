package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageEntity {

    private String type;
    private String offset;
    private Integer length;
    private String url;
    private User user;
    private String language;

    @JsonProperty("custom_emoji_id")
    private String customEmojiId;
}