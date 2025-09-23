package it.vrad.motivational.telegram.bot.infrastructure.persistence.dao;

import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl.AuthorDaoImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl.CooldownDaoImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl.PhraseDaoImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl.PhraseSentHistoryDaoImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl.TelegramFileDaoImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl.UserDaoImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.impl.UserPhraseDaoImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.AuthorToAuthorDtoMapperImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.CooldownToCooldownDtoMapperImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.PhraseSentHistoryToPhraseSentHistoryDtoMapperImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.PhraseToPhraseDtoMapperImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.TelegramFileToTelegramFileDtoMapperImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.UserPhraseToUserPhraseDtoMapperImpl;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.mapper.UserToUserDtoMapperImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Test configuration for DAO layer integration test.
 * <p>
 * Imports all necessary DAO implementations and mappers for testing.
 * Using this configuration as a context in your DAO integration test
 * ensures a shared Spring context and avoids unnecessary database re-creation
 */
@Import({
        DaoTestConfig.UserDaoImplTestConfig.class,
        DaoTestConfig.TelegramFileDaoImplTestConfig.class,
        DaoTestConfig.PhraseSentHistoryDaoImplTestConfig.class,
        DaoTestConfig.AuthorDaoImplTestConfig.class,
        DaoTestConfig.PhraseDaoImplTestConfig.class,
        DaoTestConfig.CooldownDaoImplTestConfig.class,
        DaoTestConfig.UserPhraseDaoImplTestConfig.class
})
public class DaoTestConfig {

    /**
     * Test configuration for UserDaoImpl integration test.
     * <p>
     * Import only the beans needed to test UserDaoImpl.
     */
    @Configuration
    @Import({
            UserDaoImpl.class,
            UserToUserDtoMapperImpl.class,
            UserPhraseToUserPhraseDtoMapperImpl.class
    })
    public static class UserDaoImplTestConfig {
    }

    /**
     * Test configuration for TelegramFileDaoImpl integration test.
     * <p>
     * Import only the beans needed to test TelegramFileDaoImpl.
     */
    @Configuration
    @Import({
            TelegramFileDaoImpl.class,
            TelegramFileToTelegramFileDtoMapperImpl.class
    })
    public static class TelegramFileDaoImplTestConfig {
    }

    /**
     * Test configuration for PhraseSentHistoryDaoImpl integration test.
     * <p>
     * Import only the beans needed to test PhraseSentHistoryDaoImpl.
     */
    @Configuration
    @Import({
            PhraseSentHistoryDaoImpl.class,
            PhraseSentHistoryToPhraseSentHistoryDtoMapperImpl.class
    })
    public static class PhraseSentHistoryDaoImplTestConfig {
    }

    /**
     * Test configuration for AuthorDaoImpl integration test.
     * <p>
     * Import only the beans needed to test AuthorDaoImpl.
     */
    @Configuration
    @Import({
            AuthorDaoImpl.class,
            AuthorToAuthorDtoMapperImpl.class,
            PhraseToPhraseDtoMapperImpl.class
    })
    public static class AuthorDaoImplTestConfig {
    }

    /**
     * Test configuration for PhraseDaoImpl integration test.
     * <p>
     * Import only the beans needed to test PhraseDaoImpl.
     */
    @Configuration
    @Import({
            PhraseDaoImpl.class,
            PhraseToPhraseDtoMapperImpl.class,
            UserPhraseToUserPhraseDtoMapperImpl.class
    })
    public static class PhraseDaoImplTestConfig {
    }

    /**
     * Test configuration for CooldownDaoImpl integration test.
     * <p>
     * Import only the beans needed to test CooldownDaoImpl.
     */
    @Configuration
    @Import({
            CooldownDaoImpl.class,
            CooldownToCooldownDtoMapperImpl.class
    })
    public static class CooldownDaoImplTestConfig {
    }

    /**
     * Test configuration for UserPhraseDaoImpl integration test.
     * <p>
     * Import only the beans needed to test UserPhraseDaoImpl.
     */
    @Configuration
    @Import({
            UserPhraseDaoImpl.class,
            UserPhraseToUserPhraseDtoMapperImpl.class
    })
    public static class UserPhraseDaoImplTestConfig {
    }
}
