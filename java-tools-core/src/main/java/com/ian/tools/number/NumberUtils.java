package com.ian.tools.number;

import io.github.iankingh.javatools.number.Numbers;

/**
 * @deprecated Use {@link Numbers}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class NumberUtils {
    private NumberUtils() {}

    /**
     * @deprecated Use {@link Numbers#isPositiveInteger(String, int, int)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static boolean checkNum(String value) {
        return Numbers.isPositiveInteger(value, 5, 15);
    }

    /**
     * @deprecated Use {@link Numbers#add(double, double)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static double add(double left, double right) {
        return Numbers.add(left, right);
    }

    /**
     * @deprecated Use {@link Numbers#round(double, int)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static double round(double value, int scale) {
        return Numbers.round(value, scale);
    }
}
