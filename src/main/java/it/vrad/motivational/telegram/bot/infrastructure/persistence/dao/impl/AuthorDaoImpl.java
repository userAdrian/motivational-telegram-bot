package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.AuthorDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.AbstractDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.AuthorDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Author;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.AuthorToAuthorDtoMapper;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.repository.AuthorRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Implementation of {@link AuthorDao} for author entity persistence operations.
 */
@Component
public class AuthorDaoImpl extends AbstractDao<Author, AuthorDto> implements AuthorDao {

    private final AuthorRepository authorRepository;

    public AuthorDaoImpl(AuthorToAuthorDtoMapper authorToAuthorDtoMapper, AuthorRepository authorRepository) {
        super(authorToAuthorDtoMapper);
        this.authorRepository = authorRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param authorDto {@inheritDoc}
     * @returns {@inheritDoc}
     */
    @Override
    @Transactional
    public Long getOrCreateAuthorId(AuthorDto authorDto) {
        Objects.requireNonNull(authorDto);

        // Try to find the author by first and last name
        return authorRepository.findByFirstNameAndLastName(authorDto.getFirstName(), authorDto.getLastName())
                .map(Author::getId)
                .orElseGet(() -> {
                    // If not found, persist a new author
                    return authorRepository.save(toEntity(authorDto)).getId();
                });
    }
}

