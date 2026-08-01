package io.github.iankingh.javatools.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class RedisStringStoreIntegrationTest {
    @Container
    private static final GenericContainer<?> REDIS =
            new GenericContainer<>(DockerImageName.parse("redis:7.4-alpine"))
                    .withExposedPorts(6379);

    private static LettuceConnectionFactory connectionFactory;
    private static RedisStringStore store;

    @BeforeAll
    static void connect() {
        connectionFactory =
                new LettuceConnectionFactory(REDIS.getHost(), REDIS.getMappedPort(6379));
        connectionFactory.afterPropertiesSet();
        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);
        template.afterPropertiesSet();
        store = new RedisStringStore(template);
    }

    @AfterAll
    static void disconnect() {
        connectionFactory.destroy();
    }

    @Test
    void roundTripsValuesAgainstRedis() {
        store.set("integration:key", "value", Duration.ofSeconds(30));

        assertEquals("value", store.get("integration:key").orElseThrow());
    }
}
