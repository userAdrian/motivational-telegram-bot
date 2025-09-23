package it.vrad.motivational.telegram.bot.core.service.phrase.impl;

import it.vrad.motivational.telegram.bot.core.exception.NoSuchPhraseException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseDeliveryManager;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseService;
import it.vrad.motivational.telegram.bot.core.service.phrase.history.PhraseSentHistoryService;
import it.vrad.motivational.telegram.bot.core.service.phrase.user.UserPhraseService;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link PhraseDeliveryManager}
 */
@RequiredArgsConstructor
@Service
public class PhraseDeliveryManagerImpl implements PhraseDeliveryManager {
    private final PhraseService phraseService;
    private final UserPhraseService userPhraseService;
    private final TelegramIntegrationApi telegramIntegrationApi;
    private final PhraseSentHistoryService phraseSentHistoryService;

    /**
     * {@inheritDoc}
     *
     * @param user {@inheritDoc}
     * @throws NoSuchPhraseException {@inheritDoc}
     */
    @Override
    public void deliverRandomPhrase(UserDto user) throws NoSuchPhraseException {
        Long userId = user.getId();

        // Get a random phrase for the user, throw if none found
        PhraseDto phrase = phraseService.getRandomPhraseOrThrow(userId);

        // Send the phrase to the user via Telegram and save to history
        telegramIntegrationApi.sendPhrase(UserDtoUtility.getTelegramChatId(user), phrase);
        phraseSentHistoryService.save(phrase, user);

        // Update the user's phrase read status
        userPhraseService.updateUserPhraseReadStatus(userId, phrase.getId());
    }
}
