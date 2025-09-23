package it.vrad.motivational.telegram.bot.core.service.date.impl;

import it.vrad.motivational.telegram.bot.config.properties.PhraseProperties;
import it.vrad.motivational.telegram.bot.core.service.date.DateService;
import it.vrad.motivational.telegram.bot.shared.util.DateUtility;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Implementation of {@link DateService} that provides useful methods for dates.
 */
@Service
public class DateServiceImpl implements DateService {

    private final ZoneId timeZone;

    public DateServiceImpl(PhraseProperties phraseProperties) {
        this.timeZone = phraseProperties.getTimeZone();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public OffsetDateTime getCurrentOffsetDateTime() {
        // Return the current date and time in the configured time zone
        return DateUtility.nowAtZone(timeZone);
    }

    /**
     * {@inheritDoc}
     *
     * @param dateTime {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public OffsetDateTime convertToDefaultTimeZone(OffsetDateTime dateTime) {
        Objects.requireNonNull(dateTime);

        return dateTime.atZoneSameInstant(timeZone).toOffsetDateTime();
    }
}
