package io.github.iankingh.javatools.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** JSON tree cleanup that works on a defensive copy. */
public final class JsonValues {
    private JsonValues() {}

    /** Removes null nodes and empty text values recursively without mutating the input tree. */
    public static JsonNode withoutNullsAndEmptyStrings(JsonNode input) {
        Objects.requireNonNull(input, "input");
        JsonNode copy = input.deepCopy();
        prune(copy);
        return copy;
    }

    private static void prune(JsonNode node) {
        if (node instanceof ObjectNode object) {
            List<String> removals = new ArrayList<>();
            object.properties()
                    .forEach(
                            entry -> {
                                JsonNode value = entry.getValue();
                                if (value.isNull()
                                        || (value.isTextual() && value.textValue().isEmpty())) {
                                    removals.add(entry.getKey());
                                } else {
                                    prune(value);
                                }
                            });
            object.remove(removals);
        } else if (node instanceof ArrayNode array) {
            for (JsonNode value : array) {
                prune(value);
            }
        }
    }
}
