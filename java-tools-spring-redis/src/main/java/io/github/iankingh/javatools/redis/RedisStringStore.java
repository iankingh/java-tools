package io.github.iankingh.javatools.redis;

import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

/** Constructor-injected string operations with explicit null and duration semantics. */
public final class RedisStringStore {
    private static final Duration MINIMUM_EXPIRY = Duration.ofMillis(1);

    private final StringRedisTemplate template;

    /** Creates a store backed by the supplied Spring template. */
    public RedisStringStore(StringRedisTemplate template) {
        this.template = Objects.requireNonNull(template, "template");
    }

    /** Deletes a key and returns whether it existed. */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(template.delete(requireKey(key)));
    }

    /** Deletes all supplied keys and returns the number removed. */
    public long deleteAll(Collection<String> keys) {
        Objects.requireNonNull(keys, "keys");
        if (keys.isEmpty()) {
            return 0;
        }
        keys.forEach(RedisStringStore::requireKey);
        Long deleted = template.delete(keys);
        return deleted == null ? 0 : deleted;
    }

    /** Returns whether a key exists. */
    public boolean exists(String key) {
        return Boolean.TRUE.equals(template.hasKey(requireKey(key)));
    }

    /** Stores a value while preserving an existing expiry. */
    public void set(String key, String value) {
        template.opsForValue()
                .set(requireKey(key), Objects.requireNonNull(value, "value"), Expiration.keepTtl());
    }

    /** Stores a value with a positive expiry. */
    public void set(String key, String value, Duration expiry) {
        template.opsForValue()
                .set(
                        requireKey(key),
                        Objects.requireNonNull(value, "value"),
                        requireRedisExpiry(expiry));
    }

    /** Reads a value without converting a missing value into the text {@code "null"}. */
    public Optional<String> get(String key) {
        return Optional.ofNullable(template.opsForValue().get(requireKey(key)));
    }

    /** Applies a positive expiry and returns whether the key existed. */
    public boolean expire(String key, Duration expiry) {
        return Boolean.TRUE.equals(template.expire(requireKey(key), requireRedisExpiry(expiry)));
    }

    /** Returns a positive remaining expiry. Empty means the key is missing or has no expiry. */
    public Optional<Duration> remainingTtl(String key) {
        Long milliseconds = template.getExpire(requireKey(key), TimeUnit.MILLISECONDS);
        return milliseconds != null && milliseconds >= 0
                ? Optional.of(Duration.ofMillis(milliseconds))
                : Optional.empty();
    }

    private static String requireKey(String key) {
        Objects.requireNonNull(key, "key");
        if (key.isBlank()) {
            throw new IllegalArgumentException("key must not be blank");
        }
        return key;
    }

    private static Duration requireRedisExpiry(Duration duration) {
        Objects.requireNonNull(duration, "expiry");
        if (duration.isZero() || duration.isNegative()) {
            throw new IllegalArgumentException("expiry must be positive");
        }
        return duration.compareTo(MINIMUM_EXPIRY) < 0 ? MINIMUM_EXPIRY : duration;
    }
}
