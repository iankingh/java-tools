package io.github.iankingh.javatools.security;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/** Authenticated encryption using AES-GCM and a fresh 96-bit nonce per message. */
public final class AesGcmCrypto {
    private static final int AUTHENTICATION_TAG_BITS = 128;
    private static final int NONCE_BYTES = 12;

    private AesGcmCrypto() {}

    /** Generates a 128-bit or 256-bit AES key. */
    public static SecretKey generateKey(int bits) throws GeneralSecurityException {
        if (bits != 128 && bits != 256) {
            throw new IllegalArgumentException("AES key size must be 128 or 256 bits");
        }
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(bits);
        return generator.generateKey();
    }

    /** Encrypts plaintext and authenticates optional additional data. */
    public static EncryptedPayload encrypt(
            byte[] plaintext, SecretKey key, byte[] additionalData, SecureRandom random)
            throws GeneralSecurityException {
        Objects.requireNonNull(plaintext, "plaintext");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(random, "random");
        byte[] nonce = new byte[NONCE_BYTES];
        random.nextBytes(nonce);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(AUTHENTICATION_TAG_BITS, nonce));
        if (additionalData != null) {
            cipher.updateAAD(additionalData);
        }
        return new EncryptedPayload(nonce, cipher.doFinal(plaintext));
    }

    /** Decrypts and verifies an encrypted payload. */
    public static byte[] decrypt(EncryptedPayload payload, SecretKey key, byte[] additionalData)
            throws GeneralSecurityException {
        Objects.requireNonNull(payload, "payload");
        Objects.requireNonNull(key, "key");
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(
                Cipher.DECRYPT_MODE,
                key,
                new GCMParameterSpec(AUTHENTICATION_TAG_BITS, payload.nonce()));
        if (additionalData != null) {
            cipher.updateAAD(additionalData);
        }
        return cipher.doFinal(payload.ciphertext());
    }

    /** Immutable encrypted bytes and the nonce required to decrypt them. */
    public record EncryptedPayload(byte[] nonce, byte[] ciphertext) {
        public EncryptedPayload {
            Objects.requireNonNull(nonce, "nonce");
            Objects.requireNonNull(ciphertext, "ciphertext");
            if (nonce.length != NONCE_BYTES) {
                throw new IllegalArgumentException("AES-GCM nonce must be 12 bytes");
            }
            nonce = Arrays.copyOf(nonce, nonce.length);
            ciphertext = Arrays.copyOf(ciphertext, ciphertext.length);
        }

        @Override
        public byte[] nonce() {
            return Arrays.copyOf(nonce, nonce.length);
        }

        @Override
        public byte[] ciphertext() {
            return Arrays.copyOf(ciphertext, ciphertext.length);
        }
    }
}
