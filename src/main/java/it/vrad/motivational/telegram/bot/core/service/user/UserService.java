package it.vrad.motivational.telegram.bot.core.service.user;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;

/**
 * Service interface for user-related operations.
 */
public interface UserService {

    /**
     * Finds and validates a user by their Telegram user ID.
     * <p>
     * Validation is performed using {@link UserDtoUtility#validateUser(UserDto)}.
     *
     * @param telegramUserId the Telegram user ID
     * @return the valid UserDto
     * @throws NoSuchUserException if the user does not exist
     */
    UserDto findValidUser(Long telegramUserId);
}
