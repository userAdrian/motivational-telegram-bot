package it.vrad.motivational.telegram.bot.integration.telegram.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardMarkup;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendMessageRequest {

    @JsonProperty("chat_id")
    @NotNull
    private Long chatId;

    @NotEmpty
    private String text;

    @JsonProperty("reply_markup")
    private InlineKeyboardMarkup replyMarkup;

}
