package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseBody {
    private boolean ok;

    @JsonProperty("error_message")
    private String errorMessage;
    private String description;
    private Object result;
}
