package it.vrad.motivational.telegram.bot.core.service.cooldown;

import it.vrad.motivational.telegram.bot.core.exception.CooldownException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

/**
 * Service interface for managing user cooldowns.
 */
public interface CooldownService {

    /**
     * Finds a cooldown for a user by type, optionally checking if the cooldown is currently active.
     *
     * @param userId        the ID of the user
     * @param type          the type of cooldown
     * @param checkIfActive whether to throw an exception if the cooldown is active
     * @return an Optional containing the cooldown if found
     * @throws CooldownException if {@code checkIfActive} is true and the cooldown is active
     */
    Optional<CooldownDto> findCooldownByUserIdAndType(Long userId, CooldownType type, boolean checkIfActive) throws CooldownException;

    /**
     * Updates the ending date of a cooldown for a user and type.
     * <p>
     * If {@code id} is null, creates a new cooldown for the user and type with the appropriate ending time.<br>
     * If {@code id} is not null, updates only the ending date of the existing cooldown to the current time.
     *
     * @param id      the ID of the cooldown to update, or null to create a new cooldown
     * @param userDto the user associated with the cooldown
     * @param type    the type of cooldown
     * @return the updated or newly created CooldownDto
     * @throws EntityNotFoundException if the cooldown with the given ID does not exist
     */
    CooldownDto updateCooldownEndingDate(Long id, UserDto userDto, CooldownType type);
}
