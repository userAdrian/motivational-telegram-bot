package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseSentHistoryDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.BaseTestRepository;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.DaoTestConfig;
import it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static it.vrad.motivational.telegram.bot.shared.test.util.TestAssertions.assertRecursiveEqualsIgnoringId;
import static it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory.createPhraseSentHistoryDtoToSave;

/**
 * Integration tests for {@link PhraseSentHistoryDaoImpl}.
 * <p>
 * This class verifies the correct behavior of the PhraseSentHistoryDaoImpl's operations.
 * Test data is created using {@link PersistenceTestFactory}.
 */
@Import(DaoTestConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PhraseSentHistoryDaoImplIT extends BaseTestRepository {

    private final PhraseSentHistoryDaoImpl phraseSentHistoryDao;

    /**
     * Test cases for saving PhraseSentHistory entities.
     */
    @Nested
    class Create {
        @Test
        @DisplayName("save should persist and return the saved PhraseSentHistoryDto")
        void save_whenValidInput_returnsPersistedDto() {
            PhraseSentHistoryDto dto = createPhraseSentHistoryDtoToSave();
            PhraseSentHistoryDto saved = phraseSentHistoryDao.save(dto);
            assertRecursiveEqualsIgnoringId(saved, dto);
        }
    }

}
