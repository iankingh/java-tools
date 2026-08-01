package com.ian.tools.string;

import io.github.iankingh.javatools.text.Strings;

/**
 * @deprecated Use {@link Strings}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public class StrUtils {
    /**
     * @deprecated Use {@link Strings#removeWhitespace(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String removeAllBlank(String value) {
        return Strings.removeWhitespace(value);
    }

    /**
     * @deprecated Use {@link Strings#trimWhitespace(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String trim(String value) {
        return Strings.trimWhitespace(value);
    }

    /**
     * @deprecated Use {@link Strings#hasSequentialRun(String, int)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public boolean continuousLetterAndNumCheck(String value, int length) {
        return Strings.hasSequentialRun(value, length);
    }

    /**
     * @deprecated Use {@link Strings#hasRepeatedRun(String, int)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static boolean SameLetterAndNumCheck(String value, int length) {
        return Strings.hasRepeatedRun(value, length);
    }

    /**
     * @deprecated Use {@link Strings#removeWhitespace(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String replaceBlank(String value) {
        return Strings.removeWhitespace(value);
    }

    /**
     * @deprecated Use {@link Strings#mask(String, int, int, char)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String strReplaceByMask(String value) {
        return Strings.mask(
                value, Math.max(0, value.length() - 5), Math.max(0, value.length() - 2), '*');
    }

    /**
     * @deprecated Use {@link Strings#maskLast(String, int, char)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String strReplaceByMask2(String value) {
        return Strings.maskLast(value, 5, '*');
    }

    /**
     * @deprecated Use {@link Strings#isBlank(Object)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static boolean isBlank(Object value) {
        return Strings.isBlank(value);
    }
}
