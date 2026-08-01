package io.github.iankingh.javatools.number;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NumbersTest {
    @Test
    void validatesPositiveIntegerLength() {
        assertTrue(Numbers.isPositiveInteger("12345", 5, 15));
        assertFalse(Numbers.isPositiveInteger("01234", 5, 15));
        assertFalse(Numbers.isPositiveInteger("1234", 5, 15));
        assertFalse(Numbers.isPositiveInteger(null, 5, 15));
        assertThrows(IllegalArgumentException.class, () -> Numbers.isPositiveInteger("1", 0, 1));
        assertThrows(IllegalArgumentException.class, () -> Numbers.isPositiveInteger("1", 2, 1));
    }

    @Test
    void performsDecimalArithmetic() {
        assertEquals(0.3, Numbers.add(0.1, 0.2));
        assertEquals(1.24, Numbers.round(1.235, 2));
        assertThrows(IllegalArgumentException.class, () -> Numbers.round(1.0, -1));
    }

    @Test
    void formatsGroupedValuesWithStableRounding() {
        assertEquals("12,345", Numbers.formatGrouped("12345"));
        assertEquals("12,345.6", Numbers.formatGrouped("12345.6"));
        assertEquals("12,345.68", Numbers.formatGrouped("12345.678"));
        assertThrows(NumberFormatException.class, () -> Numbers.formatGrouped("not-a-number"));
        assertThrows(NullPointerException.class, () -> Numbers.formatGrouped(null));
    }
}
