package io.github.iankingh.javatools.text;

import java.util.UUID;

/** Identifier generation helpers. */
public final class Identifiers {
    private Identifiers() {}

    /** Returns a lowercase UUID without separators. */
    public static String compactUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
