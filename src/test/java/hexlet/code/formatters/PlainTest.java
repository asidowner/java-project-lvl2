package hexlet.code.formatters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlainTest {
    private List<Map<String, Object>> list;

    @BeforeEach
    void setUp() {
        list = List.of(Map.of("status", "added", "field", "key", "newValue", 1),
                Map.of("status", "removed", "field", "key1", "oldValue", "value"),
                Map.of("status", "unchanged", "field", "key2", "value", "value"),
                Map.of("status", "changed", "field", "key3",
                        "newValue", "value", "oldValue", new String[]{"a", "b"}));
    }

    @Test
    void testFormatText() {
        String expected = "\nProperty 'key' was added with value: 1\n"
                          + "Property 'key1' was removed\n"
                          + "Property 'key3' was updated. From [complex value] to 'value'\n";

        assertEquals(expected, new Plain().formatText(list));
    }

    @Test
    void testUnknownFormatText() {
        list = List.of(Map.of("status", "added1", "field", "key", "newValue", 1));
        try {
            new Plain().formatText(list);
        } catch (Exception e) {
            assertEquals(RuntimeException.class, e.getClass());
        }
    }
}
