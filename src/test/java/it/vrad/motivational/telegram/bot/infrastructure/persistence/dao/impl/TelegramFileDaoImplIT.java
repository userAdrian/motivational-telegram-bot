package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.TelegramFileDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.BaseTestRepository;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.DaoTestConfig;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.TelegramFile;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.repository.TelegramFileRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.TELEGRAM_FILE_NAME;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.TELEGRAM_FILE_NAME_NOT_PRESENT;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.TELEGRAM_FILE_TELEGRAM_ID;
import static it.vrad.motivational.telegram.bot.shared.test.util.TestAssertions.assertRecursiveEquals;
import static it.vrad.motivational.telegram.bot.shared.test.util.TestAssertions.assertRecursiveEqualsIgnoringId;
import static it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory.createGenericTelegramFileDto;
import static it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory.createTelegramFileDtoToSave;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link TelegramFileDaoImpl}.
 * <p>
 * This class verifies the correct behavior of the TelegramFileDaoImpl's CRUD operations, including:
 * <ul>
 *   <li>Saving Telegram files and verifying persistence</li>
 *   <li>Reading Telegram files by name</li>
 *   <li>Retrieving Telegram file IDs by name</li>
 * </ul>
 * <p>
 * The tests use a test configuration that loads only the required beans and mappers for isolation.
 * Test data is created directly in the test methods.
 */
@Import(DaoTestConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TelegramFileDaoImplIT extends BaseTestRepository {

    private final TelegramFileDaoImpl telegramFileDao;
    private final TelegramFileRepository telegramFileRepository;

    /**
     * Test cases for saving TelegramFile entities.
     */
    @Nested
    class Create {
        @Test
        @DisplayName("saveTelegramFile should persist and return the TelegramFileDto saved")
        void saveTelegramFile_whenValidInput_returnsTelegramFileDto() {
            TelegramFileDto dto = createTelegramFileDtoToSave();

            TelegramFileDto saved = telegramFileDao.saveTelegramFile(dto);
            assertRecursiveEqualsIgnoringId(saved, dto);

            Optional<TelegramFile> entityOpt = telegramFileRepository.findByName(dto.getName());
            assertThat(entityOpt).isPresent();
            assertThat(entityOpt.get().getTelegramId()).isEqualTo(dto.getTelegramId());
        }
    }

    /**
     * Test cases for reading TelegramFile entities.
     */
    @Nested
    class Read {
        @Test
        @DisplayName("getTelegramFileByName should return a non-empty Optional<TelegramFileDto> when the file exists")
        void getTelegramFileByName_whenFileExists_returnsNonEmptyOptional() {
            TelegramFileDto expectedTelegramFile = createGenericTelegramFileDto();

            Optional<TelegramFileDto> result = telegramFileDao.getTelegramFileByName(expectedTelegramFile.getName());
            assertThat(result).isPresent();
            assertRecursiveEquals(result.get(), expectedTelegramFile);
        }

        @Test
        @DisplayName("getTelegramFileByName should return empty Optional<TelegramFileDto> when the file does not exist")
        void getTelegramFileByName_whenFileDoesNotExist_returnsEmptyOptional() {
            Optional<TelegramFileDto> result = telegramFileDao.getTelegramFileByName(TELEGRAM_FILE_NAME_NOT_PRESENT);
            assertThat(result).isNotPresent();
        }

        @Test
        @DisplayName("getTelegramIdByName should return the telegramId when the file exists")
        void getTelegramIdByName_whenFileExists_returnsTelegramId() {
            String id = telegramFileDao.getTelegramIdByName(TELEGRAM_FILE_NAME);
            assertThat(id).isEqualTo(TELEGRAM_FILE_TELEGRAM_ID);
        }

        @Test
        @DisplayName("getTelegramIdByName should return null when the file does not exist")
        void getTelegramIdByName_whenFileDoesNotExist_returnsNull() {
            String id = telegramFileDao.getTelegramIdByName(TELEGRAM_FILE_NAME_NOT_PRESENT);
            assertThat(id).isNull();
        }
    }

}
