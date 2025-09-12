package it.vrad.motivational.telegram.bot.core.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CallbackDataDetails {
    private String key;
    private int stepIndex;
    private String data;
}
