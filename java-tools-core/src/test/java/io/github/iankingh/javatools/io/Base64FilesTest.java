package io.github.iankingh.javatools.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class Base64FilesTest {
    @TempDir Path temporaryDirectory;

    @Test
    void decodesPlainAndDataUriPayloads() {
        String encoded =
                Base64.getEncoder().encodeToString("image".getBytes(StandardCharsets.UTF_8));

        assertArrayEquals("image".getBytes(StandardCharsets.UTF_8), Base64Files.decode(encoded));
        assertArrayEquals(
                "image".getBytes(StandardCharsets.UTF_8),
                Base64Files.decode("data:image/png;base64," + encoded));
    }

    @Test
    void writesDecodedBytes() throws Exception {
        String encoded = Base64.getEncoder().encodeToString(new byte[] {1, 2, 3});
        Path target = temporaryDirectory.resolve("image.bin");

        assertEquals(target, Base64Files.write(encoded, target));
        assertArrayEquals(new byte[] {1, 2, 3}, Files.readAllBytes(target));
    }

    @Test
    void rejectsInvalidPayloads() {
        assertThrows(NullPointerException.class, () -> Base64Files.decode(null));
        assertThrows(
                IllegalArgumentException.class, () -> Base64Files.decode("data:text/plain,value"));
        assertThrows(IllegalArgumentException.class, () -> Base64Files.decode("%%%"));
        assertThrows(NullPointerException.class, () -> Base64Files.write("", null));
    }
}
