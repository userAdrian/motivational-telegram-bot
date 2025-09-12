package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.AuthorDto;

/**
 * Data Access Object interface for author entities.
 */
public interface AuthorDao {
    /**
     * Retrieves the ID of an author by first and last name. If the author does not exist, persists it and returns the new ID.
     *
     * @param authorDto the {@link AuthorDto} instance
     * @return the ID of the author
     */
    Long getOrCreateAuthorId(AuthorDto authorDto);
}

