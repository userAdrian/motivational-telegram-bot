package it.vrad.motivational.telegram.bot.core.service.date;

import java.time.OffsetDateTime;

/**
 * Service for dates.
 */
public interface DateService {

    /**
     * Returns the current date and time in the configured time zone.
     *
     * @return the current {@link OffsetDateTime} in the configured time zone via the <br>
     * {@code motivational.telegram.bot.configuration.phrase.time-zone} property.
     */
    OffsetDateTime getCurrentOffsetDateTime();

    /**
     * Converts the given {@link OffsetDateTime} to the configured time zone.
     *
     * @param dateTime the date and time to convert
     * @return the {@link OffsetDateTime} at the configured time zone via the <br>
     * {@code motivational.telegram.bot.configuration.phrase.time-zone} property.
     */
    OffsetDateTime convertToDefaultTimeZone(OffsetDateTime dateTime);
}
