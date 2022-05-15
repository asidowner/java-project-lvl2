package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatterTest {
    private List<Map<String, Object>> list;
    private String format;

    @BeforeEach
    void setUp() {
        list = List.of(Map.of("status", "added", "field", "key", "newValue", 1));
        format = "stylish1";
    }

    @Test
    void testAnotherFormat() {
        try {
            Formatter.formatText(list, format);
        } catch (IOException e) {
            assertEquals(IOException.class, e.getClass());
        }
    }

    @Test
    void testFormatter() {
        Formatter formatter = new Formatter();
        assertEquals(Formatter.class, formatter.getClass());
    }
}
