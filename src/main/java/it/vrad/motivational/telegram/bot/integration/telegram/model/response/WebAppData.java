package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WebAppData {
    private String data;

    @JsonProperty("button_text")
    private String buttonText;
}
