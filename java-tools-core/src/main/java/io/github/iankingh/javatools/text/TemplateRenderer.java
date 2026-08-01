package io.github.iankingh.javatools.text;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Renders small classpath text templates through literal placeholder replacement. */
public final class TemplateRenderer {
    private static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{([^{}]+)}");

    private TemplateRenderer() {}

    /**
     * Replaces each {@code ${key}} placeholder with its string value.
     *
     * @throws IOException when the resource is missing or cannot be read
     */
    public static String render(ClassLoader classLoader, String resourceName, Map<String, ?> values)
            throws IOException {
        Objects.requireNonNull(classLoader, "classLoader");
        Objects.requireNonNull(resourceName, "resourceName");
        Objects.requireNonNull(values, "values");
        String template;
        try (InputStream input = classLoader.getResourceAsStream(resourceName)) {
            if (input == null) {
                throw new IOException("Classpath template not found: " + resourceName);
            }
            template = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
        Matcher matcher = PLACEHOLDER.matcher(template);
        StringBuilder rendered = new StringBuilder(template.length());
        while (matcher.find()) {
            String key = matcher.group(1);
            String replacement =
                    values.containsKey(key)
                            ? Objects.toString(values.get(key), "")
                            : matcher.group();
            matcher.appendReplacement(rendered, Matcher.quoteReplacement(replacement));
        }
        return matcher.appendTail(rendered).toString();
    }
}
