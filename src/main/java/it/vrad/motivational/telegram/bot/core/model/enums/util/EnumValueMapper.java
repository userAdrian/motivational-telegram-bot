package it.vrad.motivational.telegram.bot.core.model.enums.util;

import it.vrad.motivational.telegram.bot.core.model.enums.TelegramValued;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Generic utility to map a String value received from Telegram to a corresponding enum
 * constant that implements {@link TelegramValued}.
 * <p>
 * Usage example:
 * ChatType type = EnumValueMapper.fromTelegramValue(ChatType.class, "private");
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EnumValueMapper {

    /**
     * Map the provided Telegram string value to the matching enum constant of the given enum type.
     * The enum type must implement {@link TelegramValued} so the mapper can read the
     * telegram value for each constant.
     *
     * @param enumType the enum class to search
     * @param value    the Telegram value to match (case-insensitive)
     * @param <E>      enum type that implements {@link TelegramValued}
     * @return the matching enum constant
     * @throws IllegalArgumentException if no matching constant is found or if enumType/value are null
     */
    public static <E extends Enum<E> & TelegramValued> E fromTelegramValue(Class<E> enumType, String value) {
        Objects.requireNonNull(enumType, "enumType must not be null");
        Objects.requireNonNull(value, "value must not be null");

        for (E constant : enumType.getEnumConstants()) {
            if (constant.getTelegramValue().equalsIgnoreCase(value)) {
                return constant;
            }
        }

        throw new IllegalArgumentException("No " + enumType.getSimpleName() + " enum found for value: " + value);
    }
}

