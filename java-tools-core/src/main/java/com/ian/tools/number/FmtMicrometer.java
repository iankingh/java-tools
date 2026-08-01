package com.ian.tools.number;

import io.github.iankingh.javatools.number.Numbers;

/**
 * @deprecated Use {@link Numbers#formatGrouped(String)}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class FmtMicrometer {
    private FmtMicrometer() {}

    /**
     * @deprecated Use {@link Numbers#formatGrouped(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String fmtMicrometer(String value) {
        return Numbers.formatGrouped(value);
    }
}
