package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserPhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.BaseTestRepository;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.PersistenceTestFactory;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.DaoTestConfig;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.PHRASE_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.USER_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.USER_ID_NOT_PRESENT;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.USER_PHRASE_ID;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.USER_PHRASE_ID_TO_SAVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration tests for {@link UserPhraseDaoImpl}.
 * <p>
 * This class verifies the correct behavior of UserPhraseDaoImpl's CRUD operations, including:
 * <ul>
 *   <li>Creating user phrases and verifying persistence</li>
 *   <li>Reading user phrases by composite key and by user</li>
 *   <li>Updating user phrase fields and ensuring changes are persisted</li>
 *   <li>Resetting read flags for all user phrases of a user</li>
 * </ul>
 * <p>
 * The tests use a test configuration that loads only the required beans and mappers for isolation.
 * Test data is provided via {@code data.sql} and
 * {@link it.vrad.motivational.telegram.bot.infrastructure.persistence.PersistenceTestFactory}.
 */
@Import(DaoTestConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserPhraseDaoImplIT extends BaseTestRepository {
    private final UserPhraseDaoImpl userPhraseDao;

    /**
     * Tests for creating user phrases in the database.
     */
    @Nested
    class Create {
        @Test
        @DisplayName("save should persist and return the UserPhraseDto saved")
        void save_whenValidInput_returnsUserPhraseDto() {
            UserPhraseDto userPhrase = PersistenceTestFactory.createUserPhraseDtoToSave();

            UserPhraseDto saved = userPhraseDao.save(userPhrase);

            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getRead()).isTrue();
            assertThat(saved.getReadCount()).isOne();
            assertThat(saved)
                    .hasFieldOrPropertyWithValue("userDto.id", userPhrase.getUserDto().getId())
                    .hasFieldOrPropertyWithValue("phraseDto.id", userPhrase.getPhraseDto().getId());
        }
    }

    /**
     * Tests for reading user phrases from the database.
     */
    @Nested
    class Read {
        @Test
        @DisplayName("findById should return a non-empty Optional<UserPhraseDto> when UserPhrase exists")
        void findById_whenExists_returnsNonEmptyOptional() {
            UserPhraseDto expected = PersistenceTestFactory.createGenericUserPhraseDto();

            Optional<UserPhraseDto> foundOpt = userPhraseDao.findByUserPhraseId(USER_ID, PHRASE_ID);

            assertThat(foundOpt).isPresent();
            assertRecursiveEqualsIgnoringId(foundOpt.get(), expected);
        }

        @Test
        @DisplayName("findById should return empty Optional<UserPhraseDto> when UserPhrase not exists")
        void findUserPhraseById_whenNotExists_returnsEmptyOptional() {
            Optional<UserPhraseDto> found = userPhraseDao.findByUserPhraseId(USER_ID_NOT_PRESENT, PHRASE_ID);
            assertThat(found).isNotPresent();
        }

        @Test
        @DisplayName("findAllByUserId should return all user phrases for a user when present")
        void findAllByUserId_whenPresent_returnsList() {
            List<UserPhraseDto> userPhrases = userPhraseDao.findAllByUserId(USER_ID);

            assertThat(userPhrases).isNotNull();
            assertThat(userPhrases)
                    .allMatch(up -> up.getUserDto() != null)
                    .map(UserPhraseDto::getUserDto)
                    .allMatch(userDto -> USER_ID.equals(userDto.getId()));
        }

        @Test
        @DisplayName("findAllByUserId should return empty list when user not present")
        void findAllByUserId_whenNotPresent_returnsEmptyList() {
            List<UserPhraseDto> userPhrases = userPhraseDao.findAllByUserId(USER_ID_NOT_PRESENT);
            assertThat(userPhrases).isEmpty();
        }
    }

    /**
     * Tests for updating user phrase fields and verifying persistence.
     */
    @Nested
    class Update {
        @Test
        @DisplayName("markAsReadAndIncrement should return non-empty Optional<UserPhraseDto> with read field set to true and incremented readCount")
        void markAsReadAndIncrement_whenUserPhraseExists_returnNonEmptyOptional() {
            Optional<UserPhraseDto> exceptedOpt = userPhraseDao.findByUserPhraseId(
                    USER_PHRASE_ID.getUserId(), USER_PHRASE_ID.getPhraseId());
            assertThat(exceptedOpt).isPresent();

            Optional<UserPhraseDto> updatedOpt = userPhraseDao.markAsReadAndIncrement(
                    USER_PHRASE_ID.getUserId(), USER_PHRASE_ID.getPhraseId());
            assertThat(updatedOpt).isPresent();

            UserPhraseDto expected = exceptedOpt.get();
            UserPhraseDto updated = updatedOpt.get();
            assertThat(updated.getRead()).isTrue();
            assertThat(updated.getReadCount()).isEqualTo(expected.getReadCount() + 1);
        }

        @Test
        @DisplayName("markAsReadAndIncrement should return empty Optional<UserPhraseDto> when user phrase not exists")
        void markAsReadAndIncrement_whenUserPhraseNotExists_returnsEmptyOptional() {
            Optional<UserPhraseDto> userPhraseOpt = userPhraseDao.markAsReadAndIncrement(
                    USER_PHRASE_ID_TO_SAVE.getUserId(), USER_PHRASE_ID_TO_SAVE.getPhraseId());
            assertThat(userPhraseOpt).isNotPresent();
        }
    }

    /**
     * Tests for resetting read flags for all user phrases of a user.
     */
    @Nested
    class Reset {
        @Test
        @DisplayName("resetReadFlag should set read to false for all user phrases of a user")
        void resetReadFlag_setsReadToFalse() {
            List<UserPhraseDto> resetList = userPhraseDao.resetReadFlag(USER_ID);
            assertThat(resetList).isNotNull();
            assertThat(resetList).allMatch(up -> up.getRead() == Boolean.FALSE);
        }
    }
}

