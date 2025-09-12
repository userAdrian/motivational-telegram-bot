package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import lombok.Data;

@Data
public class Dice {

    private String emoji;

    private Integer value;
}