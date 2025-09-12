package it.vrad.motivational.telegram.bot.core.service.message;

import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.User;

/**
 * Service interface for initial message operations.
 */
public interface InitialMessageService {

    /**
     * Processes the initial message ({@link CommandConstants.Initial}) sent by the user and sends a welcome photo message.
     *
     * @param incomingMessageContext the context of the incoming message
     * @return {@code null}
     */
    MessageDto processInitialMessage(IncomingMessageContext incomingMessageContext);

    /**
     * Generates the welcome message DTO for the user.
     *
     * @param user       the Telegram user
     * @param userFromDB the user entity from the database
     * @return the welcome message DTO
     */
    MessageDto generateWelcomeMessageDto(User user, UserDto userFromDB);

}
