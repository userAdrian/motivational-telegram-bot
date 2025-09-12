package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseSentHistoryDto;

/**
 * Data access object for managing {@link PhraseSentHistoryDto} entities.
 */
public interface PhraseSentHistoryDao {
    /**
     * Saves the given {@link PhraseSentHistoryDto} to the persistence storage.
     *
     * @param phraseSentHistoryDto the DTO to save
     * @return the saved {@link PhraseSentHistoryDto}
     */
    PhraseSentHistoryDto save(PhraseSentHistoryDto phraseSentHistoryDto);
}
