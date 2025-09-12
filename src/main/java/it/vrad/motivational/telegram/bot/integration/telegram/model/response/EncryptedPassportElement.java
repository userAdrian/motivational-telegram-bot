package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EncryptedPassportElement {

    private String type;
    private String data;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;
    private List<PassportFile> files;

    @JsonProperty("front_side")
    private PassportFile frontSide;

    @JsonProperty("reverse_side")
    private PassportFile reverseSide;

    private PassportFile selfie;
    private List<PassportFile> translation;
    private String hash;
}