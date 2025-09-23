package it.vrad.motivational.telegram.bot.core.service.cooldown.impl;

import it.vrad.motivational.telegram.bot.config.properties.CommandProperties;
import it.vrad.motivational.telegram.bot.core.exception.CooldownException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import it.vrad.motivational.telegram.bot.core.service.cooldown.CooldownManager;
import it.vrad.motivational.telegram.bot.core.service.cooldown.CooldownService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link CooldownManager}
 */
@RequiredArgsConstructor
@Service
public class CooldownManagerImpl implements CooldownManager {
    private final CooldownService cooldownService;
    private final CommandProperties commandProperties;

    /**
     * {@inheritDoc}
     *
     * @param cooldownType {@inheritDoc}
     * @param cooldownId   {@inheritDoc}
     * @param user         {@inheritDoc}
     */
    @Override
    public void applyCooldown(CooldownType cooldownType, Long cooldownId, UserDto user) {
        // Apply cooldown if the user does not have a role that skips it
        if (shouldApplyCooldown(user)) {
            cooldownService.updateCooldownEndingDate(cooldownId, user, cooldownType);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param cooldownType {@inheritDoc}
     * @param user         {@inheritDoc}
     * @return {@inheritDoc}
     * @throws CooldownException {@inheritDoc}
     */
    @Override
    public Optional<CooldownDto> getCooldown(CooldownType cooldownType, UserDto user) throws CooldownException {
        return cooldownService.findCooldownByUserIdAndType(user.getId(), cooldownType, shouldApplyCooldown(user));

    }

    private boolean shouldApplyCooldown(UserDto user) {
        return user.matchesNoRole(commandProperties.getSkipCooldownRoles());
    }
}
