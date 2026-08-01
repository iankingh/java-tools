package io.github.iankingh.javatools.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class JsonValuesTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void removesEmptyValuesRecursivelyWithoutMutatingInput() throws Exception {
        var input =
                objectMapper.readTree(
                        """
            {"name":"","nested":{"remove":null,"keep":"value"},"items":[{"remove":""}]}
            """);

        var result = JsonValues.withoutNullsAndEmptyStrings(input);

        assertEquals(
                objectMapper.readTree(
                        """
            {"nested":{"keep":"value"},"items":[{}]}
            """),
                result);
        assertEquals("", input.get("name").textValue());
    }

    @Test
    void handlesScalarAndRejectsNull() throws Exception {
        var scalar = objectMapper.readTree("\"value\"");
        assertEquals(scalar, JsonValues.withoutNullsAndEmptyStrings(scalar));
        assertThrows(
                NullPointerException.class, () -> JsonValues.withoutNullsAndEmptyStrings(null));
    }
}
