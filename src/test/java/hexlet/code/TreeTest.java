package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeTest {
    private Map<String, Object> map1;
    private Map<String, Object> map2;

    @BeforeEach
    void setUp() {
        map1 = Map.of("key", "value", "key2", 2, "key3", 1);
        map2 = Map.of("key", List.of("a", "b"), "key1", 1, "key3", 1);
    }

    @Test
    void testBuild() {
        List<Map<String, Object>> expected = List.of(
                Map.of("field", "key", "status", "changed", "oldValue", "value",
                        "newValue", List.of("a", "b")),
                Map.of("field", "key1", "status", "added", "newValue", 1),
                Map.of("field", "key2", "status", "removed", "oldValue", 2),
                Map.of("field", "key3", "status", "unchanged", "value", 1)
        );
        assertEquals(expected, Tree.build(map1, map2));
    }
}
