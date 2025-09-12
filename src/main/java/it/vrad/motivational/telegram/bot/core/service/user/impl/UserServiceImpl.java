package it.vrad.motivational.telegram.bot.core.service.user.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.UserDao;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserService} for user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    /**
     * Constructs a UserServiceImpl with the provided UserDao.
     *
     * @param userDao the user data access object
     */
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * {@inheritDoc}
     *
     * @param telegramUserId {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NoSuchUserException {@inheritDoc}
     */
    @Override
    public UserDto findValidUser(Long telegramUserId) {
        // Find user by Telegram ID, throw if not found
        UserDto user = userDao.findUserByTelegramId(telegramUserId)
                .orElseThrow(() -> new NoSuchUserException(telegramUserId));

        // Validate user properties
        UserDtoUtility.validateUser(user);

        return user;
    }
}
