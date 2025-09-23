package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * Data access object for managing {@link UserDto} entities.
 */
public interface UserDao {

    /**
     * Finds a user by their Telegram ID.
     *
     * @param telegramId the Telegram ID of the user
     * @return an {@link Optional} containing the found {@link UserDto}, or empty if not found
     */
    Optional<UserDto> findByTelegramId(Long telegramId);

    /**
     * Saves the given {@link UserDto} to the persistence storage.
     *
     * @param userDto the DTO to save
     * @return the saved {@link UserDto}
     */
    UserDto save(UserDto userDto);

    /**
     * Retrieves all valid users.
     *
     * @return a list of valid {@link UserDto} objects
     */
    List<UserDto> findAllValidUsers();

    /**
     * Updates the user with the given Telegram ID using the provided {@link UserDto}.
     *
     * @param telegramId the Telegram ID of the user to update
     * @param userDto    the DTO containing updated user information
     * @return the updated {@link UserDto}
     * @throws NoSuchUserException if no user with the given Telegram ID exists
     */
    UserDto update(Long telegramId, UserDto userDto) throws NoSuchUserException;
}
