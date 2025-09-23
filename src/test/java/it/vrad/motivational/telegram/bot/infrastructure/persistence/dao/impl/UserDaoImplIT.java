package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.model.constants.UserConstants;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.BaseTestRepository;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.PersistenceTestFactory;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.DaoTestConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.TELEGRAM_ID_NOT_PRESENT;
import static it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants.USER_TELEGRAM_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration tests for {@link it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl.UserDaoImpl}.
 * <p>
 * This class verifies the correct behavior of the UserDaoImpl's CRUD operations, including:
 * <ul>
 *   <li>Creating users and verifying persistence</li>
 *   <li>Reading users by Telegram ID and filtering valid users</li>
 *   <li>Updating user fields and ensuring changes are persisted</li>
 * </ul>
 * <p>
 * The tests use a test configuration that loads only the required beans and mappers for isolation.
 * Test data is provided via {@code data.sql} and {@link it.vrad.motivational.telegram.bot.infrastructure.persistence.PersistenceTestFactory}.
 */
@Import(DaoTestConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDaoImplIT extends BaseTestRepository {
    private final UserDaoImpl userDao;

    /**
     * Tests for creating users in the database.
     */
    @Nested
    class Create {
        @Test
        @DisplayName("save should persist and return the UserDto saved")
        public void save_whenValidInput_returnsUserDto() {
            UserDto user = PersistenceTestFactory.createUserDtoToSave();
            UserDto savedUser = userDao.save(user);

            assertThat(savedUser.getId()).isNotNull();
            assertThat(savedUser.getChatDto().getId()).isNotNull();

            assertRecursiveEqualsIgnoringId(savedUser, user);
        }
    }

    /**
     * Tests for reading users from the database, including filtering by valid roles.
     */
    @Nested
    class Read {
        @Test
        @DisplayName("findByTelegramId should return a non-empty Optional<UserDto> when the user exists")
        public void findByTelegramId_whenUserExists_returnsNonEmptyOptional() {
            UserDto expectedUser = PersistenceTestFactory.createGenericUserDto();

            Optional<UserDto> userOpt = userDao.findByTelegramId(expectedUser.getTelegramId());

            assertThat(userOpt)
                    .as("User should be found by telegramId: " + expectedUser.getTelegramId())
                    .isPresent();

            assertRecursiveEqualsIgnoringId(userOpt.get(), expectedUser);
        }

        @Test
        @DisplayName("findByTelegramId should return a empty Optional<UserDto> when the user NOT exists")
        public void findByTelegramId_whenUserNotExists_returnsEmptyOptional() {
            Optional<UserDto> userOpt = userDao.findByTelegramId(TELEGRAM_ID_NOT_PRESENT);

            assertThat(userOpt)
                    .as("User should NOT be found by telegramId: " + TELEGRAM_ID_NOT_PRESENT)
                    .isNotPresent();
        }

        @Test
        @DisplayName("findAllValidUsers should return only users with valid roles")
        public void findAllValidUsers_returnsOnlyValidUserDtos() {
            List<UserDto> validUsers = userDao.findAllValidUsers();

            assertThat(validUsers)
                    .extracting(UserDto::getUserRole)
                    .doesNotContainAnyElementsOf(UserConstants.USER_NOT_VALID_ROLES);

            assertThat(validUsers)
                    .anyMatch(u -> USER_TELEGRAM_ID.equals(u.getTelegramId()))
                    .anyMatch(u -> UserRole.ADMIN.equals(u.getUserRole()));
        }
    }

    /**
     * Tests for updating user fields and verifying persistence.
     */
    @Nested
    class Update {
        @Test
        @DisplayName("update should update the user fields and return the updated UserDto")
        public void update_whenValidInput_returnsUserDto() throws Exception {
            // Prepare a User DTO containing the fields to update
            UserDto update = PersistenceTestFactory.createUserDtoToUpdate();

            UserDto updated = userDao.update(USER_TELEGRAM_ID, update);
            assertThat(updated.getUserRole()).isEqualTo(UserRole.ADMIN);

            // Find again to confirm persistence
            Optional<UserDto> found = userDao.findByTelegramId(USER_TELEGRAM_ID);
            assertThat(found).isPresent();
            assertThat(updated.getTelegramId()).isEqualTo(USER_TELEGRAM_ID);
            assertThat(updated.getUserRole()).isEqualTo(UserRole.ADMIN);
        }

        @Test
        @DisplayName("update should throw NoSuchUserException when user is not found")
        public void update_whenUserNotFound_throwsNoSuchUserException() throws Exception {
            // Prepare a User DTO containing the fields to update
            UserDto update = PersistenceTestFactory.createUserDtoToUpdate();

            NoSuchUserException ex = assertThrows(
                    NoSuchUserException.class,
                    () -> userDao.update(TELEGRAM_ID_NOT_PRESENT, update)
            );

            assertThat(ex.getTelegramId()).isEqualTo(TELEGRAM_ID_NOT_PRESENT);
        }
    }

}
