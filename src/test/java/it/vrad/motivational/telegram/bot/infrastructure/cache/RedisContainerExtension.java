package it.vrad.motivational.telegram.bot.infrastructure.cache;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * JUnit Jupiter extension that manages a shared Redis Testcontainer for tests.
 *
 * <p>Behavior:
 * - Starts the Redis container once before all tests that use this extension and stops it after.
 * - Exposes connection information via system properties so tests / Spring can read them:
 *   <ul>
 *     <li>{@code REDIS_HOST} - container host</li>
 *     <li>{@code REDIS_PORT} - mapped host port</li>
 *     <li>{@code REDISSON_ADDRESS} - full address (e.g. {@code redis://host:port})</li>
 *   </ul>
 * <p>
 * Notes:
 * - We intentionally start/stop the container manually here instead of using Testcontainers'
 *   {@code @Container} because the Testcontainers JUnit extension scans the test class
 *   hierarchy for {@code @Container} fields; it does not pick up {@code @Container} fields
 *   declared inside a custom JUnit extension class. Starting the container explicitly
 *   ensures predictable lifecycle when this extension is registered via {@code @ExtendWith}.
 * - The static container field is annotated with {@code @SuppressWarnings("resource")}
 *   to silence IDE/compiler warnings about an unclosed {@code AutoCloseable}. The container
 *   is managed (started/stopped) by this extension.
 * <p>
 * Usage example:
 * {@code @ExtendWith(RedisContainerExtension.class)} on a test class will make the
 * container available and set the system properties before tests run.
 */
public class RedisContainerExtension implements BeforeAllCallback, AfterAllCallback {

    @SuppressWarnings("resource")
    private static final GenericContainer<?> REDIS =
            new GenericContainer<>(DockerImageName.parse("redis:7-alpine"))
                    .withExposedPorts(6379);

    private static boolean started = false;

    @Override
    public synchronized void beforeAll(ExtensionContext context) {
        if (!started) {
            REDIS.start();
            started = true;
        }

        String host = REDIS.getHost();
        Integer port = REDIS.getFirstMappedPort();
        System.setProperty("REDIS_HOST", host);
        System.setProperty("REDIS_PORT", port.toString());
        System.setProperty("REDISSON_ADDRESS", "redis://" + host + ":" + port);
    }

    @Override
    public synchronized void afterAll(ExtensionContext context) {
        if (started) {
            REDIS.stop();
            started = false;
        }
    }
}
