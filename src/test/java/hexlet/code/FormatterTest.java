package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatterTest {
    @Test
    void testAnotherFormat() {
        try {
            new Formatter("123");
        } catch (IOException e) {
            assertEquals(IOException.class, e.getClass());
        }
    }
}
