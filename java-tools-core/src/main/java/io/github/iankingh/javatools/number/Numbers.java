package io.github.iankingh.javatools.number;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/** Locale-stable numeric validation, arithmetic, and formatting. */
public final class Numbers {
    private Numbers() {}

    /** Validates a positive integer with an inclusive digit-length range. */
    public static boolean isPositiveInteger(String value, int minimumDigits, int maximumDigits) {
        if (minimumDigits <= 0 || maximumDigits < minimumDigits) {
            throw new IllegalArgumentException("Invalid digit range");
        }
        if (value == null) {
            return false;
        }
        Pattern pattern =
                Pattern.compile(
                        "[1-9][0-9]{" + (minimumDigits - 1) + "," + (maximumDigits - 1) + "}");
        return pattern.matcher(value).matches();
    }

    /** Adds two decimal values without introducing binary floating-point construction error. */
    public static double add(double left, double right) {
        return BigDecimal.valueOf(left).add(BigDecimal.valueOf(right)).doubleValue();
    }

    /** Rounds a value using {@link RoundingMode#HALF_UP}. */
    public static double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("scale must be non-negative");
        }
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /** Formats a decimal using grouping separators and its existing scale, capped at two digits. */
    public static String formatGrouped(String value) {
        Objects.requireNonNull(value, "value");
        BigDecimal decimal = new BigDecimal(value);
        int scale = Math.min(Math.max(decimal.scale(), 0), 2);
        DecimalFormat format =
                new DecimalFormat(
                        "#,##0" + (scale == 0 ? "" : "." + "0".repeat(scale)),
                        DecimalFormatSymbols.getInstance(Locale.ROOT));
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(decimal);
    }
}
