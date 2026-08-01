package com.ian.tools.collection;

import io.github.iankingh.javatools.collection.Arrays;

/**
 * @deprecated Use {@link Arrays}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public class ArrayUtils {
    /**
     * @deprecated Use {@link Arrays#expandedCopy(Object[], int)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public Object[] arraycloneAdd(Object[] data, int addLength) {
        return Arrays.expandedCopy(data, addLength);
    }
}
