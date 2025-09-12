package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SuccessfulPayment {
    private String currency;

    @JsonProperty("total_amount")
    private Integer totalAmount;

    @JsonProperty("invoice_payload")
    private String invoicePayload;

    @JsonProperty("shipping_option_id")
    private String shippingOptionId;

    @JsonProperty("order_info")
    private OrderInfo orderInfo;

    @JsonProperty("telegram_payment_charge_id")
    private String telegramPaymentChargeId;

    @JsonProperty("provider_payment_charge_id")
    private String providerPaymentChargeId;
}
