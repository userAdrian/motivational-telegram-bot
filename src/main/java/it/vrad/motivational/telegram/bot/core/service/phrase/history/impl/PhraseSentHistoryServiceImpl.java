package it.vrad.motivational.telegram.bot.core.service.phrase.history.impl;

import it.vrad.motivational.telegram.bot.config.properties.PhraseProperties;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseSentHistoryDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.service.date.DateService;
import it.vrad.motivational.telegram.bot.core.service.phrase.history.PhraseSentHistoryService;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.PhraseSentHistoryDao;
import it.vrad.motivational.telegram.bot.core.model.factory.PersistenceDtoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;


/**
 * Implementation of {@link PhraseSentHistoryService}
 * for managing the history of sent phrases.
 */
@RequiredArgsConstructor
@Service
public class PhraseSentHistoryServiceImpl implements PhraseSentHistoryService {
    private final PhraseSentHistoryDao phraseSentHistoryDao;
    private final PhraseProperties phraseProperties;
    private final DateService dateService;

    /**
     * {@inheritDoc}
     *
     * @param phraseDto {@inheritDoc}
     * @param userDto   {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public PhraseSentHistoryDto save(PhraseDto phraseDto, UserDto userDto) {
        // Get the current time in the configured time zone
        OffsetDateTime now = dateService.getCurrentOffsetDateTime();
        // Build and persist the phrase sent history record
        return phraseSentHistoryDao.save(PersistenceDtoFactory.buildPhraseSentHistoryDto(phraseDto, userDto, now));
    }
}
