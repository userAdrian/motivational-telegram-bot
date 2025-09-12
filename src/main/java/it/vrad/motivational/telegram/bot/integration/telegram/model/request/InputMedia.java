package it.vrad.motivational.telegram.bot.integration.telegram.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@Getter
public class InputMedia {

    private String type;
    private String media;
    private String caption;
}
