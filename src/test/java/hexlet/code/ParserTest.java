package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {
    private String json;
    private String yml;

    @BeforeEach
    void setUp() {
        json = """
                {
                  "key": "value"
                }""";
        yml = "key: value";
    }

    @Test
    void testParseContent() throws IOException {
        Map<String, Object> expected = Map.of("key", "value");

        assertEquals(expected, Parser.parseContent(json, "json"));
        assertEquals(expected, Parser.parseContent(yml, "yml"));
    }

    @Test
    void testBadFormat() {
        try {
            Parser.parseContent(yml, "yml1");
        } catch (Exception e) {
            assertEquals(IOException.class, e.getClass());
        }
    }
}
