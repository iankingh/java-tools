package com.ian.tools.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

@SuppressWarnings("removal")
class RedisUtilsTest {
    private RedisTemplate<String, Object> template;
    private ValueOperations<String, Object> values;
    private ListOperations<String, Object> lists;
    private HashOperations<String, Object, Object> hashes;
    private SetOperations<String, Object> sets;
    private RedisUtils redis;

    @BeforeEach
    void setUp() {
        @SuppressWarnings("unchecked")
        RedisTemplate<String, Object> mockedTemplate = mock(RedisTemplate.class);
        @SuppressWarnings("unchecked")
        ValueOperations<String, Object> mockedValues = mock(ValueOperations.class);
        @SuppressWarnings("unchecked")
        ListOperations<String, Object> mockedLists = mock(ListOperations.class);
        @SuppressWarnings("unchecked")
        HashOperations<String, Object, Object> mockedHashes = mock(HashOperations.class);
        @SuppressWarnings("unchecked")
        SetOperations<String, Object> mockedSets = mock(SetOperations.class);
        template = mockedTemplate;
        values = mockedValues;
        lists = mockedLists;
        hashes = mockedHashes;
        sets = mockedSets;
        when(template.opsForValue()).thenReturn(values);
        when(template.opsForList()).thenReturn(lists);
        when(template.opsForHash()).thenReturn(hashes);
        when(template.opsForSet()).thenReturn(sets);
        redis = new RedisUtils(template);
    }

    @Test
    void preservesCollectionShapes() {
        when(lists.range("list", 0, -1)).thenReturn(List.of("one", "two"));
        when(hashes.entries("map")).thenReturn(Map.of("key", "value"));
        when(sets.members("set")).thenReturn(Set.of("one", "two"));

        redis.setList("list", List.of("one", "two"));
        redis.setMap("map", Map.of("key", "value"));
        redis.setSet("set", Set.of("one", "two"));

        verify(lists).rightPushAll("list", new Object[] {"one", "two"});
        verify(template).delete("map");
        verify(hashes).putAll("map", Map.of("key", "value"));
        assertEquals(List.of("one", "two"), redis.getList("list"));
        assertEquals(Map.of("key", "value"), redis.getMap("map"));
        assertEquals(Set.of("one", "two"), redis.getSet("set"));
    }

    @Test
    void validatesExpiryAndBuildsStableKeys() {
        redis.set("key", "value", Duration.ofSeconds(1));
        verify(values).set("key", "value", Duration.ofSeconds(1));
        String generatedKey = RedisUtils.getKey("table", "id", "1");
        assertEquals("table:id:1", generatedKey);
        assertEquals("table:id:1:name", RedisUtils.getKeyWithColumn("table", "id", "1", "name"));
        redis.set(generatedKey, "value");
        verify(values).set(generatedKey, "value");
        redis.set("short-expiry", "value", Duration.ofNanos(1));
        verify(values).set("short-expiry", "value", Duration.ofMillis(1));
        assertThrows(
                IllegalArgumentException.class, () -> RedisUtils.getKey("table:name", "id", "1"));
        assertThrows(
                IllegalArgumentException.class, () -> redis.set("key", "value", Duration.ZERO));
    }
}
