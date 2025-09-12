package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMemberOwner extends ChatMember {

    @JsonProperty("is_anonymous")
    private boolean isAnonymous;

    @JsonProperty("custom_title")
    private String customTitle;
}