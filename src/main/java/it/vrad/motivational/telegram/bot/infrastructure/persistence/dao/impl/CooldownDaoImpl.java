package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.Cooldown;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.AbstractDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.CooldownDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.CooldownToCooldownDtoMapper;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.repository.CooldownRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;


/**
 * Implementation of {@link CooldownDao}
 * for cooldown entity persistence operations.
 */
@Component
public class CooldownDaoImpl extends AbstractDao<Cooldown, CooldownDto> implements CooldownDao {

    private final CooldownRepository cooldownRepository;

    public CooldownDaoImpl(CooldownRepository cooldownRepository,
                           CooldownToCooldownDtoMapper cooldownToCooldownDtoMapper) {
        super(cooldownToCooldownDtoMapper);
        this.cooldownRepository = cooldownRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param cooldownDto {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public CooldownDto save(CooldownDto cooldownDto) {
        Objects.requireNonNull(cooldownDto);
        return toDto(cooldownRepository.save(toEntity(cooldownDto)));
    }

    /**
     * {@inheritDoc}
     *
     * @param userId {@inheritDoc}
     * @param type   {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<CooldownDto> findByUserIdAndType(Long userId, CooldownType type) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(type);

        return cooldownRepository.findByUserIdAndType(userId, type)
                .map(super::toDto);
    }

    /**
     * {@inheritDoc}
     *
     * @param id                 {@inheritDoc}
     * @param partialCooldownDto {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<CooldownDto> updateCooldown(Long id, CooldownDto partialCooldownDto) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(partialCooldownDto);

        // Perform partial update: only non-null fields in partialCooldownDto are applied to entity
        return cooldownRepository.findById(id)
                .map(entity -> partialUpdate(entity, partialCooldownDto))
                .map(cooldownRepository::save)
                .map(super::toDto);
    }
}
