package io.github.iankingh.javatools.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LogSanitizerTest {
    @Test
    void convertsUntrustedValuesToOneLine() {
        assertEquals("", LogSanitizer.singleLine(null));
        assertEquals(
                "first second thirdfourth",
                LogSanitizer.singleLine("first\nsecond\rthird%0afourth"));
        assertEquals("value", LogSanitizer.singleLine("value"));
    }
}
