package io.github.iankingh.javatools.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringsTest {
    @Test
    void removesAndTrimsUnicodeWhitespace() {
        assertEquals("abc", Strings.removeWhitespace(" a\u3000b\nc\t"));
        assertEquals("a b", Strings.trimWhitespace("\u3000 a b \n"));
        assertEquals("", Strings.removeWhitespace(null));
        assertEquals("", Strings.trimWhitespace(""));
    }

    @Test
    void detectsIncreasingAndDecreasingRunsWithinOneCategory() {
        assertTrue(Strings.hasSequentialRun("xx1234yy", 4));
        assertTrue(Strings.hasSequentialRun("zyx", 3));
        assertTrue(Strings.hasSequentialRun("a", 1));
        assertFalse(Strings.hasSequentialRun("abC", 3));
        assertFalse(Strings.hasSequentialRun("12a", 3));
        assertFalse(Strings.hasSequentialRun("", 1));
    }

    @Test
    void detectsRepeatedRuns() {
        assertTrue(Strings.hasRepeatedRun("baaaac", 4));
        assertTrue(Strings.hasRepeatedRun("\uD83D\uDE00\uD83D\uDE00", 2));
        assertTrue(Strings.hasRepeatedRun("x", 1));
        assertFalse(Strings.hasRepeatedRun("abca", 2));
        assertFalse(Strings.hasRepeatedRun("", 1));
    }

    @Test
    void masksRangesAndTrailingCharacters() {
        assertEquals("12***6", Strings.mask("123456", 2, 5, '*'));
        assertEquals("12####", Strings.maskLast("123456", 4, '#'));
        assertEquals("**", Strings.maskLast("12", 5, '*'));
        assertEquals("12", Strings.maskLast("12", 0, '*'));
        assertEquals("A*", Strings.maskLast("A\uD83D\uDE00", 1, '*'));
    }

    @Test
    void validatesMaskAndRunArguments() {
        assertThrows(NullPointerException.class, () -> Strings.mask(null, 0, 0, '*'));
        assertThrows(IndexOutOfBoundsException.class, () -> Strings.mask("abc", -1, 2, '*'));
        assertThrows(IndexOutOfBoundsException.class, () -> Strings.mask("abc", 2, 1, '*'));
        assertThrows(IndexOutOfBoundsException.class, () -> Strings.mask("abc", 0, 4, '*'));
        assertThrows(IllegalArgumentException.class, () -> Strings.maskLast("abc", -1, '*'));
        assertThrows(IllegalArgumentException.class, () -> Strings.hasSequentialRun("abc", 0));
        assertThrows(NullPointerException.class, () -> Strings.hasRepeatedRun(null, 2));
    }

    @Test
    void identifiesBlankObjects() {
        assertTrue(Strings.isBlank(null));
        assertTrue(Strings.isBlank(" \t"));
        assertFalse(Strings.isBlank(0));
    }

    @Test
    void generatesCompactUuid() {
        String identifier = Identifiers.compactUuid();

        assertEquals(32, identifier.length());
        assertTrue(identifier.matches("[0-9a-f]{32}"));
    }
}
