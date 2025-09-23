package it.vrad.motivational.telegram.bot.core.facade.message.admin.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.ReservedCommandException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.core.facade.message.admin.AdminCommandFacadeService;
import it.vrad.motivational.telegram.bot.core.model.constants.CommandConstants;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.service.message.AdminMessageService;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseExtractorManager;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.infrastructure.cache.CacheService;
import it.vrad.motivational.telegram.bot.infrastructure.cache.factory.StepDetailFactory;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static it.vrad.motivational.telegram.bot.core.facade.util.FacadeUtility.execWithWrap;


/**
 * Implementation of {@link AdminCommandFacadeService} for admin commands.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCommandFacadeServiceImpl implements AdminCommandFacadeService {
    private final UserService userService;
    private final AdminMessageService adminMessageService;
    private final CacheService cacheService;
    private final PhraseExtractorManager phraseExtractorManager;

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<MessageDto> processLoadFilePhrasesCommand(IncomingMessageContext incomingMessageContext) {
        return execWithWrap(
                incomingMessageContext,
                "load file phrases command",
                this::doProcessLoadFilePhrasesCommand
        );
    }

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<MessageDto> processLoadFilePhrasesCommandStepOne(IncomingMessageContext incomingMessageContext) {
        return execWithWrap(
                incomingMessageContext,
                "load file phrases step one",
                this::doProcessLoadFilePhrasesCommandStepOne
        );
    }

    private Optional<MessageDto> doProcessLoadFilePhrasesCommand(IncomingMessageContext incomingMessageContext)
            throws NoSuchUserException, UserNotValidException, ReservedCommandException {
        Message message = incomingMessageContext.getMessageSent();
        Long chatId = MessageUtility.getChatId(message);

        // Validate user role for loading file phrases command
        userService.validateUserForCommand(incomingMessageContext, CommandConstants.LoadFilePhrases.TEXT);

        // Send command message
        adminMessageService.sendLoadFilePhrasesCommandMessage(chatId);

        // Set step detail in cache for next step
        cacheService.saveStepDetail(chatId, StepDetailFactory.loadFilePhrasesStepOne());

        return Optional.empty();
    }

    private Optional<MessageDto> doProcessLoadFilePhrasesCommandStepOne(IncomingMessageContext incomingMessageContext)
            throws NoSuchUserException, UserNotValidException, ReservedCommandException {
        Message message = incomingMessageContext.getMessageSent();
        Long chatId = MessageUtility.getChatId(message);

        // Validate user roles for loading file phrases command
        userService.validateUserForCommand(incomingMessageContext, CommandConstants.LoadFilePhrases.TEXT);

        // Download phrases file, parse and persist
        phraseExtractorManager.extractAndPersistPhrasesFromFile(chatId, message);

        // Notify user of successful upload
        adminMessageService.sendFileUploadSuccessNotification(chatId);

        // Remove step detail from cache after completion
        cacheService.removeStepDetail(chatId);

        return Optional.empty();
    }

}
