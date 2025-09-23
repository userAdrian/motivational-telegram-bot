package it.vrad.motivational.telegram.bot.core.service.cooldown;

import it.vrad.motivational.telegram.bot.core.exception.CooldownException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;

import java.util.Optional;

/**
 * Service interface for managing command cooldowns for users.
 * <p>
 * Provides methods to apply and retrieve cooldowns for specific commands and users, taking into account user roles
 * that may exempt them from cooldowns.
 * </p>
 */
public interface CooldownManager {

    /**
     * Applies the cooldown logic for a specific {@link CooldownType} and user.
     * Applies the cooldown if the user does not have a role that skips it.
     *
     * @param cooldownType the type of the cooldown to apply (e.g., RANDOM_PHRASE, COMMAND_X, etc.)
     * @param cooldownId   the ID of the cooldown to apply
     * @param user         the user for whom the cooldown is being applied
     */
    void applyCooldown(CooldownType cooldownType, Long cooldownId, UserDto user);

    /**
     * Retrieves the cooldown for the specified command type and user.
     * Performs cooldown check logic based on the user's roles.
     * <p>
     * See also {@link CooldownService#findCooldownByUserIdAndType(Long, CooldownType, boolean)}
     *
     * @param cooldownType the type of the cooldown to retrieve
     * @param user         the user for whom to retrieve the cooldown
     * @return an Optional containing the cooldown if found, otherwise empty
     * @throws CooldownException if cooldown apply and it's active
     */
    Optional<CooldownDto> getCooldown(CooldownType cooldownType, UserDto user) throws CooldownException;
}
