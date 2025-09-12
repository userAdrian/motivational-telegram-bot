package it.vrad.motivational.telegram.bot.infrastructure.persistence.repository;

import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.UserPhrase;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.ids.UserPhraseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPhraseRepository extends JpaRepository<UserPhrase, UserPhraseId> {

    String ALL_AVAILABLE_USER_PHRASES_QUERY = "SELECT up FROM UserPhrase up JOIN Phrase p " +
                                              "ON (up.user.id = :userId AND up.phrase.id = p.id)" +
                                              "WHERE p.disabled = false";

    @Query(ALL_AVAILABLE_USER_PHRASES_QUERY)
    List<UserPhrase> findAllValidUserPhrases(@Param("userId") Long userId);

    List<UserPhrase> findAllByUserId(Long userId);
}
