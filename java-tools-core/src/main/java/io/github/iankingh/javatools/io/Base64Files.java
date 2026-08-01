package io.github.iankingh.javatools.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;

/** Base64 decoding and file-writing operations with explicit failures. */
public final class Base64Files {
    private Base64Files() {}

    /** Decodes plain Base64 or a {@code data:*;base64,...} URI. */
    public static byte[] decode(String encoded) {
        Objects.requireNonNull(encoded, "encoded");
        String payload = encoded.strip();
        if (payload.startsWith("data:")) {
            int separator = payload.indexOf(',');
            if (separator < 0 || !payload.substring(0, separator).endsWith(";base64")) {
                throw new IllegalArgumentException("Data URI must contain a base64 payload");
            }
            payload = payload.substring(separator + 1);
        }
        return Base64.getDecoder().decode(payload);
    }

    /** Decodes a payload and writes it to the target path. */
    public static Path write(String encoded, Path target) throws IOException {
        Objects.requireNonNull(target, "target");
        return Files.write(target, decode(encoded));
    }
}
