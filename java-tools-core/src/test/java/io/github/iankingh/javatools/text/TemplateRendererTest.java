package io.github.iankingh.javatools.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TemplateRendererTest {
    @Test
    void rendersLiteralPlaceholders() throws Exception {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("name", "Ian");
        values.put("count", 2);
        values.put("optional", null);

        assertEquals(
                "Hello Ian, count=2, optional=.\n",
                TemplateRenderer.render(getClass().getClassLoader(), "template.txt", values));
    }

    @Test
    void reportsMissingTemplatesAndInvalidArguments() {
        assertThrows(
                IOException.class,
                () ->
                        TemplateRenderer.render(
                                getClass().getClassLoader(), "missing.txt", Map.of()));
        assertThrows(
                NullPointerException.class,
                () -> TemplateRenderer.render(null, "template.txt", Map.of()));
    }

    @Test
    void doesNotReinterpretPlaceholdersInsideValues() throws Exception {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("name", "${count}");
        values.put("count", 99);

        assertEquals(
                "Hello ${count}, count=99, optional=${optional}.\n",
                TemplateRenderer.render(getClass().getClassLoader(), "template.txt", values));
    }
}
