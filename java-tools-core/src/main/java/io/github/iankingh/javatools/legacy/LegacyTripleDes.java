package io.github.iankingh.javatools.legacy;

import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Migration-only Triple DES support.
 *
 * @deprecated Triple DES and ECB mode are obsolete. Use {@link
 *     io.github.iankingh.javatools.security.AesGcmCrypto} for new data.
 */
@Deprecated(forRemoval = true, since = "1.0")
public final class LegacyTripleDes {
    private LegacyTripleDes() {}

    /** Encrypts with legacy DESede/ECB/PKCS5Padding. */
    public static byte[] encryptEcb(byte[] key, byte[] data) throws GeneralSecurityException {
        return cipher(Cipher.ENCRYPT_MODE, key, null, "DESede/ECB/PKCS5Padding").doFinal(data);
    }

    /** Decrypts with legacy DESede/ECB/PKCS5Padding. */
    public static byte[] decryptEcb(byte[] key, byte[] data) throws GeneralSecurityException {
        return cipher(Cipher.DECRYPT_MODE, key, null, "DESede/ECB/PKCS5Padding").doFinal(data);
    }

    /** Encrypts with legacy DESede/CBC/PKCS5Padding. */
    public static byte[] encryptCbc(byte[] key, byte[] iv, byte[] data)
            throws GeneralSecurityException {
        return cipher(Cipher.ENCRYPT_MODE, key, iv, "DESede/CBC/PKCS5Padding").doFinal(data);
    }

    /** Decrypts with legacy DESede/CBC/PKCS5Padding. */
    public static byte[] decryptCbc(byte[] key, byte[] iv, byte[] data)
            throws GeneralSecurityException {
        return cipher(Cipher.DECRYPT_MODE, key, iv, "DESede/CBC/PKCS5Padding").doFinal(data);
    }

    private static Cipher cipher(int mode, byte[] key, byte[] iv, String transformation)
            throws GeneralSecurityException {
        DESedeKeySpec keySpec = new DESedeKeySpec(key);
        var secretKey = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
        Cipher cipher = Cipher.getInstance(transformation);
        if (iv == null) {
            cipher.init(mode, secretKey);
        } else {
            cipher.init(mode, secretKey, new IvParameterSpec(iv));
        }
        return cipher;
    }
}
