package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VideoChatScheduled {
    @JsonProperty("start_date")
    private String startDate;
}
