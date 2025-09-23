package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;

/**
 * Service interface for info page message operations.
 */
public interface InfoPageMessageService {

    /**
     * Generates the info page message DTO for the given user.
     *
     * @param user the user for whom to generate the info page message
     * @return the generated {@link MessageDto} for the info page
     */
    MessageDto generateInfoMessageDto(UserDto user);

    /**
     * Persists the Telegram file information after sending a message.
     *
     * @param sentMessage        the sent Telegram {@link Message}
     * @param telegramFileIdSent the Telegram file ID associated with the sent message
     */
    void persistTelegramFile(Message sentMessage, String telegramFileIdSent);
}
