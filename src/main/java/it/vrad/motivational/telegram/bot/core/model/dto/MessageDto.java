package it.vrad.motivational.telegram.bot.core.model.dto;

import it.vrad.motivational.telegram.bot.integration.telegram.model.response.InlineKeyboardMarkup;
import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Builder
@Getter
public class MessageDto {

    private String text;
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private String telegramFileId;
    private File photo;
    private String mediaType;
}
