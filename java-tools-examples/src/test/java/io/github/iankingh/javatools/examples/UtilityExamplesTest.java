package io.github.iankingh.javatools.examples;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/** Smoke test that the examples entry point runs end-to-end and prints expected lines. */
class UtilityExamplesTest {

    @Test
    void mainRunsAndPrintsExpectedLines() {
        var originalOut = System.out;
        var captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
        try {
            UtilityExamples.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }
        var output = captured.toString();
        assertTrue(output.contains("Date: "), "expected Date line");
        assertTrue(output.contains("Amount: "), "expected Amount line");
        assertTrue(output.contains("Masked: "), "expected Masked line");
    }
}
