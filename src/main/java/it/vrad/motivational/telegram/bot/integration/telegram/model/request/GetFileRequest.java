package it.vrad.motivational.telegram.bot.integration.telegram.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetFileRequest {

    @JsonIgnore
    private Long chatId;

    @JsonProperty("file_id")
    @NotBlank
    private String fileId;

}

