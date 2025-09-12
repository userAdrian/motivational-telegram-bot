package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Voice {
    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("file_unique_id")
    private String fileUniqueId;

    private Integer duration;

    @JsonProperty("mime_type")
    private String mimeType;

    @JsonProperty("file_size")
    private String fileSize;
}
