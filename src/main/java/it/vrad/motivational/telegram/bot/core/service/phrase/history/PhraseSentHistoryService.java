package it.vrad.motivational.telegram.bot.core.service.phrase.history;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseSentHistoryDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;

/**
 * Service interface for managing the history of sent phrases.
 * <p>
 * Provides methods to save phrase sent history records.
 */
public interface PhraseSentHistoryService {

    /**
     * Saves a record of a phrase sent to a user.
     *
     * @param phraseDto the phrase that was sent
     * @param userDto   the user to whom the phrase was sent
     * @return the saved PhraseSentHistoryDto
     */
    PhraseSentHistoryDto save(PhraseDto phraseDto, UserDto userDto);
}
