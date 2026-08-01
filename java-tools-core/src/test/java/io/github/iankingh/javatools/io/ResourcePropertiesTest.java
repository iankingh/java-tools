package io.github.iankingh.javatools.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class ResourcePropertiesTest {
    private final ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void loadsUtf8PropertiesAndRequiredValues() throws Exception {
        assertEquals("value", ResourceProperties.load(classLoader, "sample.properties").get("key"));
        assertEquals(
                "Traditional Chinese",
                ResourceProperties.getRequired(classLoader, "sample.properties", "localized"));
    }

    @Test
    void reportsMissingResourcesAndProperties() {
        assertThrows(
                IOException.class,
                () -> ResourceProperties.load(classLoader, "missing.properties"));
        assertThrows(
                IllegalArgumentException.class,
                () -> ResourceProperties.getRequired(classLoader, "sample.properties", "missing"));
        assertThrows(
                NullPointerException.class,
                () -> ResourceProperties.load(null, "sample.properties"));
    }
}
