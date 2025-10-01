package it.vrad.motivational.telegram.bot.shared.test.util;

import it.vrad.motivational.telegram.bot.shared.test.constants.PersistenceTestConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Utility assertions used in unit tests.
 * <p>
 * Provides AssertJ-based helpers.
 * <p>
 * The class is non-instantiable and intended solely for use from test code.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestAssertions {

    /**
     * Assert that two objects are equal using a recursive field-by-field comparison,
     * ignoring identifier fields (fields matching {@link PersistenceTestConstants#ID_FIELD_NAME_REGEX}).
     * <p>
     * This is useful in tests where identifier values (e.g. database-generated ids)
     * are not relevant to the equality of domain objects.
     *
     * @param actual   the actual object produced by the code under test
     * @param expected the expected object to compare against
     * @param <T>      the type of the objects being compared
     */
    public static <T> void assertRecursiveEqualsIgnoringId(T actual, T expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(PersistenceTestConstants.ID_FIELD_NAME_REGEX)
                .isEqualTo(expected);
    }

    /**
     * Assert that two objects are equal using a full recursive field-by-field comparison.
     * <p>
     * This performs a deep comparison of all fields and is appropriate when identifiers
     * and all nested state must match exactly.
     *
     * @param actual   the actual object produced by the code under test
     * @param expected the expected object to compare against
     * @param <T>      the type of the objects being compared
     */
    public static <T> void assertRecursiveEquals(T actual, T expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
