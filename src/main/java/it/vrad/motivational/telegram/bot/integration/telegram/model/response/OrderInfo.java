package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderInfo {

    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;

    @JsonProperty("shipping_address")
    private ShippingAddress shippingAddress;
}