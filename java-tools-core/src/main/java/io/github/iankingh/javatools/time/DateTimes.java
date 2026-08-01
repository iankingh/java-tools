package io.github.iankingh.javatools.time;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/** Thread-safe date and time operations based on {@code java.time}. */
public final class DateTimes {
    private static final List<DateTimeFormatter> DATE_FORMATTERS =
            List.of(
                    DateTimeFormatter.ISO_LOCAL_DATE,
                    strictFormatter("uuuu/MM/dd"),
                    strictFormatter("uuuuMMdd"));
    private static final List<DateTimeFormatter> DATE_TIME_FORMATTERS =
            List.of(
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                    strictFormatter("uuuu-MM-dd HH:mm:ss"),
                    strictFormatter("uuuu-MM-dd HH:mm"),
                    strictFormatter("uuuu/MM/dd HH:mm:ss"),
                    strictFormatter("uuuu/MM/dd HH:mm"));

    private DateTimes() {}

    /** Parses a date using the supported strict formats. */
    public static LocalDate parseDate(String value) {
        return tryParseDate(value)
                .orElseThrow(() -> new DateTimeParseException("Unsupported date format", value, 0));
    }

    /** Attempts to parse a date using the supported strict formats. */
    public static Optional<LocalDate> tryParseDate(String value) {
        if (value == null) {
            return Optional.empty();
        }
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return Optional.of(LocalDate.parse(value, formatter));
            } catch (DateTimeParseException ignored) {
                // Try the next documented format.
            }
        }
        return Optional.empty();
    }

    /** Parses a date-time using the supported strict formats. */
    public static LocalDateTime parseDateTime(String value) {
        Objects.requireNonNull(value, "value");
        for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
            try {
                return LocalDateTime.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next documented format.
            }
        }
        throw new DateTimeParseException("Unsupported date-time format", value, 0);
    }

    /** Converts a date between explicit strict patterns. Use {@code uuuu} for the year. */
    public static String convertDate(String value, String sourcePattern, String targetPattern) {
        Objects.requireNonNull(value, "value");
        LocalDate parsed = LocalDate.parse(value, strictFormatter(sourcePattern));
        return parsed.format(strictFormatter(targetPattern));
    }

    /** Returns the absolute number of calendar days between two dates. */
    public static long daysBetween(LocalDate left, LocalDate right) {
        Objects.requireNonNull(left, "left");
        Objects.requireNonNull(right, "right");
        return Math.abs(ChronoUnit.DAYS.between(left, right));
    }

    /** Checks a range with explicit boundary semantics. */
    public static boolean isWithin(
            LocalDateTime value, LocalDateTime start, LocalDateTime end, boolean inclusive) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("end must not be before start");
        }
        if (inclusive) {
            return !value.isBefore(start) && !value.isAfter(end);
        }
        return value.isAfter(start) && value.isBefore(end);
    }

    /** Returns the current date as {@code uuuuMMdd}. */
    public static String compactDate(Clock clock) {
        return LocalDate.now(Objects.requireNonNull(clock, "clock"))
                .format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    /** Returns the current time as {@code HHmmss}. */
    public static String compactTime(Clock clock) {
        return LocalTime.now(Objects.requireNonNull(clock, "clock"))
                .format(DateTimeFormatter.ofPattern("HHmmss", Locale.ROOT));
    }

    private static DateTimeFormatter strictFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.ROOT)
                .withResolverStyle(ResolverStyle.STRICT);
    }
}
