package com.ian.tools.other;

import java.util.Arrays;

/**
 * 
 */
public class EmailValidatorTest {

    // list of valid email addresses
    private static final String[] validEmailIds = new String[] { "journaldev@yahoo.com", "journaldev-100@yahoo.com",
            "journaldev.100@yahoo.com", "journaldev111@journaldev.com", "journaldev-100@journaldev.net",
            "journaldev.100@journaldev.com.au", "journaldev@1.com", "journaldev@gmail.com.com",
            "journaldev+100@gmail.com", "journaldev-100@yahoo-test.com", "journaldev_100@yahoo-test.ABC.CoM" };

    // list of invalid email addresses
    private static final String[] invalidEmailIds = new String[] { "journaldev", "journaldev@.com.my",
            "journaldev123@gmail.a", "journaldev123@.com", "journaldev123@.com.com", ".journaldev@journaldev.com",
            "journaldev()*@gmail.com", "journaldev@%*.com", "journaldev..2002@gmail.com", "journaldev.@gmail.com",
            "journaldev@journaldev@gmail.com", "journaldev@gmail.com.1a" };

    public static void main(String args[]) {
        EmailValidator  emailValidator  =new  EmailValidator();
        System.out.println("---------------------- validEmailIds --------------------------------------");
        // for (String temp : validEmailIds) {
        // boolean valid = emailValidator.validateEmail(temp);
        // System.out.println("Email ID " + temp + " is valid? " + valid);
        // }
        Arrays.stream(validEmailIds)
                .forEach(s -> System.out.println("Email ID " + s + " is valid ? :" + emailValidator.validateEmail(s)));

        System.out.println("---------------------- invalidEmailIds --------------------------------------");
        // for (String temp : invalidEmailIds) {
        // boolean valid = emailValidator.validateEmail(temp);
        // System.out.println("Email ID " + temp + " is valid? " + valid);
        // }
        Arrays.stream(invalidEmailIds)
                .forEach(s -> System.out.println("Email ID " + s + " is valid ? :" + emailValidator.validateEmail(s)));
    }

}
