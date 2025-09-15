package it.vrad.motivational.telegram.bot.core.service.user;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;

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

    /**
     * Finds a valid user by telegramUserId if the given userDto is null.
     * <br>See also {@link UserService#findValidUser(Long)}
     *
     * @param userDto        the user DTO (may be null)
     * @param telegramUserId the Telegram user ID
     * @return valid user DTO
     */
    UserDto findValidUserIfAbsent(UserDto userDto, Long telegramUserId);

    /**
     * Finds a valid user from the context or, if absent, by extracting the user ID from the given message.
     * <br>See also {@link UserService#findValidUserIfAbsent(UserDto, Long)}
     *
     * @param incomingMessageContext the context containing (optionally) a user from the database
     * @param message                the Telegram message from which to extract the user ID if needed
     * @return the valid UserDto
     */
    default UserDto findValidUserIfAbsent(IncomingMessageContext incomingMessageContext, Message message) {
        return findValidUserIfAbsent(incomingMessageContext.getUserFromDB(), MessageUtility.getUserId(message));
    }
}
