package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl;

import it.vrad.motivational.telegram.bot.core.model.dto.persistence.AuthorDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.BaseTestRepository;
import it.vrad.motivational.telegram.bot.shared.test.util.factory.PersistenceTestFactory;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.DaoTestConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration tests for {@link AuthorDaoImpl}.
 * <p>
 * This class verifies the correct behavior of the AuthorDaoImpl's getOrCreateAuthorId operation.
 * Test data is created using {@link PersistenceTestFactory}.
 */
@Import(DaoTestConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AuthorDaoImplIT extends BaseTestRepository {

    private final AuthorDaoImpl authorDao;

    /**
     * Test cases for creating and finding Author entities.
     */
    @Nested
    class GetOrCreateAuthorId {
        @Test
        @DisplayName("getOrCreateAuthorId should create a new author and return its ID")
        void getOrCreateAuthorId_whenAuthorNotExists_createsAndReturnsId() {
            AuthorDto authorDto = PersistenceTestFactory.createAuthorDtoToSave();

            Long id = authorDao.getOrCreateAuthorId(authorDto);
            assertThat(id).isNotNull();
        }

        @Test
        @DisplayName("getOrCreateAuthorId should return the existing author ID")
        void getOrCreateAuthorId_whenAuthorExists_returnsExistingId() {
            AuthorDto authorDto = PersistenceTestFactory.createGenericAuthorDto();

            Long id = authorDao.getOrCreateAuthorId(authorDto);
            assertThat(id).isNotNull();
        }

        @Test
        @DisplayName("getOrCreateAuthorId should throw NullPointerException if input is null")
        void getOrCreateAuthorId_whenNullInput_throwsNullPointerException() {
            assertThatThrownBy(() -> authorDao.getOrCreateAuthorId(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }
}

