package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarkPosition {

    private String point;

    @JsonProperty("x_shift")
    private Float xShift;

    @JsonProperty("y_shift")
    private Float yShift;

    private Float scale;
}