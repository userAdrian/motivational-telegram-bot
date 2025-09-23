package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;

/**
 * Service interface for statistics page message operations.
 */
public interface StatisticsPageMessageService {

    /**
     * Generates the statistics page message DTO for the given user.
     *
     * @param user the user for whom to generate the statistics page message
     * @return the generated {@link MessageDto} for the statistics page
     */
    MessageDto generateStatisticsMessageDto(UserDto user);

    /**
     * Persists the Telegram file information after sending a message.
     *
     * @param sentMessage        the sent Telegram {@link Message}
     * @param telegramFileIdSent the Telegram file ID associated with the sent message
     */
    void persistTelegramFile(Message sentMessage, String telegramFileIdSent);
}
