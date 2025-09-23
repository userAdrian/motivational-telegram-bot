package it.vrad.motivational.telegram.bot.core.facade.message.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.core.facade.message.InitialPageMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.service.message.InitialMessageService;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static it.vrad.motivational.telegram.bot.core.facade.util.FacadeUtility.execWithWrap;


/**
 * Implementation of {@link InitialPageMessageFacadeService} for initial message operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class InitialPageMessageFacadeServiceImpl implements InitialPageMessageFacadeService {
    private final UserService userService;
    private final InitialMessageService initialMessageService;
    private final TelegramService telegramService;

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<MessageDto> processInitialMessage(IncomingMessageContext incomingMessageContext) {
        return execWithWrap(incomingMessageContext, "process initial message", this::doProcessInitialMessage);
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     */
    @Override
    public void forwardToInitialPage(IncomingMessageContext incomingMessageContext) {
        execWithWrap(incomingMessageContext, "forward to initial page", this::doForwardToInitialPage);
    }

    private Optional<MessageDto> doProcessInitialMessage(IncomingMessageContext incomingMessageContext)
            throws UserNotValidException {
        Message message = incomingMessageContext.getMessageSent();

        // Save or validate the user based on the incoming message
        UserDto userFromDB = userService.saveOrValidateUser(message);

        // Generate the welcome message DTO
        MessageDto messageDto = initialMessageService.generateInitialMessageDto(message.getFrom(), userFromDB);

        // Send the welcome photo message
        Message sentMessage = telegramService.sendPhoto(message, messageDto);

        // Persist Telegram file info after sending
        initialMessageService.persistTelegramFile(sentMessage, messageDto.getTelegramFileId());

        return Optional.empty();
    }

    private Optional<MessageDto> doForwardToInitialPage(IncomingMessageContext incomingMessageContext)
            throws NoSuchUserException, UserNotValidException {
        Message message = incomingMessageContext.getMessageSent();

        // Find and validate the user from the database if absent inside the context
        UserDto userFromDB = userService.findValidUserIfAbsent(incomingMessageContext, message);

        // Generate the welcome message DTO for the user
        MessageDto messageDto = initialMessageService.generateInitialMessageDto(message.getFrom(), userFromDB);

        // Edit the message media to show the welcome message
        telegramService.editMessageMedia(message, messageDto);

        return Optional.empty();
    }

}
