package io.github.iankingh.javatools.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

class RedisStringStoreIntegrationTest {
    private static GenericContainer<?> redis;
    private static LettuceConnectionFactory connectionFactory;
    private static RedisStringStore store;
    private final String key = "java-tools:integration:" + UUID.randomUUID();

    @BeforeAll
    static void connect() {
        String host = System.getProperty("redis.integration.host");
        int port;
        if (host == null) {
            redis =
                    new GenericContainer<>(DockerImageName.parse("redis:7.4-alpine"))
                            .withExposedPorts(6379);
            redis.start();
            host = redis.getHost();
            port = redis.getMappedPort(6379);
        } else {
            if (host.isBlank()) {
                throw new IllegalArgumentException("redis.integration.host must not be blank");
            }
            port = Integer.parseInt(System.getProperty("redis.integration.port", "6379"));
        }
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setDatabase(
                Integer.parseInt(System.getProperty("redis.integration.database", "0")));
        connectionFactory = new LettuceConnectionFactory(configuration);
        connectionFactory.afterPropertiesSet();
        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);
        template.afterPropertiesSet();
        store = new RedisStringStore(template);
        try (var connection = connectionFactory.getConnection()) {
            assertEquals("PONG", connection.ping());
        }
    }

    @AfterAll
    static void disconnect() {
        if (connectionFactory != null) {
            connectionFactory.destroy();
        }
        if (redis != null) {
            redis.stop();
        }
    }

    @AfterEach
    void removeOwnedKeys() {
        store.deleteAll(List.of(key, key + ":other"));
    }

    @Test
    void roundTripsValuesAgainstRedis() {
        assertEquals(Optional.empty(), store.get(key));
        assertFalse(store.exists(key));
        store.set(key, "值 café");
        assertEquals("值 café", store.get(key).orElseThrow());
        assertTrue(store.exists(key));
        assertEquals(Optional.empty(), store.remainingTtl(key));
        assertTrue(store.delete(key));
        assertFalse(store.delete(key));
        assertEquals(Optional.empty(), store.get(key));
    }

    @Test
    void preservesExpiryOnOverwriteAndDeletesCollections() {
        Duration expiry = Duration.ofMinutes(2);
        store.set(key, "value", expiry);
        assertPositiveTtlAtMost(expiry);
        store.set(key, "updated");
        assertEquals("updated", store.get(key).orElseThrow());
        assertPositiveTtlAtMost(expiry);
        assertTrue(store.expire(key, Duration.ofMinutes(1)));
        assertPositiveTtlAtMost(Duration.ofMinutes(1));
        store.set(key + ":other", "other");
        assertEquals(2, store.deleteAll(List.of(key, key + ":other")));
        assertEquals(0, store.deleteAll(List.of(key, key + ":other")));
        assertFalse(store.expire(key, expiry));
        assertEquals(Optional.empty(), store.remainingTtl(key));
    }

    @Test
    void expiresValuesWithinABoundedDeadline() throws InterruptedException {
        store.set(key, "short-lived", Duration.ofMillis(100));
        long deadline = System.nanoTime() + Duration.ofSeconds(5).toNanos();
        while (store.exists(key) && System.nanoTime() < deadline) {
            Thread.sleep(10);
        }
        assertFalse(store.exists(key), "Redis did not expire the key within five seconds");
        assertEquals(Optional.empty(), store.get(key));
    }

    private void assertPositiveTtlAtMost(Duration maximum) {
        Duration ttl = store.remainingTtl(key).orElseThrow();
        assertTrue(ttl.isPositive());
        assertTrue(ttl.compareTo(maximum) <= 0);
    }
}
