package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageAutoDeleteTimerChanged {

    @JsonProperty("message_auto_delete_time")
    private Integer messageAutoDeleteTime;
}