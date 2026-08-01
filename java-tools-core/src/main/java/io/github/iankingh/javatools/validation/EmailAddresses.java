package io.github.iankingh.javatools.validation;

import java.util.regex.Pattern;

/** Practical email-address validation for application input. */
public final class EmailAddresses {
    private static final int MAX_LENGTH = 254;
    private static final Pattern PATTERN =
            Pattern.compile(
                    "^[A-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?(?:\\.[A-Z0-9](?:[A-Z0-9-]{0,61}[A-Z0-9])?)+$",
                    Pattern.CASE_INSENSITIVE);

    private EmailAddresses() {}

    /** Returns true when the input has a valid, routable-looking email syntax. */
    public static boolean isValid(String email) {
        return email != null
                && email.length() <= MAX_LENGTH
                && PATTERN.matcher(email).matches()
                && !email.contains("..");
    }
}
