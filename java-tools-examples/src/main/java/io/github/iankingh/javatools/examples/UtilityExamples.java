package io.github.iankingh.javatools.examples;

import io.github.iankingh.javatools.number.Numbers;
import io.github.iankingh.javatools.text.Strings;
import io.github.iankingh.javatools.time.DateTimes;
import java.time.Clock;

/** Small, dependency-free examples for the core module. */
public final class UtilityExamples {
    private UtilityExamples() {}

    /**
     * Runs the core-library examples.
     *
     * @param args ignored command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Date: " + DateTimes.compactDate(Clock.systemDefaultZone()));
        System.out.println("Amount: " + Numbers.formatGrouped("12345.67"));
        System.out.println("Masked: " + Strings.maskLast("1234567890", 4, '*'));
    }
}
