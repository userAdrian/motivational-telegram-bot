package it.vrad.motivational.telegram.bot.core.service.phrase;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchPhraseException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.exception.constants.ExceptionMessageConstants;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service interface for phrase operations.
 */
public interface PhraseService {

    /**
     * Retrieves a random phrase available for the specified user.
     *
     * @param userId the ID of the user
     * @return an Optional containing a random PhraseDto, or empty if none available
     */
    Optional<PhraseDto> getRandomPhrase(Long userId);

    default PhraseDto getRandomPhraseOrThrow(Long userId) throws NoSuchPhraseException {
        return getRandomPhrase(userId)
                .orElseThrow(() -> new NoSuchPhraseException(ExceptionMessageConstants.LOG_NO_PHRASES_FOUND_MESSAGE));
    }

    /**
     * Saves a set of new phrases, ignoring duplicates based on text hash.
     *
     * @param phraseDtos the set of PhraseDto objects to save
     * @return a list of saved PhraseDto objects
     */
    List<PhraseDto> save(Set<PhraseDto> phraseDtos);
}
