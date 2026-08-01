package com.ian.tools.redis;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Migration wrapper for object-based Redis access.
 *
 * @deprecated Prefer a typed repository or {@link
 *     io.github.iankingh.javatools.redis.RedisStringStore}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class RedisUtils {
    private static final Duration MINIMUM_EXPIRY = Duration.ofMillis(1);

    private final RedisTemplate<String, Object> template;

    /** Creates the migration wrapper with constructor injection. */
    public RedisUtils(RedisTemplate<String, Object> template) {
        this.template = Objects.requireNonNull(template, "template");
    }

    /** Deletes one key and reports whether it existed. */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(template.delete(requireKey(key)));
    }

    /** Deletes a collection of keys and returns the number removed. */
    public long delete(Collection<String> keys) {
        Long deleted = template.delete(Objects.requireNonNull(keys, "keys"));
        return deleted == null ? 0 : deleted;
    }

    /** Reports whether a key exists. */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(template.hasKey(requireKey(key)));
    }

    /** Stores a non-null object. */
    public void set(String key, Object value) {
        template.opsForValue().set(requireKey(key), Objects.requireNonNull(value, "value"));
    }

    /** Stores a non-null object with a positive expiry. */
    public void set(String key, Object value, Duration expiry) {
        Objects.requireNonNull(expiry, "expiry");
        if (expiry.isZero() || expiry.isNegative()) {
            throw new IllegalArgumentException("expiry must be positive");
        }
        Duration redisExpiry = expiry.compareTo(MINIMUM_EXPIRY) < 0 ? MINIMUM_EXPIRY : expiry;
        template.opsForValue()
                .set(requireKey(key), Objects.requireNonNull(value, "value"), redisExpiry);
    }

    /** Reads an object, returning null only when Redis has no value. */
    public Object get(String key) {
        return template.opsForValue().get(requireKey(key));
    }

    /** Replaces a Redis list with the supplied elements. */
    public void setList(String key, List<?> values) {
        requireKey(key);
        Objects.requireNonNull(values, "values");
        template.delete(key);
        if (!values.isEmpty()) {
            template.opsForList().rightPushAll(key, values.toArray());
        }
    }

    /** Reads every element from a Redis list. */
    public List<Object> getList(String key) {
        List<Object> values = template.opsForList().range(requireKey(key), 0, -1);
        return values == null ? List.of() : List.copyOf(values);
    }

    /** Replaces all entries in a Redis hash. */
    public void setMap(String key, Map<?, ?> values) {
        requireKey(key);
        Objects.requireNonNull(values, "values");
        template.delete(key);
        if (!values.isEmpty()) {
            template.opsForHash().putAll(key, values);
        }
    }

    /** Reads every entry from a Redis hash. */
    public Map<Object, Object> getMap(String key) {
        return Map.copyOf(template.opsForHash().entries(requireKey(key)));
    }

    /** Replaces a Redis set with the supplied elements. */
    public void setSet(String key, Set<?> values) {
        requireKey(key);
        Objects.requireNonNull(values, "values");
        template.delete(key);
        if (!values.isEmpty()) {
            template.opsForSet().add(key, values.toArray());
        }
    }

    /** Reads every member from a Redis set. */
    public Set<Object> getSet(String key) {
        Set<Object> values = template.opsForSet().members(requireKey(key));
        return values == null ? Set.of() : Set.copyOf(values);
    }

    /** Builds a colon-delimited key with a column suffix. */
    public static String getKeyWithColumn(
            String tableName, String majorKey, String majorKeyValue, String column) {
        return String.join(
                ":",
                requirePart(tableName, "tableName"),
                requirePart(majorKey, "majorKey"),
                requirePart(majorKeyValue, "majorKeyValue"),
                requirePart(column, "column"));
    }

    /** Builds a colon-delimited key. */
    public static String getKey(String tableName, String majorKey, String majorKeyValue) {
        return String.join(
                ":",
                requirePart(tableName, "tableName"),
                requirePart(majorKey, "majorKey"),
                requirePart(majorKeyValue, "majorKeyValue"));
    }

    private static String requireKey(String key) {
        Objects.requireNonNull(key, "key");
        if (key.isBlank()) {
            throw new IllegalArgumentException("key must not be blank");
        }
        return key;
    }

    private static String requirePart(String value, String name) {
        requireKey(value);
        if (value.contains(":")) {
            throw new IllegalArgumentException(
                    name + " must be non-blank and must not contain ':'");
        }
        return value;
    }
}
