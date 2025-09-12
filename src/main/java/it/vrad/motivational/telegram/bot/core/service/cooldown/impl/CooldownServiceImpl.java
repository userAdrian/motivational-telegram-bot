package it.vrad.motivational.telegram.bot.core.service.cooldown.impl;

import it.vrad.motivational.telegram.bot.config.properties.CooldownProperties;
import it.vrad.motivational.telegram.bot.core.model.enums.persistence.CooldownType;
import it.vrad.motivational.telegram.bot.core.service.date.DateService;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionLogMessageHelper;
import it.vrad.motivational.telegram.bot.core.exception.CooldownException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.CooldownDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.CooldownDao;
import it.vrad.motivational.telegram.bot.core.service.cooldown.CooldownService;
import it.vrad.motivational.telegram.bot.infrastructure.util.DateUtility;
import it.vrad.motivational.telegram.bot.core.model.factory.PersistenceDtoFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * Implementation of {@link CooldownService} for managing user cooldowns.
 */
@RequiredArgsConstructor
@Service
public class CooldownServiceImpl implements CooldownService {
    private final CooldownDao cooldownDao;
    private final CooldownProperties cooldownProperties;
    private final DateService dateService;

    /**
     * {@inheritDoc}
     *
     * @param userId        {@inheritDoc}
     * @param type          {@inheritDoc}
     * @param checkIfActive {@inheritDoc}
     * @throws CooldownException {@inheritDoc}
     */
    @Override
    public Optional<CooldownDto> findCooldownByUserIdAndType(Long userId, CooldownType type, boolean checkIfActive) throws CooldownException {
        // Retrieve cooldown for user and type
        Optional<CooldownDto> cooldownOpt = cooldownDao.findByUserIdAndType(userId, type);

        if (cooldownOpt.isPresent()) {
            CooldownDto cooldown = cooldownOpt.get();
            OffsetDateTime endingTime = cooldown.getEndingTime();
            // If requested, check if cooldown is currently active
            if (checkIfActive && isCooldownActive(endingTime)) {
                throw new CooldownException(type, dateService.convertToDefaultTimeZone(endingTime));
            }
        }

        return cooldownOpt;
    }

    /**
     * Checks if the cooldown is still active based on the ending time.
     *
     * @param endingTime the time when the cooldown ends
     * @return true if the cooldown is active, false otherwise
     */
    private static boolean isCooldownActive(OffsetDateTime endingTime) {
        // Get current time in the same zone as endingTime
        OffsetDateTime now = DateUtility.nowAtZone(endingTime.getOffset());
        // Return true if now is before or equal to endingTime
        return DateUtility.isBeforeOrEqual(now, endingTime);
    }

    /**
     * {@inheritDoc}
     *
     * @param id           {@inheritDoc}
     * @param userDto      {@inheritDoc}
     * @param type {@inheritDoc}
     * @return {@inheritDoc}
     * @throws jakarta.persistence.EntityNotFoundException {@inheritDoc}
     */
    @Override
    public CooldownDto updateCooldownEndingDate(Long id, UserDto userDto, CooldownType type) {
        if (id == null) {
            return createNewCooldown(userDto, type);
        }
        return updateExistingCooldownEndingTime(id, type);
    }

    private CooldownDto createNewCooldown(UserDto userDto, CooldownType type) {
        CooldownDto cooldownDto = PersistenceDtoFactory.buildCooldownDto(
                userDto, type, getEndingTime(type)
        );
        return cooldownDao.save(cooldownDto);
    }

    private CooldownDto updateExistingCooldownEndingTime(Long id, CooldownType type) {
        CooldownDto cooldownDto = CooldownDto.builder()
                .endingTime(getEndingTime(type))
                .build();
        return cooldownDao.updateCooldown(id, cooldownDto)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionLogMessageHelper.getCooldownNotFound(id)));
    }

    /**
     * Calculates the ending time for a cooldown based on its type and configured duration.
     *
     * @param type the type of cooldown
     * @return the calculated ending time
     */
    private OffsetDateTime getEndingTime(CooldownType type) {
        return dateService.getCurrentOffsetDateTime().plus(cooldownProperties.getCooldownDurationByType(type));
    }
}
