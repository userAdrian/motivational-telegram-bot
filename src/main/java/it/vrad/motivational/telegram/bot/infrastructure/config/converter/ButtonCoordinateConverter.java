package it.vrad.motivational.telegram.bot.infrastructure.config.converter;

import it.vrad.motivational.telegram.bot.core.model.ButtonCoordinates;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;

/**
 * Converter for transforming a string representation of button coordinates into a {@link ButtonCoordinates} object.
 * The string must contain two integer values separated by a configured split character (e.g., "1,2").
 */
@Slf4j
public class ButtonCoordinateConverter implements Converter<String, ButtonCoordinates> {

    private static final String ERR_NULL_OR_BLANK = "Button coordinate string is blank";
    private static final String ERR_INVALID_SPLIT = "Button coordinate string '%s' does not contain exactly two values separated by '%s'";
    private static final String ERR_INVALID_INTEGER = "Button coordinate values must be integers: '%s'";

    @Value("${motivational.telegram.bot.configuration.page.button-coordinate-split-character}")
    @NotBlank
    private String buttonCoordinateSplitCharacter;

    /**
     * Converts a string into a {@link ButtonCoordinates} object.
     * The string must be in the format "row{split}col", where {split} is the configured split character.
     *
     * @param source the string to convert
     * @return the parsed {@link ButtonCoordinates}
     * @throws IllegalArgumentException if the input is blank, not properly split, or not integers
     */
    @Override
    public ButtonCoordinates convert(String source) {
        // Check for blank input
        if (source.isBlank()) {
            logAndThrow(ERR_NULL_OR_BLANK);
        }
        // Split the input string by the configured split character
        String[] array = source.split(buttonCoordinateSplitCharacter);
        if (array.length != 2) {
            logAndThrow(String.format(ERR_INVALID_SPLIT, source, buttonCoordinateSplitCharacter));
        }

        try {
            // Parse row and column as integers
            int row = Integer.parseInt(array[0]);
            int col = Integer.parseInt(array[1]);
            return new ButtonCoordinates(row, col);
        } catch (NumberFormatException e) {
            String errorMessage = String.format(ERR_INVALID_INTEGER, source);

            log.error(errorMessage, e);
            throw new IllegalArgumentException(errorMessage, e);
        }
    }

    /**
     * Logs the error message and throws an {@link IllegalArgumentException}.
     *
     * @param message the error message
     */
    private void logAndThrow(String message) {
        log.error(message);
        throw new IllegalArgumentException(message);
    }

}
