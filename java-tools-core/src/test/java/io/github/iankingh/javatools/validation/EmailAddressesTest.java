package io.github.iankingh.javatools.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmailAddressesTest {
    @Test
    void acceptsPracticalAddressesAndRejectsInvalidInput() {
        assertTrue(EmailAddresses.isValid("person+tag@example.com"));
        assertTrue(EmailAddresses.isValid("A.B@example.co.uk"));
        assertFalse(EmailAddresses.isValid(null));
        assertFalse(EmailAddresses.isValid("person@example"));
        assertFalse(EmailAddresses.isValid("person..name@example.com"));
        assertFalse(EmailAddresses.isValid("x".repeat(245) + "@example.com"));
    }
}
