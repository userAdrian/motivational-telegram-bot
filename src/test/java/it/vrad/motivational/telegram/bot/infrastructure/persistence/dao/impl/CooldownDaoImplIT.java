package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionLogMessageHelper;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.BaseTestRepository;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.DaoTestConfig;
import it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.OffsetDateTime;
import java.util.Optional;

import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.COOLDOWN_ID;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.COOLDOWN_ID_NOT_PRESENT;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.USER_ID;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.USER_ID_NOT_PRESENT;
import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.USER_ID_WITHOUT_COOLDOWN;
import static it.vrad.motivational.telegram.bot.shared.test.util.TestAssertions.assertRecursiveEquals;
import static it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory.createCooldownDtoToSave;
import static it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory.createGenericCooldownDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration tests for {@link CooldownDaoImpl}.
 * <p>
 * This class verifies the correct behavior of CooldownDaoImpl's CRUD operations, including:
 * <ul>
 *   <li>Creating cooldowns and verifying persistence</li>
 *   <li>Reading cooldowns by user and type</li>
 *   <li>Updating cooldown fields and ensuring changes are persisted</li>
 *   <li>Handling not found scenarios and exceptions</li>
 * </ul>
 * <p>
 * The tests use a test configuration that loads only the required beans and mappers for isolation.
 * Test data is provided via {@code data.sql} and
 * {@link PersistenceTestFactory}.
 */
@Import(DaoTestConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CooldownDaoImplIT extends BaseTestRepository {
    private final CooldownDaoImpl cooldownDao;

    /**
     * Tests for creating cooldowns in the database.
     */
    @Nested
    class Create {
        @Test
        @DisplayName("save should persist and return the CooldownDto saved")
        void save_whenValidInput_returnsCooldownDto() {
            CooldownDto cooldown = createCooldownDtoToSave(CooldownType.RANDOM_PHRASE);

            CooldownDto saved = cooldownDao.save(cooldown);
            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getUserDto().getId()).isEqualTo(USER_ID_WITHOUT_COOLDOWN);
            assertThat(saved.getType()).isEqualTo(CooldownType.RANDOM_PHRASE);

            // Find again to confirm persistence
            Optional<CooldownDto> found = cooldownDao.findByUserIdAndType(
                    USER_ID_WITHOUT_COOLDOWN, CooldownType.RANDOM_PHRASE);

            assertThat(found)
                    .isPresent()
                    .get()
                    .hasFieldOrPropertyWithValue("id", saved.getId());
        }
    }

    /**
     * Tests for reading cooldowns from the database.
     */
    @Nested
    class Read {
        @Test
        @DisplayName("findByUserIdAndType should return non-empty Optional<CooldownDto> when cooldown exists")
        void findByUserIdAndType_whenExists_returnsNonEmptyOptional() {
            CooldownDto expected = createGenericCooldownDto(CooldownType.RANDOM_PHRASE);

            Optional<CooldownDto> found = cooldownDao.findByUserIdAndType(USER_ID, CooldownType.RANDOM_PHRASE);
            assertThat(found).isPresent();
            assertRecursiveEquals(found.get(), expected);
        }

        @Test
        @DisplayName("findByUserIdAndType should return empty Optional<CooldownDto> when cooldown not present")
        void findByUserIdAndType_whenNotExists_returnsEmptyOptional() {
            Optional<CooldownDto> found = cooldownDao.findByUserIdAndType(USER_ID_NOT_PRESENT, CooldownType.RANDOM_PHRASE);
            assertThat(found).isNotPresent();
        }
    }

    /**
     * Tests for updating cooldown fields and verifying persistence.
     */
    @Nested
    class Update {
        @Test
        @DisplayName("updateCooldown should update fields and return updated CooldownDto")
        void updateCooldown_whenValidInput_returnsUpdatedCooldownDto() {
            CooldownDto update = CooldownDto.builder()
                    .endingTime(OffsetDateTime.now())
                    .build();

            CooldownDto updated = cooldownDao.updateCooldown(COOLDOWN_ID, update);
            assertThat(updated).hasFieldOrPropertyWithValue("endingTime", update.getEndingTime());

        }

        @Test
        @DisplayName("updateCooldown should throw EntityNotFoundException if id not found")
        void updateCooldown_whenIdNotFound_throwsEntityNotFoundException() {
            assertThatThrownBy(() -> cooldownDao.updateCooldown(COOLDOWN_ID_NOT_PRESENT, CooldownDto.builder().build()))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(ExceptionLogMessageHelper.getCooldownNotFound(COOLDOWN_ID_NOT_PRESENT));
        }
    }
}
