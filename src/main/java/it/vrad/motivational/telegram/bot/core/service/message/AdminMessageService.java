package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;

/**
 * Service interface for admin message operations.
 * <p>
 * Provides methods for forwarding to the admin page and processing file phrase commands.
 */
public interface AdminMessageService {

    /**
     * Generates the admin page message DTO for the given user.
     *
     * @param user the user for whom to generate the admin page message
     * @return the generated {@link MessageDto} for the admin page
     */
    MessageDto generateAdminPageMessageDto(UserDto user);

    /**
     * Persists the Telegram file information after sending a message.
     *
     * @param sentMessage        the sent Telegram {@link Message}
     * @param telegramFileIdSent the Telegram file ID associated with the sent message
     */
    void persistTelegramFile(Message sentMessage, String telegramFileIdSent);

    /**
     * Sends instructions to the user for loading file phrases as part of the admin command process.
     *
     * @param chatId the chat ID to which the instructions should be sent
     */
    void sendLoadFilePhrasesCommandMessage(Long chatId);

    /**
     * Sends a notification to the user indicating successful upload of a file containing phrases.
     *
     * @param chatId the chat ID to which the notification should be sent
     */
    void sendFileUploadSuccessNotification(Long chatId);
}
