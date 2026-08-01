package io.github.iankingh.javatools.legacy;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@SuppressWarnings("removal")
class LegacyTripleDesTest {
    private static final byte[] KEY = "0123456789abcdefghijklmn".getBytes(StandardCharsets.UTF_8);
    private static final byte[] IV = "12345678".getBytes(StandardCharsets.UTF_8);
    private static final byte[] DATA = "legacy payload".getBytes(StandardCharsets.UTF_8);

    @Test
    void supportsMigrationRoundTrips() throws Exception {
        assertArrayEquals(
                DATA, LegacyTripleDes.decryptEcb(KEY, LegacyTripleDes.encryptEcb(KEY, DATA)));
        assertArrayEquals(
                DATA,
                LegacyTripleDes.decryptCbc(KEY, IV, LegacyTripleDes.encryptCbc(KEY, IV, DATA)));
    }
}
