package com.ian.tools.string;

import io.github.iankingh.javatools.text.Identifiers;

/**
 * @deprecated Use {@link Identifiers}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public class RandomStringUtils {
    /**
     * @deprecated Use {@link Identifiers#compactUuid()}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public String genUUIDNoDash() {
        return Identifiers.compactUuid();
    }
}
