package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {

    private Float longitude;
    private Float latitude;

    @JsonProperty("horizontal_accuracy")
    private Float horizontalAccuracy;

    @JsonProperty("live_period")
    private Integer livePeriod;

    private Integer heading;

    @JsonProperty("proximity_alert_radius")
    private Integer proximityAlertRadius;
}