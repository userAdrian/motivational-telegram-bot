package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.TelegramFileDto;

import java.util.Optional;

/**
 * Data access object for managing {@link TelegramFileDto} entities.
 */
public interface TelegramFileDao {

    /**
     * Retrieves a {@link TelegramFileDto} by its name.
     *
     * @param name the name of the Telegram file
     * @return an {@link Optional} containing the found {@link TelegramFileDto}, or empty if not found
     */
    Optional<TelegramFileDto> getTelegramFileByName(String name);

    /**
     * Retrieves the Telegram file ID by its name.
     *
     * @param name the name of the Telegram file
     * @return the Telegram file ID, or {@code null} if not found
     */
    String getTelegramIdByName(String name);

    /**
     * Saves the given {@link TelegramFileDto} to the persistence storage.
     *
     * @param telegramFileDto the DTO to save
     * @return the saved {@link TelegramFileDto}
     */
    TelegramFileDto saveTelegramFile(TelegramFileDto telegramFileDto);
}
