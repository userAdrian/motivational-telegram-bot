package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShippingQuery {
    private String id;
    private User from;

    @JsonProperty("invoice_payload")
    private String invoicePayload;

    @JsonProperty("shipping_address")
    private ShippingAddress shippingAddress;
}
