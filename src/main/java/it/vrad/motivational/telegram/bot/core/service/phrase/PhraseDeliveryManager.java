package it.vrad.motivational.telegram.bot.core.service.phrase;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchPhraseException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;

/**
 * Service dedicated to phrase delivery.
 */
public interface PhraseDeliveryManager {

    /**
     * Processes and sends a random phrase to the user.
     * <p>
     * Retrieves a random phrase, sends it via Telegram, saves the sent history,
     * and updates the user's phrase read status.
     *
     * @param user the UserDto representing the user
     * @throws NoSuchPhraseException if no phrase is available to deliver
     */
    void deliverRandomPhrase(UserDto user) throws NoSuchPhraseException;
}
