package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserPhraseDto;

import java.util.List;
import java.util.Optional;

/**
 * Data access object for managing {@link UserPhraseDto} entities.
 */
public interface UserPhraseDao {

    /**
     * Finds a user phrase by user ID and phrase ID.
     *
     * @param userId   the user ID
     * @param phraseId the phrase ID
     * @return an {@link Optional} containing the found {@link UserPhraseDto}, or empty if not found
     */
    Optional<UserPhraseDto> findUserPhraseById(Long userId, Long phraseId);

    /**
     * Retrieves all valid user phrases for the given user.
     *
     * @param userId the user ID
     * @return a list of valid {@link UserPhraseDto} objects
     */
    List<UserPhraseDto> findAllValidUserPhrase(Long userId);

    /**
     * Saves the given {@link UserPhraseDto} to the persistence storage.
     *
     * @param userPhraseDto the DTO to save
     * @return the saved {@link UserPhraseDto}
     */
    UserPhraseDto saveUserPhrase(UserPhraseDto userPhraseDto);

    /**
     * Marks the given user phrase as read and increments its read count.
     *
     * @param userPhraseDto the DTO to update
     * @return the updated {@link UserPhraseDto}
     */
    UserPhraseDto markAsReadAndIncrement(UserPhraseDto userPhraseDto);

    /**
     * Resets the read flag for all user phrases of the given user.
     *
     * @param userId the user ID
     * @return a list of updated {@link UserPhraseDto} objects
     */
    List<UserPhraseDto> resetReadFlag(Long userId);
}
