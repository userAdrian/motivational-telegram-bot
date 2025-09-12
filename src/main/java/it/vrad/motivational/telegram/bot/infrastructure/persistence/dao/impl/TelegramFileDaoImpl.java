package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.TelegramFileDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.entity.TelegramFile;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.AbstractDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.TelegramFileDao;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.TelegramFileToTelegramFileDtoMapper;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.repository.TelegramFileRepository;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link TelegramFileDao}
 * for managing persistence operations related to Telegram files.
 */
@Component
public class TelegramFileDaoImpl extends AbstractDao<TelegramFile, TelegramFileDto> implements TelegramFileDao {

    private final TelegramFileRepository telegramFileRepository;

    public TelegramFileDaoImpl(TelegramFileRepository telegramFileRepository,
                               TelegramFileToTelegramFileDtoMapper telegramFileToTelegramFileDtoMapper) {
        super(telegramFileToTelegramFileDtoMapper);
        this.telegramFileRepository = telegramFileRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @param name {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Optional<TelegramFileDto> getTelegramFileByName(String name) {
        Objects.requireNonNull(name);

        // Find entity by name and map to DTO
        return telegramFileRepository.findByName(name)
                .map(super::toDto)
                .or(Optional::empty);
    }

    /**
     * {@inheritDoc}
     *
     * @param name {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String getTelegramIdByName(String name) {
        // Retrieve DTO by name
        Optional<TelegramFileDto> telegramFileDto = getTelegramFileByName(name);

        // Return telegramId if present, otherwise null
        return telegramFileDto.map(TelegramFileDto::getTelegramId).orElse(null);
    }

    /**
     * {@inheritDoc}
     *
     * @param telegramFileDto {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public TelegramFileDto saveTelegramFile(TelegramFileDto telegramFileDto) {
        TelegramFile entity = toEntity(telegramFileDto);

        // Save entity to repository
        TelegramFile savedEntity = telegramFileRepository.save(entity);
        // Convert saved entity back to DTO
        return toDto(savedEntity);
    }

}
