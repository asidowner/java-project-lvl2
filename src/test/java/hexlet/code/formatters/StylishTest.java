package hexlet.code.formatters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StylishTest {
    private List<Map<String, Object>> list;

    @BeforeEach
    void setUp() {
        List<String> str = List.of("a", "b");
        list = List.of(Map.of("status", "added", "field", "key", "newValue", 1),
                Map.of("status", "removed", "field", "key1", "oldValue", "value"),
                Map.of("status", "unchanged", "field", "key2", "value", "value"),
                Map.of("status", "changed", "field", "key3",
                        "newValue", "value", "oldValue", str));
    }
    @Test
    void testFormatText() {
        String expected = "{\n"
                + "   + key: 1\n"
                + "   - key1: value\n"
                + "     key2: value\n"
                + "   - key3: [a, b]\n"
                + "   + key3: value\n"
                + "}";

        assertEquals(expected, new Stylish().formatText(list));
    }

    @Test
    void testBadDiffStatus() {
        try {
            list = List.of(Map.of("status", "added1", "field", "key", "newValue", 1));
            new Stylish().formatText(list);
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
        }
    }
}
