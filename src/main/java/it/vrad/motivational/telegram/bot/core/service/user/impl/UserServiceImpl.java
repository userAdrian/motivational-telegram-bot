package it.vrad.motivational.telegram.bot.core.service.user.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.ReservedCommandException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.model.factory.PersistenceDtoFactory;
import it.vrad.motivational.telegram.bot.core.service.auth.AuthRoleService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.UserDao;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Chat;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Implementation of {@link UserService} for user-related operations.
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final AuthRoleService authRoleService;

    /**
     * {@inheritDoc}
     *
     * @param message {@inheritDoc}
     * @return {@inheritDoc}
     * @throws UserNotValidException {@inheritDoc}
     */
    @Override
    public UserDto saveOrValidateUser(Message message) throws UserNotValidException {
        Long telegramUserId = MessageUtility.getUserId(message);

        Optional<UserDto> userOpt = userDao.findByTelegramId(telegramUserId);
        return userOpt.isPresent()
                ? UserDtoUtility.validateUser(userOpt.get())
                : saveUser(telegramUserId, message.getChat());
    }

    private UserDto saveUser(Long telegramUserId, Chat chat) {
        return userDao.save(PersistenceDtoFactory.buildInitialUserDto(telegramUserId, chat));
    }

    /**
     * {@inheritDoc}
     *
     * @param telegramUserId {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NoSuchUserException   {@inheritDoc}
     * @throws UserNotValidException {@inheritDoc}
     */
    @Override
    public UserDto findValidUser(Long telegramUserId) throws NoSuchUserException, UserNotValidException {
        // Find user by Telegram ID, throw if not found
        UserDto user = userDao.findByTelegramId(telegramUserId)
                .orElseThrow(() -> new NoSuchUserException(telegramUserId));

        // Validate user properties and return it if valid
        return UserDtoUtility.validateUser(user);
    }

    /**
     * {@inheritDoc}
     *
     * @param userDto        {@inheritDoc}
     * @param telegramUserId {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NoSuchUserException   {@inheritDoc}
     * @throws UserNotValidException {@inheritDoc}
     */
    @Override
    public UserDto findValidUserIfAbsent(UserDto userDto, Long telegramUserId)
            throws NoSuchUserException, UserNotValidException {
        if (userDto != null) {
            return UserDtoUtility.validateUser(userDto);
        }

        return findValidUser(telegramUserId);
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @param command                {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NoSuchUserException      {@inheritDoc}
     * @throws UserNotValidException    {@inheritDoc}
     * @throws ReservedCommandException {@inheritDoc}
     */
    @Override
    public UserDto validateUserForCommand(IncomingMessageContext incomingMessageContext, String command)
            throws NoSuchUserException, UserNotValidException, ReservedCommandException {
        Set<UserRole> commandAuthRoles = authRoleService.getAuthRolesForCommand(command);

        return findValidUserIfAbsent(incomingMessageContext, commandAuthRoles);
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @param page                   {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NoSuchUserException      {@inheritDoc}
     * @throws UserNotValidException    {@inheritDoc}
     * @throws ReservedCommandException {@inheritDoc}
     */
    @Override
    public UserDto validateUserForPage(IncomingMessageContext incomingMessageContext, String page)
            throws NoSuchUserException, UserNotValidException, ReservedCommandException {
        Set<UserRole> pageAuthRoles = authRoleService.getAuthRolesForPage(page);

        return findValidUserIfAbsent(incomingMessageContext, pageAuthRoles);
    }

}
