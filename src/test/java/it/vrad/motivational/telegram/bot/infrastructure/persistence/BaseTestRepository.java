package it.vrad.motivational.telegram.bot.infrastructure.persistence;

import it.vrad.motivational.telegram.bot.infrastructure.persistence.constants.PersistenceTestConstants;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(basePackages = "it.vrad.motivational.telegram.bot.infrastructure.persistence.entity")
@EnableJpaRepositories(basePackages = "it.vrad.motivational.telegram.bot.infrastructure.persistence.repository")
public abstract class BaseTestRepository {

    protected static <T> void assertRecursiveEqualsIgnoringId(T actual, T expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(PersistenceTestConstants.ID_FIELD_NAME_REGEX)
                .isEqualTo(expected);
    }
}
