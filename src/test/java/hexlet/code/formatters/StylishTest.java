package hexlet.code.formatters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StylishTest {

    @Test
    void returnAddedLine() {
        String expected = "   + key: value\n";

        assertEquals(expected, new Stylish().returnAddedLine("key", "value"));
    }

    @Test
    void returnRemovedLine() {
        String expected = "   - key: value\n";

        assertEquals(expected, new Stylish().returnRemovedLine("key", "value"));
    }

    @Test
    void returnUnchangedLine() {
        String expected = "     key: value\n";

        assertEquals(expected, new Stylish().returnUnchangedLine("key", "value"));
    }

    @Test
    void returnChangedLine() {
        String expected = "   - key: value1\n   + key: value2\n";

        assertEquals(expected, new Stylish().returnChangedLine("key", "value1", "value2"));
    }

    @Test
    void returnParagraph() {
        String expected = "{\na\n}";
        assertEquals(expected, new Stylish().returnParagraph("a\n"));
    }
}
