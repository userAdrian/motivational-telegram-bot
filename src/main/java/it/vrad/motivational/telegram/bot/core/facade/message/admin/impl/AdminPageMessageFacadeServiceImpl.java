package it.vrad.motivational.telegram.bot.core.facade.message.admin.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.ReservedCommandException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.core.facade.message.admin.AdminPageMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.model.constants.PageConstants;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.service.message.AdminMessageService;
import it.vrad.motivational.telegram.bot.core.service.telegram.TelegramService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static it.vrad.motivational.telegram.bot.core.facade.util.FacadeUtility.execWithWrap;


/**
 * Implementation of {@link AdminPageMessageFacadeService}
 * for initial message operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AdminPageMessageFacadeServiceImpl implements AdminPageMessageFacadeService {
    private final UserService userService;
    private final AdminMessageService adminMessageService;
    private final TelegramService telegramService;

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     */
    @Override
    public void forwardToAdminPage(IncomingMessageContext incomingMessageContext) {
        execWithWrap(incomingMessageContext, "forward to admin page", this::doForwardToAdminPage);
    }

    private void doForwardToAdminPage(IncomingMessageContext incomingMessageContext)
            throws NoSuchUserException, UserNotValidException, ReservedCommandException {
        // Find and validate the user from the database if absent inside the context
        UserDto userDto = userService.validateUserForPage(
                incomingMessageContext,
                PageConstants.AdminPage.PAGE_REFERENCE
        );

        // Generate the welcome message DTO for the user
        MessageDto messageDto = adminMessageService.generateAdminPageMessageDto(userDto);

        Message message = incomingMessageContext.getSentMessage();
        // Edit the message media to show the welcome message
        Message editedMessage = telegramService.editMessageMedia(message, messageDto);

        // Persist Telegram file info after sending
        adminMessageService.persistTelegramFile(editedMessage, messageDto.getTelegramFileId());
    }


}
