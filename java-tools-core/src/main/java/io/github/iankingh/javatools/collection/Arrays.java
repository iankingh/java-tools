package io.github.iankingh.javatools.collection;

import java.util.Objects;

/** Array operations that preserve the runtime component type. */
public final class Arrays {
    private Arrays() {}

    /**
     * Returns a copy with additional trailing capacity.
     *
     * @param source source array
     * @param additionalLength number of empty slots to append
     * @return expanded copy
     */
    public static <T> T[] expandedCopy(T[] source, int additionalLength) {
        Objects.requireNonNull(source, "source");
        if (additionalLength < 0) {
            throw new IllegalArgumentException("additionalLength must be non-negative");
        }
        return java.util.Arrays.copyOf(source, Math.addExact(source.length, additionalLength));
    }
}
