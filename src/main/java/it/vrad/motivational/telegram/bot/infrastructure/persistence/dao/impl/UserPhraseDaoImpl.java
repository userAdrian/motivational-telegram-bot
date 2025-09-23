package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserPhraseDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.AbstractDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.UserPhraseDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.UserPhrase;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.ids.UserPhraseId;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.UserPhraseToUserPhraseDtoMapper;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.repository.UserPhraseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link UserPhraseDao} for managing persistence operations related to user phrases.
 */
@Component
public class UserPhraseDaoImpl extends AbstractDao<UserPhrase, UserPhraseDto> implements UserPhraseDao {

    private final UserPhraseRepository userPhraseRepository;

    public UserPhraseDaoImpl(UserPhraseRepository userPhraseRepository,
                             UserPhraseToUserPhraseDtoMapper userPhraseToUserPhraseDtoMapper) {

        super(userPhraseToUserPhraseDtoMapper);
        this.userPhraseRepository = userPhraseRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param userId   {@inheritDoc}
     * @param phraseId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<UserPhraseDto> findByUserPhraseId(Long userId, Long phraseId) {
        // Find user phrase by composite ID and map to DTO
        return this.findById(userId, phraseId)
                .map(super::toDto)
                .or(Optional::empty);
    }

    private Optional<UserPhrase> findById(Long userId, Long phraseId) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(phraseId);

        UserPhraseId userPhraseId = new UserPhraseId(userId, phraseId);

        return userPhraseRepository.findById(userPhraseId);
    }

    /**
     * {@inheritDoc}
     *
     * @param userId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<UserPhraseDto> findAllByUserId(Long userId) {
        Objects.requireNonNull(userId);

        // Retrieve all valid user phrases for the user and map to DTOs
        return toDto(userPhraseRepository.findAllByUserId(userId));
    }

    /**
     * {@inheritDoc}
     *
     * @param userPhraseDto {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public UserPhraseDto save(UserPhraseDto userPhraseDto) {
        Objects.requireNonNull(userPhraseDto);

        // Save the user phrase entity and return as DTO
        return toDto(userPhraseRepository.save(toEntity(userPhraseDto)));
    }

    /**
     * {@inheritDoc}
     *
     * @param userId   {@inheritDoc}
     * @param phraseId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<UserPhraseDto> markAsReadAndIncrement(Long userId, Long phraseId) {
        Optional<UserPhrase> userPhraseOpt = findById(userId, phraseId);

        if (userPhraseOpt.isEmpty()) return Optional.empty();

        return userPhraseOpt
                .map(UserPhraseDaoImpl::markAsReadAndIncrement)
                .map(userPhraseRepository::save)
                .map(this::toDto);
    }

    private static UserPhrase markAsReadAndIncrement(UserPhrase userPhrase) {
        // Mark as read and increment the read count
        userPhrase.setRead(Boolean.TRUE);
        int currentCount = userPhrase.getReadCount();
        userPhrase.setReadCount(currentCount + 1);

        return userPhrase;
    }

    /**
     * {@inheritDoc}
     *
     * @param userId {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public List<UserPhraseDto> resetReadFlag(Long userId) {
        Objects.requireNonNull(userId);
        List<UserPhrase> userPhrasesToUpdate = userPhraseRepository.findAllByUserId(userId);
        userPhrasesToUpdate.forEach(userPhrase -> userPhrase.setRead(false));

        // Save all updated entities and return as DTOs
        return toDto(userPhraseRepository.saveAll(userPhrasesToUpdate));
    }

}
