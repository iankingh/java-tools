package com.ian.tools.file;

import io.github.iankingh.javatools.io.Base64Files;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * @deprecated Use {@link Base64Files}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class ImageUtil {
    private ImageUtil() {}

    /**
     * @deprecated Use {@link Base64Files#write(String, Path)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static boolean save(String base64, String destinationFilename) {
        try {
            Base64Files.write(base64, Path.of(destinationFilename));
            return true;
        } catch (IOException | IllegalArgumentException exception) {
            return false;
        }
    }

    /**
     * @deprecated Use {@link Base64Files#decode(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static Optional<byte[]> decodeBase64(String base64) {
        try {
            return Optional.of(Base64Files.decode(base64));
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }
}
