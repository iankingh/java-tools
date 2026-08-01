package io.github.iankingh.javatools.collection;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ArraysTest {
    @Test
    void expandsArrayWithoutChangingComponentType() {
        String[] expanded = Arrays.expandedCopy(new String[] {"one", "two"}, 2);

        assertArrayEquals(new String[] {"one", "two", null, null}, expanded);
    }

    @Test
    void rejectsInvalidArguments() {
        assertThrows(NullPointerException.class, () -> Arrays.expandedCopy(null, 1));
        assertThrows(IllegalArgumentException.class, () -> Arrays.expandedCopy(new String[0], -1));
    }
}
