package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KeyboardButton {

    private String text;

    @JsonProperty("request_user")
    private KeyboardButtonRequestUser requestUser;

    @JsonProperty("request_chat")
    private KeyboardButtonRequestChat requestChat;

    @JsonProperty("request_contact")
    private boolean requestContact;

    @JsonProperty("request_location")
    private boolean requestLocation;

    @JsonProperty("request_poll")
    private KeyboardButtonPollType requestPoll;

    @JsonProperty("web_app")
    private WebAppInfo webApp;
}