package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Invoice {

    private String title;
    private String description;

    @JsonProperty("start_parameter")
    private String startParameter;

    private String currency;

    @JsonProperty("total_amount")
    private Integer totalAmount;
}