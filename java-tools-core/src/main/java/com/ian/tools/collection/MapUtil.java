package com.ian.tools.collection;

import io.github.iankingh.javatools.collection.Maps;
import java.util.Map;

/**
 * @deprecated Use {@link Maps}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public class MapUtil {
    /**
     * @deprecated Use {@link Maps#removeEmptyEntries(Map)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static void removeNullEntry(Map<?, ?> map) {
        Maps.removeEmptyEntries(map);
    }

    /**
     * @deprecated Use {@link Maps#removeNullKeys(Map)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static void removeNullKey(Map<?, ?> map) {
        Maps.removeNullKeys(map);
    }

    /**
     * @deprecated Use {@link Maps#removeEmptyValues(Map)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static void removeNullValue(Map<?, ?> map) {
        Maps.removeEmptyValues(map);
    }

    /**
     * @deprecated Use {@link Maps#removeEmptyValues(Map)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Map<String, String> removeMapEmptyValue(Map<String, String> map) {
        Maps.removeEmptyValues(map);
        return map;
    }

    /**
     * @deprecated Use {@link Maps#parseKeyValueMap(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public Map<String, String> StringConvertMap(String value) {
        return Maps.parseKeyValueMap(value);
    }
}
