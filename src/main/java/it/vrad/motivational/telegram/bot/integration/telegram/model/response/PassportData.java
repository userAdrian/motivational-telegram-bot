package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import lombok.Data;

import java.util.List;

@Data
public class PassportData {
    private List<EncryptedPassportElement> data;
    private EncryptedCredentials credentials;
}
