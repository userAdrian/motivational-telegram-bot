package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShippingAddress {
    @JsonProperty("country_code")
    private String countryCode;

    private String state;
    private String city;

    @JsonProperty("street_line1")
    private String streetLine1;

    @JsonProperty("street_line2")
    private String streetLine2;

    @JsonProperty("post_code")
    private String postCode;
}
