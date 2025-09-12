package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForumTopicCreated {

    private String name;

    @JsonProperty("icon_color")
    private Integer iconColor;

    @JsonProperty("icon_custom_emoji_id")
    private String iconCustomEmojiId;
}