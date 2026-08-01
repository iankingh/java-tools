package com.ian.tools.redis;

import io.github.iankingh.javatools.redis.RedisStringStore;
import java.time.Duration;
import java.util.Collection;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @deprecated Use {@link RedisStringStore}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class RedisStrUtils {
    private final RedisStringStore store;

    public RedisStrUtils(StringRedisTemplate template) {
        this.store = new RedisStringStore(template);
    }

    /**
     * @deprecated Use {@link RedisStringStore#delete(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public void delete(String key) {
        store.delete(key);
    }

    /**
     * @deprecated Use {@link RedisStringStore#deleteAll(Collection)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public void delete(Collection<String> keys) {
        store.deleteAll(keys);
    }

    /**
     * @deprecated Use {@link RedisStringStore#exists(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public Boolean hasKey(String key) {
        return store.exists(key);
    }

    /**
     * @deprecated Use {@link RedisStringStore#expire(String, Duration)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public boolean expire(String key, long seconds) {
        return store.expire(key, Duration.ofSeconds(seconds));
    }

    /**
     * @deprecated Use {@link RedisStringStore#remainingTtl(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public Long getExpire(String key) {
        return store.remainingTtl(key).map(Duration::toSeconds).orElse(-1L);
    }
}
