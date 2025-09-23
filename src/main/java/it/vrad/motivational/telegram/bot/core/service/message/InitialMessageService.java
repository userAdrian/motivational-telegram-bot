package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.User;

/**
 * Service interface for initial page message operations.
 */
public interface InitialMessageService {
    /**
     * Generates the welcome message DTO for the user.
     *
     * @param user       the Telegram user
     * @param userFromDB the user entity from the database
     * @return the welcome message DTO
     */
    MessageDto generateInitialMessageDto(User user, UserDto userFromDB);

    /**
     * Persists the Telegram file information after sending a message.
     *
     * @param sentMessage        the sent Telegram {@link Message}
     * @param telegramFileIdSent the Telegram file ID associated with the sent message
     */
    void persistTelegramFile(Message sentMessage, String telegramFileIdSent);
}
