package com.ian.tools.other;

import io.github.iankingh.javatools.legacy.LegacyTripleDes;

/**
 * @deprecated Triple DES is obsolete. Use {@link
 *     io.github.iankingh.javatools.security.AesGcmCrypto}.
 */
@Deprecated(forRemoval = true, since = "1.0")
@SuppressWarnings("removal")
public final class STD3Des {
    private STD3Des() {}

    /**
     * @deprecated Migration only.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static byte[] des3EncodeECB(byte[] key, byte[] data) throws Exception {
        return LegacyTripleDes.encryptEcb(key, data);
    }

    /**
     * @deprecated Migration only.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static byte[] ees3DecodeECB(byte[] key, byte[] data) throws Exception {
        return LegacyTripleDes.decryptEcb(key, data);
    }

    /**
     * @deprecated Migration only.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyIv, byte[] data) throws Exception {
        return LegacyTripleDes.encryptCbc(key, keyIv, data);
    }

    /**
     * @deprecated Migration only.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyIv, byte[] data) throws Exception {
        return LegacyTripleDes.decryptCbc(key, keyIv, data);
    }
}
