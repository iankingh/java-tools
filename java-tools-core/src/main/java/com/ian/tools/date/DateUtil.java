package com.ian.tools.date;

import io.github.iankingh.javatools.time.DateTimes;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * @deprecated Prefer {@link DateTimes} and {@code java.time} types.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class DateUtil {
    private static final ZoneId SYSTEM_ZONE = ZoneId.systemDefault();

    private DateUtil() {}

    /**
     * @deprecated Use {@link DateTimes#parseDate(String)} or an explicit formatter.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Date StringToDate(String value) {
        try {
            return toDate(DateTimes.parseDateTime(value));
        } catch (DateTimeParseException exception) {
            return toDate(DateTimes.parseDate(value).atStartOfDay());
        }
    }

    /**
     * @deprecated Use an explicit {@link DateTimeFormatter}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Date StringToDate(String value, String pattern) {
        Objects.requireNonNull(value, "value");
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(normalizePattern(pattern), Locale.ROOT);
        try {
            return toDate(LocalDateTime.parse(value, formatter));
        } catch (DateTimeParseException exception) {
            return toDate(LocalDate.parse(value, formatter).atStartOfDay());
        }
    }

    /**
     * @deprecated Use an explicit {@link DateTimeFormatter}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String DateToString(Date date, String pattern) {
        return LocalDateTime.ofInstant(
                        Objects.requireNonNull(date, "date").toInstant(), SYSTEM_ZONE)
                .format(DateTimeFormatter.ofPattern(normalizePattern(pattern), Locale.ROOT));
    }

    /**
     * @deprecated Use {@link LocalDate#plusDays(long)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Date addDay(Date date, int amount) {
        return toDate(toLocalDateTime(date).plusDays(amount));
    }

    /**
     * @deprecated Use {@link LocalDate#plusMonths(long)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Date addMonth(Date date, int amount) {
        return toDate(toLocalDateTime(date).plusMonths(amount));
    }

    /**
     * @deprecated Use {@link LocalDate#plusYears(long)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Date addYear(Date date, int amount) {
        return toDate(toLocalDateTime(date).plusYears(amount));
    }

    /**
     * @deprecated Use {@link DateTimes#daysBetween(LocalDate, LocalDate)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static int getIntervalDays(Date left, Date right) {
        return Math.toIntExact(
                DateTimes.daysBetween(
                        toLocalDateTime(left).toLocalDate(), toLocalDateTime(right).toLocalDate()));
    }

    /**
     * @deprecated Use {@link DateTimes#compactDate(Clock)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String getYYYYMMDD() {
        return DateTimes.compactDate(Clock.systemDefaultZone());
    }

    /**
     * @deprecated Use {@link DateTimes#compactTime(Clock)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String getHHMMSS() {
        return DateTimes.compactTime(Clock.systemDefaultZone());
    }

    /**
     * @deprecated Use {@link DateTimes#isWithin(LocalDateTime, LocalDateTime, LocalDateTime,
     *     boolean)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static boolean CheckTimebelongCalendar(Date value, Date start, Date end) {
        return DateTimes.isWithin(
                toLocalDateTime(value), toLocalDateTime(start), toLocalDateTime(end), false);
    }

    private static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(
                Objects.requireNonNull(date, "date").toInstant(), SYSTEM_ZONE);
    }

    private static Date toDate(LocalDateTime dateTime) {
        Instant instant = dateTime.atZone(SYSTEM_ZONE).toInstant();
        return Date.from(instant);
    }

    private static String normalizePattern(String pattern) {
        return Objects.requireNonNull(pattern, "pattern").replace("yyyy", "uuuu");
    }
}
