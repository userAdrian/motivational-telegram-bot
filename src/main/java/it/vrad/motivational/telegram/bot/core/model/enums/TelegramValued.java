package it.vrad.motivational.telegram.bot.core.model.enums;

/**
 * Marker interface for enums that expose a string value received from Telegram.
 */
public interface TelegramValued {
    /**
     * Return the raw string value coming from Telegram for this enum constant.
     */
    String getTelegramValue();
}

