package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InlineKeyboardMarkup {

    @JsonProperty("inline_keyboard")
    private InlineKeyboardButton[][] inlineKeyboard;

    public InlineKeyboardMarkup(){};

    public InlineKeyboardMarkup(InlineKeyboardButton[][] inlineKeyboardButton){
        this.inlineKeyboard = inlineKeyboardButton;
    }
}