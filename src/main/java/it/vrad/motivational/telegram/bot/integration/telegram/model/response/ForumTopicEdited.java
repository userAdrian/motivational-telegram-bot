package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForumTopicEdited {

    private String name;

    @JsonProperty("icon_custom_emoji_id")
    private String iconCustomEmojiId;
}