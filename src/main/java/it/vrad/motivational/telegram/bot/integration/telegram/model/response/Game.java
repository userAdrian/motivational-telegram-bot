package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Game {

    private String title;
    private String description;
    private List<PhotoSize> photo;
    private String text;

    @JsonProperty("text_entities")
    private List<MessageEntity> textEntities;

    private Animation animation;
}