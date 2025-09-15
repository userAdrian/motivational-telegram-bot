package it.vrad.motivational.telegram.bot.core.util;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.UserRole;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionUtility;
import it.vrad.motivational.telegram.bot.core.exception.ReservedCommandException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.ChatDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * Utility class for user-related operations and validation.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoUtility {

    /**
     * Retrieves the chat ID from a UserDto.
     *
     * @param userDto the user DTO
     * @return chat ID
     * @throws NoSuchElementException if chatDto or telegramId is not present
     */
    public static Long getChatId(UserDto userDto) {
        return Optional.ofNullable(userDto)
                .map(UserDto::getChatDto)
                .map(ChatDto::getTelegramId)
                .orElseThrow(() -> ExceptionUtility.createNoSuchElementException("telegramId", "userDto.chatDto"));
    }

    /**
     * Validates if the user is valid.
     *
     * @param user the user DTO
     * @return the same user DTO if valid
     * @throws UserNotValidException if user is invalid
     */
    public static UserDto validateUser(UserDto user) {
        if (user.isInvalid()) {
            throw new UserNotValidException(user.getTelegramId(), user.getUserRole());
        }

        return user;
    }

    /**
     * Validates if the user is authorized for a command.
     *
     * @param command   the command string
     * @param user      the user DTO
     * @param userRoles allowed roles
     * @return the same user DTO if authorized
     * @throws ReservedCommandException if user is unauthorized
     */
    public static UserDto validateUserAuthorization(String command, UserDto user, Set<UserRole> userRoles) {
        if (user.isUnauthorized((userRoles))) {
            throw new ReservedCommandException(command, userRoles);
        }

        return user;
    }

}
