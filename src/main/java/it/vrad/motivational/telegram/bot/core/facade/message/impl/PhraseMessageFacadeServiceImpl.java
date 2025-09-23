package it.vrad.motivational.telegram.bot.core.facade.message.impl;

import it.vrad.motivational.telegram.bot.core.exception.CooldownException;
import it.vrad.motivational.telegram.bot.core.exception.NoSuchPhraseException;
import it.vrad.motivational.telegram.bot.core.exception.NoSuchUserException;
import it.vrad.motivational.telegram.bot.core.exception.UserNotValidException;
import it.vrad.motivational.telegram.bot.core.facade.message.PhraseMessageFacadeService;
import it.vrad.motivational.telegram.bot.core.model.context.IncomingMessageContext;
import it.vrad.motivational.telegram.bot.core.model.dto.MessageDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import it.vrad.motivational.telegram.bot.core.service.cooldown.CooldownManager;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseDeliveryManager;
import it.vrad.motivational.telegram.bot.core.service.user.UserService;
import it.vrad.motivational.telegram.bot.integration.telegram.model.response.Message;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static it.vrad.motivational.telegram.bot.core.facade.util.FacadeUtility.execWithWrap;


/**
 * Implementation of {@link PhraseMessageFacadeService} for phrase message operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PhraseMessageFacadeServiceImpl implements PhraseMessageFacadeService {
    private final UserService userService;
    private final PhraseDeliveryManager phraseDeliveryManager;
    private final CooldownManager cooldownManager;

    /**
     * {@inheritDoc}
     *
     * @param incomingMessageContext {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<MessageDto> processRandomPhraseCommand(IncomingMessageContext incomingMessageContext) {
        return execWithWrap(incomingMessageContext, "random phrase command", this::doProcessRandomPhraseCommand);
    }

    private Optional<MessageDto> doProcessRandomPhraseCommand(IncomingMessageContext incomingMessageContext)
            throws CooldownException, NoSuchUserException, UserNotValidException, NoSuchPhraseException {
        Message message = incomingMessageContext.getMessageSent();

        // Retrieve valid user
        UserDto user = userService.findValidUserIfAbsent(incomingMessageContext, message);
        log.debug("User found: id={}", user.getId());

        Optional<CooldownDto> cooldownOpt = cooldownManager.getCooldown(CooldownType.RANDOM_PHRASE, user);

        // Deliver random phrase
        phraseDeliveryManager.deliverRandomPhrase(user);

        // Handle cooldown if applicable
        Long cooldownId = cooldownOpt.map(CooldownDto::getId).orElse(null);
        cooldownManager.applyCooldown(CooldownType.RANDOM_PHRASE, cooldownId, user);

        return Optional.empty();
    }

}
