package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;


import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.model.constants.UserConstants;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.AbstractDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.UserDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Chat;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.User;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.UserToUserDtoMapper;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link UserDao} for managing persistence operations related to users.
 */
@Component
@Slf4j
public class UserDaoImpl extends AbstractDao<User, UserDto> implements UserDao {

    private final UserRepository userRepository;

    public UserDaoImpl(UserRepository userRepository,
                       UserToUserDtoMapper userToUserDtoMapper) {
        super(userToUserDtoMapper);
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param telegramId {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public Optional<UserDto> findUserByTelegramId(Long telegramId) {
        Objects.requireNonNull(telegramId);

        // Find user by Telegram ID and map to DTO
        return userRepository.findByTelegramId(telegramId)
                .map(super::toDto)
                .or(Optional::empty);
    }

    /**
     * {@inheritDoc}
     *
     * @param userDto {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public UserDto saveUser(UserDto userDto) {
        Objects.requireNonNull(userDto);

        User entity = toEntity(userDto);

        // Set the user reference in chat if present
        Chat chat = entity.getChat();
        if (chat != null) {
            chat.setUser(entity);
        }

        // Save entity and return as DTO
        return toDto(userRepository.save(entity));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public List<UserDto> findAllValidUsers() {
        // Retrieve all users whose roles are not in the invalid roles list
        return toDto(userRepository.findAllByUserRoleNotIn(UserConstants.USER_NOT_VALID_ROLES));
    }

    /**
     * {@inheritDoc}
     *
     * @param telegramId {@inheritDoc}
     * @param userDto    {@inheritDoc}
     * @return {@inheritDoc}
     * @throws NoSuchUserException {@inheritDoc}
     */
    @Override
    public UserDto updateUser(Long telegramId, UserDto userDto) {
        Objects.requireNonNull(telegramId);
        Objects.requireNonNull(userDto);

        // Find user by Telegram ID, update, save, and return as DTO
        return userRepository.findByTelegramId(telegramId)
                .map(user -> partialUpdate(user, userDto))
                .map(userRepository::save)
                .map(super::toDto)
                .orElseThrow(() -> new NoSuchUserException(telegramId));
    }
}
