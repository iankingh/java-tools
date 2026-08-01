package io.github.iankingh.javatools.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Null-safe map cleanup and parsing operations. */
public final class Maps {
    private Maps() {}

    /** Removes null keys and empty values from the supplied mutable map. */
    public static void removeEmptyEntries(Map<?, ?> map) {
        Objects.requireNonNull(map, "map");
        map.entrySet().removeIf(entry -> entry.getKey() == null || isEmptyValue(entry.getValue()));
    }

    /** Removes null keys from the supplied mutable map. */
    public static void removeNullKeys(Map<?, ?> map) {
        Objects.requireNonNull(map, "map");
        map.keySet().removeIf(Objects::isNull);
    }

    /** Removes empty values from the supplied mutable map. */
    public static void removeEmptyValues(Map<?, ?> map) {
        Objects.requireNonNull(map, "map");
        map.entrySet().removeIf(entry -> isEmptyValue(entry.getValue()));
    }

    /** Returns whether a value is null or an empty text, collection, map, optional, or array. */
    public static boolean isEmptyValue(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof CharSequence text) {
            return text.isEmpty();
        }
        if (value instanceof Collection<?> collection) {
            return collection.isEmpty();
        }
        if (value instanceof Map<?, ?> map) {
            return map.isEmpty();
        }
        if (value instanceof Optional<?> optional) {
            return optional.isEmpty();
        }
        return value.getClass().isArray() && Array.getLength(value) == 0;
    }

    /**
     * Parses the common {@code {key=value, other=value}} representation.
     *
     * <p>Values may contain {@code =}. Commas are treated as entry separators.
     */
    public static Map<String, String> parseKeyValueMap(String input) {
        Objects.requireNonNull(input, "input");
        String content = input.strip();
        if (content.startsWith("{") && content.endsWith("}")) {
            content = content.substring(1, content.length() - 1).strip();
        }
        Map<String, String> result = new LinkedHashMap<>();
        if (content.isEmpty()) {
            return result;
        }
        for (String pair : content.split(",")) {
            String[] entry = pair.split("=", 2);
            if (entry.length != 2 || entry[0].isBlank()) {
                throw new IllegalArgumentException("Invalid key-value entry: " + pair.strip());
            }
            result.put(entry[0].strip(), entry[1].strip());
        }
        return result;
    }
}
