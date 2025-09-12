package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.AbstractDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.PhraseDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Phrase;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.PhraseToPhraseDtoMapper;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.repository.PhraseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link PhraseDao}
 * for phrase entity persistence operations.
 */
@Component
public class PhraseDaoImpl extends AbstractDao<Phrase, PhraseDto> implements PhraseDao {

    private final PhraseRepository phraseRepository;

    public PhraseDaoImpl(PhraseRepository phraseRepository,
                         PhraseToPhraseDtoMapper phraseToPhraseDtoMapper) {
        super(phraseToPhraseDtoMapper);
        this.phraseRepository = phraseRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param userId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<PhraseDto> findAllAvailablePhrases(Long userId) {
        Objects.requireNonNull(userId);
        return toDto(phraseRepository.findAllAvailablePhrases(userId));
    }

    /**
     * {@inheritDoc}
     *
     * @param phraseId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<PhraseDto> findPhraseById(Long phraseId) {
        Objects.requireNonNull(phraseId);
        return phraseRepository.findById(phraseId).map(super::toDto);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public List<PhraseDto> findAll() {
        return phraseRepository.findAll().stream()
                .map(super::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * @param phraseDtos {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<PhraseDto> saveAll(Set<PhraseDto> phraseDtos) {
        List<Phrase> entities = phraseDtos.stream()
                .map(super::toEntity)
                .collect(Collectors.toList());
        return toDto(phraseRepository.saveAll(entities));
    }
}
