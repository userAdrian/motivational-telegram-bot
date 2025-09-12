package it.vrad.motivational.telegram.bot.integration.telegram.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardMarkup;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendPhotoRequest extends AbstractTelegramRequest {

    @JsonProperty("chat_id")
    @NotNull
    private final Long chatId;

    @JsonProperty("photo")
    private final String telegramFileId;

    private final String caption;

    @JsonProperty("photo")
    private final File photo;

    @JsonProperty("reply_markup")
    private final InlineKeyboardMarkup replyMarkup;


    private SendPhotoRequest(
            Long chatId,
            String telegramFileId,
            String caption,
            File photo,
            InlineKeyboardMarkup replyMarkup
    ) {
        if ((telegramFileId == null && photo == null) || (telegramFileId != null && photo != null))
            throw new IllegalArgumentException("Either telegramFileId or photo must be set, not both");

        this.chatId = chatId;
        this.telegramFileId = telegramFileId;
        this.caption = caption;
        this.photo = photo;
        this.replyMarkup = replyMarkup;
    }

}