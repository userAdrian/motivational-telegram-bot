package it.vrad.motivational.telegram.bot;

import it.vrad.motivational.telegram.bot.infrastructure.cache.EnableRedisContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableRedisContainer
class MotivationalTelegramBotApplicationTest {

    @Test
    void contextLoads() {
    }

}
