package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

/**
 * Data Access Object interface for cooldown entities.
 */
public interface CooldownDao {
    /**
     * Saves a cooldown entity to the database.
     *
     * @param cooldownDto the cooldown DTO to save
     * @return the saved CooldownDto
     */
    CooldownDto save(CooldownDto cooldownDto);

    /**
     * Finds a cooldown by user ID and cooldown type.
     *
     * @param userId the ID of the user
     * @param type   the type of cooldown
     * @return an Optional containing the CooldownDto if found, or empty if not found
     */
    Optional<CooldownDto> findByUserIdAndType(Long userId, CooldownType type);

    /**
     * Partially updates fields of a cooldown entity identified by its ID.
     *
     * @param id                 the ID of the cooldown to update
     * @param partialCooldownDto a DTO containing only the fields to update
     * @return the updated CooldownDto
     * @throws EntityNotFoundException when entity with {@code id} is not found
     */
    CooldownDto updateCooldown(Long id, CooldownDto partialCooldownDto);
}
