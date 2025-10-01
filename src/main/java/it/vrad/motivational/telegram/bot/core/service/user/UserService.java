package it.vrad.motivational.telegram.bot.core.service.user;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.ReservedCommandException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;

import java.util.Set;

/**
 * Service interface for user-related operations.
 */
public interface UserService {

    /**
     * Saves the user if not present in the database, otherwise validates the existing user.
     * <p>
     * See also {@link UserDtoUtility#validateUser(UserDto)}
     *
     * @param message the Telegram message
     * @return the saved or validated UserDto
     * @throws UserNotValidException if user is not valid
     */
    UserDto saveOrValidateUser(Message message) throws UserNotValidException;

    /**
     * Finds and validates a user by their Telegram user ID.
     * <p>
     * Validation is performed using {@link UserDtoUtility#validateUser(UserDto)}.
     *
     * @param telegramUserId the Telegram user ID
     * @return the valid UserDto
     * @throws NoSuchUserException   if the user does not exist
     * @throws UserNotValidException if the user is not valid
     */
    UserDto findValidUser(Long telegramUserId) throws NoSuchUserException, UserNotValidException;

    /**
     * Finds a valid user by telegramUserId if the given userDto is null.
     * <br>See also {@link UserService#findValidUser(Long)}
     *
     * @param userDto        the user DTO (may be null)
     * @param telegramUserId the Telegram user ID
     * @return valid user DTO
     * @throws NoSuchUserException   if the user does not exist
     * @throws UserNotValidException if the user is not valid
     */
    UserDto findValidUserIfAbsent(UserDto userDto, Long telegramUserId) throws NoSuchUserException, UserNotValidException;

    /**
     * Finds a valid user from the context or, if absent, by extracting the user ID from the given message.
     * <br>See also {@link UserService#findValidUserIfAbsent(UserDto, Long)}
     *
     * @param incomingMessageContext the context containing (optionally) a user from the database
     * @param message                the Telegram message from which to extract the user ID if needed
     * @return the valid UserDto
     * @throws NoSuchUserException   if the user does not exist
     * @throws UserNotValidException if the user is not valid
     */
    default UserDto findValidUserIfAbsent(IncomingMessageContext incomingMessageContext, Message message)
            throws NoSuchUserException, UserNotValidException {
        return findValidUserIfAbsent(incomingMessageContext.getUserFromDB(), MessageUtility.getUserId(message));
    }

    /**
     * Finds and validates a user from the context and checks authorization for the given roles.
     * <p>
     * See also {@link UserService#findValidUserIfAbsent(UserDto, Long)},
     * <br>{@link UserDtoUtility#validateUserAuthorization(String, UserDto, Set)}
     * </p>
     *
     * @param incomingMessageContext the context containing (optionally) a user from the database
     * @param userRoles              the set of roles required for authorization
     * @return the valid and authorized UserDto
     * @throws ReservedCommandException if the command is reserved and cannot be used
     * @throws NoSuchUserException      if the user does not exist
     * @throws UserNotValidException    if the user is not valid
     */
    default UserDto findValidUserIfAbsent(IncomingMessageContext incomingMessageContext, Set<UserRole> userRoles)
            throws ReservedCommandException, NoSuchUserException, UserNotValidException {
        Message message = incomingMessageContext.getSentMessage();
        // find valid user
        UserDto user = findValidUserIfAbsent(incomingMessageContext, message);

        String command = MessageUtility.resolveCommandIfPresent(message.getText());

        return UserDtoUtility.validateUserAuthorization(command, user, userRoles);
    }

    /**
     * Validates the user for the specified command, ensuring the user is authorized to execute it.
     * <p>
     * This method checks the user's validity and role authorization for the given command.
     *
     * @param incomingMessageContext the context containing (optionally) a user from the database and the incoming message
     * @param command                the command to validate authorization for
     * @return the valid and authorized UserDto
     * @throws NoSuchUserException      if the user does not exist
     * @throws UserNotValidException    if the user is not valid
     * @throws ReservedCommandException if the command is reserved and cannot be used
     */
    UserDto validateUserForCommand(IncomingMessageContext incomingMessageContext, String command)
            throws NoSuchUserException, UserNotValidException, ReservedCommandException;

    /**
     * Validates the user for the specified page, ensuring the user is authorized to access it.
     * <p>
     * This method checks the user's validity and role authorization for the given page.
     *
     * @param incomingMessageContext the context containing (optionally) a user from the database and the incoming message
     * @param page                   the page to validate authorization for
     * @return the valid and authorized UserDto
     * @throws NoSuchUserException      if the user does not exist
     * @throws UserNotValidException    if the user is not valid
     * @throws ReservedCommandException if the command is reserved and cannot be used
     */
    UserDto validateUserForPage(IncomingMessageContext incomingMessageContext, String page)
            throws NoSuchUserException, UserNotValidException, ReservedCommandException;

}
