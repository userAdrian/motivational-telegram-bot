package it.vrad.motivational.telegram.bot.integration.telegram.model.response;

import lombok.Data;

@Data
public class ProximityAlertTriggered {
    private User traveler;
    private User watcher;
    private Integer distance;
}
