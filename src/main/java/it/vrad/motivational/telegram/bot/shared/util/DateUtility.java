package it.vrad.motivational.telegram.bot.shared.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Utility class for date and time operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtility {

    public static final DateTimeFormatter COOLDOWN_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "EEEE, d MMMM yyyy, HH:mm O", Locale.ENGLISH);

    /**
     * Gets the current OffsetDateTime.
     *
     * @return current OffsetDateTime
     */
    public static OffsetDateTime now() {
        return OffsetDateTime.now();
    }

    /**
     * Gets the current OffsetDateTime at a specific ZoneOffset.
     *
     * @param zoneOffset the zone offset
     * @return OffsetDateTime at zone
     */
    public static OffsetDateTime nowAtZone(ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset);

        return OffsetDateTime.now(zoneOffset);
    }

    /**
     * Gets the current OffsetDateTime at a specific ZoneId.
     *
     * @param zoneId the zone ID
     * @return OffsetDateTime at zone
     */
    public static OffsetDateTime nowAtZone(ZoneId zoneId) {
        Objects.requireNonNull(zoneId);

        return OffsetDateTime.now(zoneId);
    }

    /**
     * Checks if firstDate is before or equal to secondDate.
     *
     * @param firstDate  first date
     * @param secondDate second date
     * @return true if firstDate {@literal <=} secondDate
     */
    public static boolean isBeforeOrEqual(OffsetDateTime firstDate, OffsetDateTime secondDate) {
        Objects.requireNonNull(firstDate);
        Objects.requireNonNull(secondDate);

        return !firstDate.isAfter(secondDate);
    }

    /**
     * Creates a daily cron expression for a given LocalTime.
     *
     * @param localTime the local time
     * @return cron expression string
     */
    public static String createDailyCronExpression(LocalTime localTime) {
        Objects.requireNonNull(localTime);

        return String.format("%d %d %d * * ?", localTime.getSecond(), localTime.getMinute(), localTime.getHour());
    }
}
