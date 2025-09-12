package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import lombok.Data;

@Data
public class EncryptedCredentials {

    private String data;
    private String hash;
    private String secret;
}