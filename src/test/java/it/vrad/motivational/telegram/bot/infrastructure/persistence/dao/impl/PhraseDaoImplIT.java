package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.BaseTestRepository;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.PersistenceTestFactory;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.DaoTestConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link PhraseDaoImpl}.
 * <p>
 * This class verifies the correct behavior of the PhraseDaoImpl's CRUD operations.
 * Test data is created using {@link PersistenceTestFactory}.
 */
@Import(DaoTestConfig.PhraseDaoImplTestConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PhraseDaoImplIT extends BaseTestRepository {

    private final PhraseDaoImpl phraseDao;

    /**
     * Test cases for saving Phrase entities.
     */
    @Nested
    class Create {
        @Test
        @DisplayName("saveAll should persist and return the saved PhraseDto list")
        void saveAll_whenValidInput_returnsPhraseDtos() {
            Set<PhraseDto> phraseDto = PersistenceTestFactory.createPhraseDtosToSave();

            List<PhraseDto> saved = phraseDao.saveAll(phraseDto);
            assertThat(saved).isNotEmpty();
            assertRecursiveEqualsIgnoringId(saved, phraseDto);
        }
    }

    /**
     * Test cases for reading Phrase entities.
     */
    @Nested
    class Read {
        @Test
        @DisplayName("findAllAvailablePhrases should return all available phrases for a user")
        void findAllAvailablePhrases_whenUserIdProvided_returnsPhrases() {
            List<PhraseDto> result = phraseDao.findAllAvailablePhrases(PersistenceTestConstants.USER_ID);
            assertThat(result).isNotEmpty();
            assertThat(result.getFirst().getText()).isEqualTo(PersistenceTestConstants.PHRASE_TEXT);
        }

        @Test
        @DisplayName("findPhraseById should return non-empty Optional<PhraseDto> when it exists")
        void findPhraseById_whenPhraseExists_returnsNonEmptyOptional() {
            PhraseDto expectedPhrase = PersistenceTestFactory.createGenericPhraseDto();

            Optional<PhraseDto> result = phraseDao.findPhraseById(PersistenceTestConstants.PHRASE_ID);
            assertThat(result).isPresent();
            assertRecursiveEqualsIgnoringId(result.get(), expectedPhrase);
        }

        @Test
        @DisplayName("findAll should return all phrases")
        void findAll_returnsAllPhrases() {
            List<PhraseDto> result = phraseDao.findAll();
            assertThat(result).isNotEmpty();
            assertThat(result.getFirst().getText()).isEqualTo(PersistenceTestConstants.PHRASE_TEXT);
        }
    }

}

