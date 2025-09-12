package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReplyKeyboardMarkup {
    private List<List<KeyboardButton>> keyboard;

    @JsonProperty("is_persistent")
    private boolean isPersistent;

    @JsonProperty("resize_keyboard")
    private boolean resizeKeyboard;

    @JsonProperty("one_time_keyboard")
    private boolean oneTimeKeyboard;

    @JsonProperty("input_field_placeholder")
    private String inputFieldPlaceholder;

    private boolean selective;

    public ReplyKeyboardMarkup(){}

    public ReplyKeyboardMarkup(List<List<KeyboardButton>> keyboard){
        this.keyboard = keyboard;
    }
}
