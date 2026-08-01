package com.ian.tools.other;

import io.github.iankingh.javatools.validation.EmailAddresses;

/**
 * @deprecated Use {@link EmailAddresses}.
 */
@Deprecated(forRemoval = true, since = "1.0")
public class EmailValidator {
    /**
     * @deprecated Use {@link EmailAddresses#isValid(String)}.
     */
    @Deprecated(forRemoval = true, since = "1.0")
    public boolean validateEmail(String email) {
        return EmailAddresses.isValid(email);
    }
}
