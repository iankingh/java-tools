package io.github.iankingh.javatools.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

class DateTimesTest {
    @Test
    void parsesSupportedStrictDateFormats() {
        assertEquals(LocalDate.of(2026, 7, 30), DateTimes.parseDate("2026-07-30"));
        assertEquals(LocalDate.of(2026, 7, 30), DateTimes.parseDate("2026/07/30"));
        assertEquals(LocalDate.of(2026, 7, 30), DateTimes.parseDate("20260730"));
        assertTrue(DateTimes.tryParseDate(null).isEmpty());
        assertTrue(DateTimes.tryParseDate("2026-02-30").isEmpty());
        assertThrows(DateTimeParseException.class, () -> DateTimes.parseDate("30/07/2026"));
    }

    @Test
    void parsesSupportedDateTimeFormats() {
        LocalDateTime expected = LocalDateTime.of(2026, 7, 30, 23, 20, 31);
        assertEquals(expected, DateTimes.parseDateTime("2026-07-30T23:20:31"));
        assertEquals(expected, DateTimes.parseDateTime("2026-07-30 23:20:31"));
        assertEquals(expected, DateTimes.parseDateTime("2026/07/30 23:20:31"));
        assertEquals(expected.withSecond(0), DateTimes.parseDateTime("2026-07-30 23:20"));
        assertEquals(expected.withSecond(0), DateTimes.parseDateTime("2026/07/30 23:20"));
        assertThrows(DateTimeParseException.class, () -> DateTimes.parseDateTime("invalid"));
        assertThrows(NullPointerException.class, () -> DateTimes.parseDateTime(null));
    }

    @Test
    void convertsDatesAndCalculatesAbsoluteIntervals() {
        assertEquals("30/07/2026", DateTimes.convertDate("2026-07-30", "uuuu-MM-dd", "dd/MM/uuuu"));
        assertEquals(2, DateTimes.daysBetween(LocalDate.of(2026, 8, 1), LocalDate.of(2026, 7, 30)));
        assertThrows(
                NullPointerException.class, () -> DateTimes.daysBetween(null, LocalDate.now()));
    }

    @Test
    void checksInclusiveAndExclusiveRanges() {
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime end = start.plusHours(1);

        assertTrue(DateTimes.isWithin(start, start, end, true));
        assertFalse(DateTimes.isWithin(start, start, end, false));
        assertTrue(DateTimes.isWithin(start.plusMinutes(1), start, end, false));
        assertFalse(DateTimes.isWithin(end.plusNanos(1), start, end, true));
        assertThrows(
                IllegalArgumentException.class, () -> DateTimes.isWithin(start, end, start, true));
        assertThrows(NullPointerException.class, () -> DateTimes.isWithin(null, start, end, true));
    }

    @Test
    void formatsCurrentDateAndTimeFromInjectedClock() {
        Clock clock = Clock.fixed(Instant.parse("2026-07-30T15:20:31Z"), ZoneOffset.UTC);

        assertEquals("20260730", DateTimes.compactDate(clock));
        assertEquals("152031", DateTimes.compactTime(clock));
        assertThrows(NullPointerException.class, () -> DateTimes.compactDate(null));
    }
}
