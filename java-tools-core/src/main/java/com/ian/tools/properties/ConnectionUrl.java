package com.ian.tools.properties;

import io.github.iankingh.javatools.io.ResourceProperties;
import java.io.IOException;

/**
 * @deprecated Use {@link ResourceProperties}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class ConnectionUrl {
    private ConnectionUrl() {}

    /**
     * @deprecated Use {@link ResourceProperties#getRequired(ClassLoader, String, String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static String getResource(String filename, String propertyName) {
        try {
            return ResourceProperties.getRequired(
                    ConnectionUrl.class.getClassLoader(), filename, propertyName);
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Cannot read classpath properties: " + filename, exception);
        }
    }
}
