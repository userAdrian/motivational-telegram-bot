package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Sticker {
    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("file_unique_id")
    private String fileUniqueId;

    private String type;
    private Integer width;
    private Integer height;

    @JsonProperty("is_animated")
    private boolean isAnimated;

    @JsonProperty("is_video")
    private boolean isVideo;

    private PhotoSize thumbnail;
    private String emoji;

    @JsonProperty("set_name")
    private String setName;

    @JsonProperty("premium_animation")
    private File premiumAnimation;

    @JsonProperty("mark_position")
    private MarkPosition markPosition;

    @JsonProperty("custom_emoji")
    private String customEmoji;

    @JsonProperty("needs_repainting")
    private boolean needsRePainting;

    @JsonProperty("file_size")
    private String fileSize;
}
