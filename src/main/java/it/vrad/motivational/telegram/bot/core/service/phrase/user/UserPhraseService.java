package it.vrad.motivational.telegram.bot.core.service.phrase.user;

import it.vrad.motivational.telegram.bot.core.model.StatisticsPageDetails;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserPhraseDto;

import java.util.List;

/**
 * Service interface for user phrase operations.
 */
public interface UserPhraseService {

    /**
     * Resets the read status of all phrases for the specified user.
     *
     * @param userId the ID of the user
     * @return a list of PhraseDto objects after reset
     */
    List<PhraseDto> resetPhrases(Long userId);

    /**
     * Updates the read status of a specific phrase for the user.
     * If the phrase-user relation does not exist, creates it.
     *
     * @param userId the ID of the user
     * @param phraseId the ID of the phrase
     * @return the updated UserPhraseDto
     */
    UserPhraseDto updateUserPhraseReadStatus(Long userId, Long phraseId);

    /**
     * Fetches statistics page details for the user's phrases.
     *
     * @param userId the ID of the user
     * @return the statistics page details
     */
    StatisticsPageDetails fetchStatisticsPageDetails(Long userId);
}
