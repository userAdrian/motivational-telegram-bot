package it.vrad.motivational.telegram.bot.integration.telegram.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteMessageRequest {

    @JsonProperty("chat_id")
    @NotNull
    private Long chatId;

    @JsonProperty("message_id")
    @NotNull
    private Long messageId;
}
