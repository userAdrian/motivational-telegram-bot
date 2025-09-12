package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseSentHistoryDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.PhraseSentHistory;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.AbstractDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.PhraseSentHistoryDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.PhraseSentHistoryToPhraseSentHistoryDtoMapper;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.repository.PhraseSentHistoryRepository;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Implementation of {@link PhraseSentHistoryDao} for managing phrase sent history persistence.
 */
@Component
public class PhraseSentHistoryDaoImpl extends AbstractDao<PhraseSentHistory, PhraseSentHistoryDto> implements PhraseSentHistoryDao {

    private final PhraseSentHistoryRepository phraseSentHistoryRepository;

    public PhraseSentHistoryDaoImpl(PhraseSentHistoryRepository phraseSentHistoryRepository,
                                    PhraseSentHistoryToPhraseSentHistoryDtoMapper mapper) {
        super(mapper);
        this.phraseSentHistoryRepository = phraseSentHistoryRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param phraseSentHistoryDto {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public PhraseSentHistoryDto save(PhraseSentHistoryDto phraseSentHistoryDto) {
        Objects.requireNonNull(phraseSentHistoryDto);

        // Convert DTO to entity, save it using the repository, then convert back to DTO
        return toDto(phraseSentHistoryRepository.save(toEntity(phraseSentHistoryDto)));
    }
}
