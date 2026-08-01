package io.github.iankingh.javatools.security;

/** Prevents untrusted values from creating additional log lines. */
public final class LogSanitizer {
    private LogSanitizer() {}

    /** Removes literal and common percent-encoded CR/LF sequences. */
    public static String singleLine(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("(?i)%0d|%0a", "").replace('\r', ' ').replace('\n', ' ');
    }
}
