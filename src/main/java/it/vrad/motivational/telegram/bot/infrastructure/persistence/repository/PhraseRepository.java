package it.vrad.motivational.telegram.bot.infrastructure.persistence.repository;

import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    /**
     * JPQL query to select all active (not disabled) phrases that the user has not yet read.
     * Performs a LEFT JOIN with UserPhrase.
     * <p>
     * The 'up IS NULL' condition ensures only phrases
     * with no 'read' record for the given user are returned (i.e., unread phrases).
     */
    String ALL_AVAILABLE_PHRASES_QUERY = "SELECT p FROM Phrase p LEFT JOIN UserPhrase up " +
                                         "ON (up.user.id = :userId AND p.id = up.phrase.id AND up.read = true)" +
                                         "WHERE p.disabled = false AND up IS NULL";

    @Query(ALL_AVAILABLE_PHRASES_QUERY)
    List<Phrase> findAllAvailablePhrases(@Param("userId") Long userId);
}
