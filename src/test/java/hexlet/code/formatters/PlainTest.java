package hexlet.code.formatters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainTest {
    @Test
    void returnAddedLine() {
        String expected = "Property '%s' was added with value: '%s'\n".formatted("key", "value");
        assertEquals(expected, new Plain().returnAddedLine("key", "value"));
    }

    @Test
    void returnRemovedLine() {
        String expected = "Property '%s' was removed\n".formatted("key");
        assertEquals(expected, new Plain().returnRemovedLine("key", "value"));
    }

    @Test
    void returnUnchangedLine() {
        String expected = "";
        assertEquals(expected, new Plain().returnUnchangedLine("key", "value"));
    }

    @Test
    void returnChangedLine() {
        String[] str = {"a", "b"};
        String expected = "Property '%s' was updated. From %s to %s\n".formatted("key", "[complex value]", 1);
        assertEquals(expected, new Plain().returnChangedLine("key", str, 1));
    }

    @Test
    void returnParagraph() {
        String expected = "\n%s".formatted("a");
        assertEquals(expected, new Plain().returnParagraph("a"));
    }
}
