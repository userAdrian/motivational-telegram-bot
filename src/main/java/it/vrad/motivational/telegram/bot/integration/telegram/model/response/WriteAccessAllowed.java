package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WriteAccessAllowed {
    @JsonProperty("web_app_name")
    private String webAppName;
}
