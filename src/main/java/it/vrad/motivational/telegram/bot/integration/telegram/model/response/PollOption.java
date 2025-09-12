package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PollOption {
    private String text;

    @JsonProperty("voter_count")
    private Integer voterCount;
}
