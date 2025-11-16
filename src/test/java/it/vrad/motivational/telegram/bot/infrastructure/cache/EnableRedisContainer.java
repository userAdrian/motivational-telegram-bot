package it.vrad.motivational.telegram.bot.infrastructure.cache;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@ExtendWith(RedisContainerExtension.class)
public @interface EnableRedisContainer {
}
