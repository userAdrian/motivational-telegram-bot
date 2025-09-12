package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VideoNote {
    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("file_unique_id")
    private String fileUniqueId;

    private Integer length;
    private Integer duration;
    private PhotoSize thumbnail;

    @JsonProperty("file_size")
    private String fileSize;
}
