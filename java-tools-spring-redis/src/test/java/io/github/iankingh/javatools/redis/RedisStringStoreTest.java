package io.github.iankingh.javatools.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.Expiration;

class RedisStringStoreTest {
    private StringRedisTemplate template;
    private ValueOperations<String, String> values;
    private RedisStringStore store;

    @BeforeEach
    void setUp() {
        template = mock(StringRedisTemplate.class);
        @SuppressWarnings("unchecked")
        ValueOperations<String, String> mockedValues = mock(ValueOperations.class);
        values = mockedValues;
        when(template.opsForValue()).thenReturn(values);
        store = new RedisStringStore(template);
    }

    @Test
    void writesReadsAndDeletesValues() {
        when(values.get("key")).thenReturn("value");
        when(template.hasKey("key")).thenReturn(true);
        when(template.delete("key")).thenReturn(true);

        store.set("key", "value");

        assertEquals(Optional.of("value"), store.get("key"));
        assertTrue(store.exists("key"));
        assertTrue(store.delete("key"));
        verify(values).set("key", "value", Expiration.keepTtl());
    }

    @Test
    void supportsExpiryAndMissingValues() {
        Duration expiry = Duration.ofSeconds(5);
        when(values.get("missing")).thenReturn(null);
        when(template.expire("key", expiry)).thenReturn(true);
        when(template.getExpire("key", TimeUnit.MILLISECONDS)).thenReturn(1500L);
        when(template.getExpire("permanent", TimeUnit.MILLISECONDS)).thenReturn(-1L);

        store.set("key", "value", expiry);

        verify(values).set("key", "value", expiry);
        assertTrue(store.expire("key", expiry));
        assertEquals(Optional.of(Duration.ofMillis(1500)), store.remainingTtl("key"));
        assertEquals(Optional.empty(), store.remainingTtl("permanent"));
        assertEquals(Optional.empty(), store.get("missing"));
    }

    @Test
    void roundsSubMillisecondExpiriesUp() {
        store.set("key", "value", Duration.ofNanos(1));
        when(template.expire("key", Duration.ofMillis(1))).thenReturn(true);

        assertTrue(store.expire("key", Duration.ofNanos(1)));
        verify(values).set("key", "value", Duration.ofMillis(1));
    }

    @Test
    void deletesCollectionsAndHandlesDriverNulls() {
        when(template.delete(List.of("one", "two"))).thenReturn(2L);
        when(template.delete(List.of("unknown"))).thenReturn(null);

        assertEquals(0, store.deleteAll(List.of()));
        assertEquals(2, store.deleteAll(List.of("one", "two")));
        assertEquals(0, store.deleteAll(List.of("unknown")));
        when(template.delete("missing")).thenReturn(null);
        assertFalse(store.delete("missing"));
    }

    @Test
    void validatesKeysValuesAndDurations() {
        assertThrows(NullPointerException.class, () -> new RedisStringStore(null));
        assertThrows(IllegalArgumentException.class, () -> store.get(" "));
        assertThrows(NullPointerException.class, () -> store.set("key", null));
        assertThrows(IllegalArgumentException.class, () -> store.expire("key", Duration.ZERO));
        assertThrows(
                IllegalArgumentException.class,
                () -> store.set("key", "value", Duration.ofSeconds(-1)));
        assertThrows(NullPointerException.class, () -> store.deleteAll(null));
        assertThrows(IllegalArgumentException.class, () -> store.deleteAll(List.of("")));
    }
}
