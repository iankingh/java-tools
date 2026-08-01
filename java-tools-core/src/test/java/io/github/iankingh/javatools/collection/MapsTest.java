package io.github.iankingh.javatools.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class MapsTest {
    @Test
    void removesNullKeysAndAllSupportedEmptyValues() {
        Map<Object, Object> values = new LinkedHashMap<>();
        values.put(null, "null-key");
        values.put("null", null);
        values.put("text", "");
        values.put("list", List.of());
        values.put("map", Map.of());
        values.put("optional", Optional.empty());
        values.put("array", new String[0]);
        values.put("kept", "value");

        Maps.removeEmptyEntries(values);

        assertEquals(Map.of("kept", "value"), values);
    }

    @Test
    void removesKeysAndValuesIndependently() {
        Map<Object, Object> values = new LinkedHashMap<>();
        values.put(null, "kept");
        values.put("empty", new ArrayList<>());
        values.put("kept", "value");

        Maps.removeNullKeys(values);
        assertFalse(values.containsKey(null));
        Maps.removeEmptyValues(values);

        assertEquals(Map.of("kept", "value"), values);
    }

    @Test
    void identifiesEmptyAndNonEmptyValues() {
        assertTrue(Maps.isEmptyValue(null));
        assertTrue(Maps.isEmptyValue(Optional.empty()));
        assertFalse(Maps.isEmptyValue(" "));
        assertFalse(Maps.isEmptyValue(List.of("value")));
        assertFalse(Maps.isEmptyValue(new int[] {1}));
        assertFalse(Maps.isEmptyValue(0));
    }

    @Test
    void parsesMapRepresentationsAndPreservesEqualsInValues() {
        assertEquals(
                Map.of("account", "123", "token", "a=b"),
                Maps.parseKeyValueMap("{account=123, token=a=b}"));
        assertTrue(Maps.parseKeyValueMap("{}").isEmpty());
        assertTrue(Maps.parseKeyValueMap(" ").isEmpty());
    }

    @Test
    void rejectsMalformedMapsAndNullArguments() {
        assertThrows(
                IllegalArgumentException.class, () -> Maps.parseKeyValueMap("missing-separator"));
        assertThrows(IllegalArgumentException.class, () -> Maps.parseKeyValueMap(" =value"));
        assertThrows(NullPointerException.class, () -> Maps.parseKeyValueMap(null));
        assertThrows(NullPointerException.class, () -> Maps.removeEmptyEntries(null));
        assertThrows(NullPointerException.class, () -> Maps.removeNullKeys(null));
        assertThrows(NullPointerException.class, () -> Maps.removeEmptyValues(null));
    }
}
