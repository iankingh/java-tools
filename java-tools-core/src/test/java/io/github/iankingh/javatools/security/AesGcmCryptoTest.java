package io.github.iankingh.javatools.security;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.AEADBadTagException;
import org.junit.jupiter.api.Test;

class AesGcmCryptoTest {
    @Test
    void encryptsAndAuthenticatesData() throws Exception {
        var key = AesGcmCrypto.generateKey(128);
        byte[] additionalData = "context".getBytes();
        var encrypted =
                AesGcmCrypto.encrypt("secret".getBytes(), key, additionalData, new SecureRandom());

        assertArrayEquals(
                "secret".getBytes(), AesGcmCrypto.decrypt(encrypted, key, additionalData));
        assertNotSame(encrypted.nonce(), encrypted.nonce());
        assertNotSame(encrypted.ciphertext(), encrypted.ciphertext());
    }

    @Test
    void supportsNullAdditionalDataAnd256BitKeys() throws Exception {
        var key = AesGcmCrypto.generateKey(256);
        var encrypted = AesGcmCrypto.encrypt(new byte[0], key, null, new SecureRandom());

        assertArrayEquals(new byte[0], AesGcmCrypto.decrypt(encrypted, key, null));
    }

    @Test
    void rejectsTamperingAndInvalidArguments() throws Exception {
        var key = AesGcmCrypto.generateKey(128);
        var encrypted = AesGcmCrypto.encrypt("secret".getBytes(), key, null, new SecureRandom());
        byte[] tampered = encrypted.ciphertext();
        tampered[0] ^= 1;

        assertThrows(
                AEADBadTagException.class,
                () ->
                        AesGcmCrypto.decrypt(
                                new AesGcmCrypto.EncryptedPayload(encrypted.nonce(), tampered),
                                key,
                                null));
        assertThrows(IllegalArgumentException.class, () -> AesGcmCrypto.generateKey(192));
        assertThrows(
                IllegalArgumentException.class,
                () -> new AesGcmCrypto.EncryptedPayload(new byte[8], new byte[1]));
        assertThrows(
                NullPointerException.class,
                () -> AesGcmCrypto.encrypt(null, key, null, new SecureRandom()));
        assertThrows(
                GeneralSecurityException.class,
                () -> AesGcmCrypto.decrypt(encrypted, AesGcmCrypto.generateKey(128), null));
    }
}
