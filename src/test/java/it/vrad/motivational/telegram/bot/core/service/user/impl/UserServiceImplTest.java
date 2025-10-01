package it.vrad.motivational.telegram.bot.core.service.user.impl;

import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.service.auth.impl.AuthRoleServiceImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl.UserDaoImpl;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants.SAVE_USER_TELEGRAM_ID;
import static it.vrad.motivational.telegram.bot.shared.test.util.TestAssertions.assertRecursiveEquals;
import static it.vrad.motivational.telegram.bot.shared.test.util.factory.ContextTestFactory.createGenericIncomingMessageCtx;
import static it.vrad.motivational.telegram.bot.shared.test.util.factory.ContextTestFactory.createGenericIncomingMessageCtxNoUser;
import static it.vrad.motivational.telegram.bot.shared.test.util.factory.TelegramTestObjectFactory.createGenericMessage;
import static it.vrad.motivational.telegram.bot.shared.test.util.factory.TelegramTestObjectFactory.createGenericMessageNewUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserServiceImpl.
 * <p>
 * This test class groups unit tests that verify UserServiceImpl behavior such as
 * saving or validating users, loading users when absent, and role-based validation
 * for commands and pages. Tests use Mockito for mocking and JUnit 5 for structure.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private AuthRoleServiceImpl authRoleService;

    @Mock
    private UserDaoImpl userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto expected;
    private Long telegramId;

    @BeforeEach
    void setUp() {
        expected = PersistenceTestFactory.createGenericUserDto();
        telegramId = expected.getTelegramId();
    }

    /**
     * Tests for the saveOrValidateUser method of {@link UserServiceImpl}.
     * <p>
     * Covers scenarios where the user is already present and when a new user must be created.
     */
    @Nested
    @DisplayName("saveOrValidateUser")
    class SaveOrValidateUserTest {
        @Test
        @DisplayName("should return validated user when telegramId exists")
        void saveOrValidateUser_whenUserExists_returnsValidatedUser() throws Exception {
            when(userDao.findByTelegramId(telegramId)).thenReturn(Optional.of(expected));

            UserDto result = userService.saveOrValidateUser(createGenericMessage());

            assertRecursiveEquals(result, expected);
            verify(userDao).findByTelegramId(telegramId);
            verify(userDao, never()).save(any(UserDto.class));
        }

        @Test
        @DisplayName("should save and return new user when telegramId not exists")
        void saveOrValidateUser_whenUserNotExists_savesAndReturnsUser() throws Exception {
            when(userDao.findByTelegramId(SAVE_USER_TELEGRAM_ID)).thenReturn(Optional.empty());
            when(userDao.save(any(UserDto.class))).thenReturn(expected);

            UserDto result = userService.saveOrValidateUser(createGenericMessageNewUser());

            assertRecursiveEquals(result, expected);
            verify(userDao).findByTelegramId(SAVE_USER_TELEGRAM_ID);
            verify(userDao).save(any(UserDto.class));
        }
    }

    @Test
    @DisplayName("findValidUser should return UserDto when telegramId exists and user is valid")
    void findValidUser_whenValidInput_returnUserDto() throws Exception {
        when(userDao.findByTelegramId(telegramId)).thenReturn(Optional.of(expected));

        UserDto result = userService.findValidUser(telegramId);

        assertRecursiveEquals(result, expected);
        verify(userDao).findByTelegramId(telegramId);
    }

    /**
     * Tests for the findValidUserIfAbsent method of {@link UserServiceImpl}.
     * <p>
     * Verifies behavior when a user is provided, when it must be loaded from DB,
     * and when the context already contains the user.
     */
    @Nested
    @DisplayName("findValidUserIfAbsent")
    class FindValidUserIfAbsentTest {

        @Test
        @DisplayName("should return input user when provided")
        void findValidUserIfAbsent_shouldValidateAndReturnInputUser() throws Exception {
            UserDto result = userService.findValidUserIfAbsent(expected, telegramId);

            assertRecursiveEquals(result, expected);
            verify(userDao, never()).findByTelegramId(telegramId);
        }

        @Test
        @DisplayName("should load user from database when input is null")
        void findValidUserIfAbsent_shouldLoadFromDBWhenNotProvided() throws Exception {
            when(userDao.findByTelegramId(telegramId)).thenReturn(Optional.of(expected));

            UserDto result = userService.findValidUserIfAbsent(null, telegramId);

            assertRecursiveEquals(result, expected);
            verify(userDao).findByTelegramId(telegramId);
        }

        @Test
        @DisplayName("should return user from context and message when present")
        void findValidUserIfAbsent_withContextAndMessage_returnsUserDtoFromContext() throws Exception {
            IncomingMessageContext ctx = createGenericIncomingMessageCtx();
            UserDto userFromContext = ctx.getUserFromDB();

            UserDto result = userService.findValidUserIfAbsent(ctx, ctx.getSentMessage());

            assertRecursiveEquals(result, userFromContext);
            verify(userDao, never()).findByTelegramId(anyLong());
        }

        @Test
        @DisplayName("should load user from database when context has no user")
        void findValidUserIfAbsent_withContextAndMessage_returnsUserDtoFromDB() throws Exception {
            IncomingMessageContext ctx = createGenericIncomingMessageCtxNoUser();
            Message sentMessage = ctx.getSentMessage();
            Long userTelegramIdFromMessage = sentMessage.getFrom().getId();

            when(userDao.findByTelegramId(userTelegramIdFromMessage)).thenReturn(Optional.of(expected));

            UserDto result = userService.findValidUserIfAbsent(ctx, sentMessage);

            assertRecursiveEquals(result, expected);
            verify(userDao).findByTelegramId(userTelegramIdFromMessage);
        }

        @Test
        @DisplayName("should return user from context and validate roles")
        void findValidUserIfAbsent_withContextAndUserRoles_returnsUserDtoFromContext() throws Exception {
            IncomingMessageContext ctx = createGenericIncomingMessageCtx();
            expected = ctx.getUserFromDB();

            UserDto result = userService.findValidUserIfAbsent(ctx, getUserRoles(expected));

            assertRecursiveEquals(result, expected);
            verify(userDao, never()).findByTelegramId(anyLong());
        }

        @Test
        @DisplayName("should load user from database when context has no user and validate roles")
        void findValidUserIfAbsent_withContextAndUserRoles_returnsUserDtoFromDB() throws Exception {
            when(userDao.findByTelegramId(telegramId)).thenReturn(Optional.of(expected));

            IncomingMessageContext ctx = createGenericIncomingMessageCtxNoUser();
            UserDto result = userService.findValidUserIfAbsent(ctx, getUserRoles(expected));

            assertRecursiveEquals(result, expected);
            verify(userDao).findByTelegramId(telegramId);
        }
    }


    /**
     * Tests for user validation logic in {@link UserServiceImpl}.
     * <p>
     * Includes validation for commands and pages against role requirements.
     */
    @Nested
    @DisplayName("User validation")
    class ValidateUserTest {
        @Test
        @DisplayName("should return validated user for command when user is valid")
        void validateUserForCommand_whenValidUser_returnUserDto() throws Exception {
            String command = CommandConstants.RandomPhrase.TEXT;

            when(authRoleService.getAuthRolesForCommand(command))
                    .thenReturn(getUserRoles(expected));
            when(userDao.findByTelegramId(telegramId)).thenReturn(Optional.of(expected));

            IncomingMessageContext ctx = createGenericIncomingMessageCtxNoUser();
            UserDto result = userService.validateUserForCommand(ctx, command);

            assertRecursiveEquals(result, expected);
            verify(authRoleService).getAuthRolesForCommand(command);
            verify(userDao).findByTelegramId(telegramId);
        }

        @Test
        @DisplayName("should return validated user for page when user is valid")
        void validateUserForPage_whenValidUser_returnUserDto() throws Exception {
            String pageReference = PageConstants.AdminPage.PAGE_REFERENCE;

            when(authRoleService.getAuthRolesForPage(pageReference))
                    .thenReturn(getUserRoles(expected));
            when(userDao.findByTelegramId(telegramId)).thenReturn(Optional.of(expected));

            IncomingMessageContext ctx = createGenericIncomingMessageCtxNoUser();
            UserDto result = userService.validateUserForPage(ctx, pageReference);

            assertRecursiveEquals(result, expected);
            verify(authRoleService).getAuthRolesForPage(pageReference);
            verify(userDao).findByTelegramId(telegramId);
        }

    }

    // --------------------------------------
    // Test helpers
    // --------------------------------------
    private static Set<UserRole> getUserRoles(UserDto expected) {
        return Collections.singleton(expected.getUserRole());
    }


}
