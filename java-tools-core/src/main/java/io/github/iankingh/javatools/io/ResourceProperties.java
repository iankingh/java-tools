package io.github.iankingh.javatools.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/** UTF-8 classpath property loading with explicit missing-resource errors. */
public final class ResourceProperties {
    private ResourceProperties() {}

    /** Loads a UTF-8 properties resource through the supplied class loader. */
    public static Properties load(ClassLoader classLoader, String resourceName) throws IOException {
        Objects.requireNonNull(classLoader, "classLoader");
        Objects.requireNonNull(resourceName, "resourceName");
        URL resource = classLoader.getResource(resourceName);
        if (resource == null) {
            throw new IOException("Classpath resource not found: " + resourceName);
        }
        try (InputStream input = resource.openStream()) {
            Properties properties = new Properties();
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            return properties;
        }
    }

    /** Reads a required property from a UTF-8 classpath resource. */
    public static String getRequired(
            ClassLoader classLoader, String resourceName, String propertyName) throws IOException {
        Objects.requireNonNull(propertyName, "propertyName");
        String value = load(classLoader, resourceName).getProperty(propertyName);
        if (value == null) {
            throw new IllegalArgumentException(
                    "Property '" + propertyName + "' is missing from " + resourceName);
        }
        return value;
    }
}
