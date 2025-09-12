package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Data Access Object interface for phrase entities.
 */
public interface PhraseDao {
    /**
     * Finds all available phrases for the specified user.
     *
     * @param userId the ID of the user
     * @return a list of available PhraseDto objects
     */
    List<PhraseDto> findAllAvailablePhrases(Long userId);

    /**
     * Finds a phrase by its ID.
     *
     * @param phraseId the ID of the phrase
     * @return an Optional containing the PhraseDto if found, or empty if not found
     */
    Optional<PhraseDto> findPhraseById(Long phraseId);

    /**
     * Finds all phrases in the database.
     *
     * @return a list of all PhraseDto objects
     */
    List<PhraseDto> findAll();

    /**
     * Saves a set of phrases to the database.
     *
     * @param phraseDtos the set of PhraseDto objects to save
     * @return a list of saved PhraseDto objects
     */
    List<PhraseDto> saveAll(Set<PhraseDto> phraseDtos);
}
