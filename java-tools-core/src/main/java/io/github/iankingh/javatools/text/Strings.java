package io.github.iankingh.javatools.text;

import java.util.Objects;

/** Unicode-aware string cleanup, masking, and run detection. */
public final class Strings {
    private Strings() {}

    /** Removes Unicode whitespace and space characters from a string. */
    public static String removeWhitespace(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        return value.codePoints()
                .filter(codePoint -> !isWhitespace(codePoint))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /** Trims Unicode whitespace and space characters from both ends. */
    public static String trimWhitespace(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        int start = 0;
        int end = value.length();
        while (start < end) {
            int codePoint = value.codePointAt(start);
            if (!isWhitespace(codePoint)) {
                break;
            }
            start += Character.charCount(codePoint);
        }
        while (start < end) {
            int codePoint = value.codePointBefore(end);
            if (!isWhitespace(codePoint)) {
                break;
            }
            end -= Character.charCount(codePoint);
        }
        return value.substring(start, end);
    }

    /** Returns whether an alphanumeric run increases or decreases one character at a time. */
    public static boolean hasSequentialRun(String value, int minimumLength) {
        validateRunArguments(value, minimumLength);
        if (minimumLength == 1) {
            return !value.isEmpty();
        }
        int[] codePoints = value.codePoints().toArray();
        int runLength = 1;
        int direction = 0;
        for (int index = 1; index < codePoints.length; index++) {
            int previous = codePoints[index - 1];
            int current = codePoints[index];
            int difference = current - previous;
            int nextDirection = Integer.signum(difference);
            if (sameAsciiCategory(previous, current)
                    && Math.abs(difference) == 1
                    && (direction == 0 || direction == nextDirection)) {
                runLength++;
                direction = nextDirection;
                if (runLength >= minimumLength) {
                    return true;
                }
            } else {
                runLength = 1;
                direction = 0;
            }
        }
        return false;
    }

    /** Returns whether the same character repeats for at least the requested length. */
    public static boolean hasRepeatedRun(String value, int minimumLength) {
        validateRunArguments(value, minimumLength);
        if (minimumLength == 1) {
            return !value.isEmpty();
        }
        int[] codePoints = value.codePoints().toArray();
        int runLength = 1;
        for (int index = 1; index < codePoints.length; index++) {
            runLength = codePoints[index] == codePoints[index - 1] ? runLength + 1 : 1;
            if (runLength >= minimumLength) {
                return true;
            }
        }
        return false;
    }

    /** Replaces a code-point range with the supplied mask character. */
    public static String mask(
            String value, int startInclusive, int endExclusive, char maskCharacter) {
        Objects.requireNonNull(value, "value");
        int[] codePoints = value.codePoints().toArray();
        if (startInclusive < 0
                || endExclusive < startInclusive
                || endExclusive > codePoints.length) {
            throw new IndexOutOfBoundsException("Invalid mask range");
        }
        StringBuilder result = new StringBuilder(value.length());
        for (int index = 0; index < codePoints.length; index++) {
            result.appendCodePoint(
                    index >= startInclusive && index < endExclusive
                            ? maskCharacter
                            : codePoints[index]);
        }
        return result.toString();
    }

    /** Masks at most the requested number of trailing characters. */
    public static String maskLast(String value, int count, char maskCharacter) {
        Objects.requireNonNull(value, "value");
        if (count < 0) {
            throw new IllegalArgumentException("count must be non-negative");
        }
        int length = value.codePointCount(0, value.length());
        return mask(value, Math.max(0, length - count), length, maskCharacter);
    }

    /** Returns true for null values or text containing only Unicode whitespace. */
    public static boolean isBlank(Object value) {
        return value == null || value.toString().isBlank();
    }

    private static void validateRunArguments(String value, int minimumLength) {
        Objects.requireNonNull(value, "value");
        if (minimumLength <= 0) {
            throw new IllegalArgumentException("minimumLength must be positive");
        }
    }

    private static boolean sameAsciiCategory(int left, int right) {
        return (Character.isDigit(left) && Character.isDigit(right))
                || (left >= 'A' && left <= 'Z' && right >= 'A' && right <= 'Z')
                || (left >= 'a' && left <= 'z' && right >= 'a' && right <= 'z');
    }

    private static boolean isWhitespace(int codePoint) {
        return Character.isWhitespace(codePoint) || Character.isSpaceChar(codePoint);
    }
}
