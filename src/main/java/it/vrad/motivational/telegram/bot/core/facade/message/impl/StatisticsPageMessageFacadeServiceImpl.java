package it.vrad.motivational.telegram.bot.core.facade.message.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.core.facade.message.StatisticsPageMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.service.message.StatisticsPageMessageService;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static it.vrad.motivational.telegram.bot.core.facade.util.FacadeUtility.execWithWrap;


/**
 * Implementation of {@link StatisticsPageMessageFacadeService} for statistics page message operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class StatisticsPageMessageFacadeServiceImpl implements StatisticsPageMessageFacadeService {
    private final UserService userService;
    private final StatisticsPageMessageService statisticsPageMessageService;
    private final TelegramService telegramService;

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     */
    @Override
    public void forwardToStatisticsPage(IncomingMessageContext incomingMessageContext) {
        execWithWrap(incomingMessageContext, "forward to statistics page", this::doForwardToStatisticsPage);
    }

    private void doForwardToStatisticsPage(IncomingMessageContext incomingMessageContext)
            throws NoSuchUserException, UserNotValidException {
        Message message = incomingMessageContext.getMessageSent();

        // Find and validate the user from the database if absent inside the context
        UserDto userFromDB = userService.findValidUserIfAbsent(incomingMessageContext, message);

        // Generate the welcome message DTO for the user
        MessageDto messageDto = statisticsPageMessageService.generateStatisticsMessageDto(userFromDB);

        // Edit the message media to show the welcome message
        Message editedMessage = telegramService.editMessageMedia(message, messageDto);

        // Persist Telegram file info after sending
        statisticsPageMessageService.persistTelegramFile(editedMessage, messageDto.getTelegramFileId());
    }

}
