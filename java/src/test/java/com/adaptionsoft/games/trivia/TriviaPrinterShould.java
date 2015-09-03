package com.adaptionsoft.games.trivia;

import java.io.PrintStream;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TriviaPrinterShould {

    private Object actualPrinted;

    @Test
    public void
    print_output_to_system_console() {
        createMockedSystemOut();
        Object objectToPrint = "Object to print";

        new TriviaPrinter().print(objectToPrint);

        assertThat(actualPrinted, is(objectToPrint));
    }

    private void createMockedSystemOut() {
        PrintStream mockedSystemOutput = new PrintStream(System.out) {

            @Override
            public void println(Object printed) {
                actualPrinted = printed;
            }
        };

        System.setOut(mockedSystemOutput);
    }
}
