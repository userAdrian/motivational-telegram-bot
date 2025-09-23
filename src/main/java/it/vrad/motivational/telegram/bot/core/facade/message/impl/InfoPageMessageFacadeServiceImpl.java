package it.vrad.motivational.telegram.bot.core.facade.message.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.core.facade.message.InfoPageMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.service.message.InfoPageMessageService;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static it.vrad.motivational.telegram.bot.core.facade.util.FacadeUtility.execWithWrap;


/**
 * Implementation of {@link InfoPageMessageFacadeService} for info page message operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class InfoPageMessageFacadeServiceImpl implements InfoPageMessageFacadeService {
    private final UserService userService;
    private final InfoPageMessageService infoPageMessageService;
    private final TelegramService telegramService;

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     */
    @Override
    public void forwardToInfoPage(IncomingMessageContext incomingMessageContext) {
        execWithWrap(incomingMessageContext, "forward to info page", this::doForwardToInfoPage);
    }

    private void doForwardToInfoPage(IncomingMessageContext incomingMessageContext)
            throws NoSuchUserException, UserNotValidException {
        Message message = incomingMessageContext.getMessageSent();

        // Find and validate the user from the database if absent inside the context
        UserDto userFromDB = userService.findValidUserIfAbsent(incomingMessageContext, message);

        // Generate the welcome message DTO for the user
        MessageDto messageDto = infoPageMessageService.generateInfoMessageDto(userFromDB);

        // Edit the message media to show the welcome message
        Message editedMessage = telegramService.editMessageMedia(message, messageDto);

        // Persist Telegram file info after sending
        infoPageMessageService.persistTelegramFile(editedMessage, messageDto.getTelegramFileId());
    }

}
