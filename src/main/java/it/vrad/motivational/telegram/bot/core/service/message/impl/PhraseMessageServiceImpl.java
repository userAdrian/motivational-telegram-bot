package it.vrad.motivational.telegram.bot.core.service.message.impl;

import it.vrad.motivational.telegram.bot.config.properties.CommandProperties;
import it.vrad.motivational.telegram.bot.infrastructure.exception.constants.ExceptionMessageConstants;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import it.vrad.motivational.telegram.bot.core.exception.NoSuchPhraseException;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.core.service.cooldown.CooldownService;
import it.vrad.motivational.telegram.bot.core.service.message.PhraseMessageService;
import it.vrad.motivational.telegram.bot.core.service.phrase.history.PhraseSentHistoryService;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseService;
import it.vrad.motivational.telegram.bot.core.service.phrase.user.UserPhraseService;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


/**
 * Implementation of {@link PhraseMessageService}
 * for phrase message operations.
 */
@RequiredArgsConstructor
@Service
public class PhraseMessageServiceImpl implements PhraseMessageService {
    private final UserService userService;
    private final PhraseService phraseService;
    private final UserPhraseService userPhraseService;
    private final CooldownService cooldownService;
    private final TelegramIntegrationApi telegramIntegrationApi;
    private final PhraseSentHistoryService phraseSentHistoryService;
    private final CommandProperties commandProperties;

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    @Transactional
    public MessageDto processRandomPhraseCommand(IncomingMessageContext incomingMessageContext) {
        Objects.requireNonNull(incomingMessageContext);
        Message message = incomingMessageContext.getMessageSent();

        // Retrieve valid user
        UserDto user = UserDtoUtility.findValidUserIfAbsent(incomingMessageContext, userService, message);

        // Check if the user does not have any of the roles that skip cooldown
        boolean cooldownActiveCheck = user.matchesNoRole(commandProperties.getSkipCooldownRoles());

        Long userId = user.getId();
        // Retrieve cooldown if applicable
        Optional<CooldownDto> cooldownOpt = getRandomPhraseCooldown(userId, cooldownActiveCheck);

        // Process and send a random phrase to the user
        processAndSendRandomPhrase(userId, message, user);

        Long cooldownId = cooldownOpt.map(CooldownDto::getId).orElse(null);
        handleCooldown(cooldownActiveCheck, cooldownId, user);

        return null;
    }

    /**
     * Processes and sends a random phrase to the user.
     * <p>
     * Retrieves a random phrase, sends it via Telegram, saves the sent history,
     * and updates the user's phrase read status.
     *
     * @param userId  the ID of the user
     * @param message the Telegram message
     * @param user    the UserDto of the user
     */
    private void processAndSendRandomPhrase(Long userId, Message message, UserDto user) {
        // Get a random phrase for the user, throw if none found
        PhraseDto phrase = getRandomPhraseOrThrow(userId);

        // Send the phrase to the user via Telegram and save to history
        telegramIntegrationApi.sendPhrase(MessageUtility.getChatId(message), phrase);
        phraseSentHistoryService.save(phrase, user);

        // Update the user's phrase read status
        userPhraseService.updateUserPhraseReadStatus(userId, phrase.getId());
    }

    private PhraseDto getRandomPhraseOrThrow(Long userId) {
        return phraseService.getRandomPhrase(userId)
                .orElseThrow(() -> new NoSuchPhraseException(ExceptionMessageConstants.LOG_NO_PHRASES_FOUND_MESSAGE));
    }

    private void handleCooldown(boolean cooldownActiveCheck, Long cooldownId, UserDto user) {
        // Apply cooldown if the user does not have a role that skips it
        if (cooldownActiveCheck) {
            cooldownService.updateCooldownEndingDate(cooldownId, user, CooldownType.RANDOM_PHRASE);
        }
    }

    /**
     * Retrieves the cooldown for the random phrase command for the user with ID {@code userId}.
     *
     * @param userId                  the ID of the user
     * @param skipCooldownActiveCheck whether to skip cooldown check
     * @return an Optional containing the cooldown if found
     */
    private Optional<CooldownDto> getRandomPhraseCooldown(Long userId, boolean skipCooldownActiveCheck) {
        return cooldownService.findCooldownByUserIdAndType(userId, CooldownType.RANDOM_PHRASE, skipCooldownActiveCheck);
    }

}
