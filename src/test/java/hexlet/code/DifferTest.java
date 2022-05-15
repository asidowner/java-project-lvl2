package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class DifferTest {
    private String file1json;
    private String file2json;
    private String file1FullPath;
    private String file1yaml;
    private String file2yaml;
    private String file1txt;
    private String file2txt;
    private final String formatStylish = "stylish";
    private final String formatPlain = "plain";
    private final String formatJson = "json";

    @BeforeEach
    void setUp() {
        file1json = "src/test/resources/file1.json";
        file2json = "src/test/resources/file2.json";
        file1yaml = "src/test/resources/file1.yml";
        file2yaml = "src/test/resources/file2.yml";
        file1txt = "src/test/resources/file1.txt";
        file2txt = "src/test/resources/file2.txt";
        file1FullPath = Path.of(file1json).toAbsolutePath().toString();
    }

    @Test
    void testGenerateStylish() throws IOException {
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";
        assertEquals(Differ.generate(file1json, file2json), expected);
        assertEquals(Differ.generate(file1FullPath, file2json, formatStylish), expected);
        assertEquals(Differ.generate(file1yaml, file2yaml, formatStylish), expected);
    }

    @Test
    void testGeneratePlain() throws IOException {
        String expected = "\nProperty 'chars2' was updated. From [complex value] to false"
                          + "\nProperty 'checked' was updated. From false to true"
                          + "\nProperty 'default' was updated. From null to [complex value]"
                          + "\nProperty 'id' was updated. From 45 to null"
                          + "\nProperty 'key1' was removed"
                          + "\nProperty 'key2' was added with value: 'value2'"
                          + "\nProperty 'numbers2' was updated. From [complex value] to [complex value]"
                          + "\nProperty 'numbers3' was removed"
                          + "\nProperty 'numbers4' was added with value: [complex value]"
                          + "\nProperty 'obj1' was added with value: [complex value]"
                          + "\nProperty 'setting1' was updated. From 'Some value' to 'Another value'"
                          + "\nProperty 'setting2' was updated. From 200 to 300"
                          + "\nProperty 'setting3' was updated. From true to 'none'";
        assertEquals(expected, Differ.generate(file1yaml, file2yaml, formatPlain));
    }

    @Test
    void testGenerateJson() throws IOException {
        String expected = "[{\"field\":\"chars1\",\"status\":\"unchanged\",\"value\":[\"a\",\"b\",\"c\"]},"
                          + "{\"field\":\"chars2\",\"newValue\":false,\"oldValue\":[\"d\",\"e\",\"f\"],"
                          + "\"status\":\"changed\"},{\"field\":\"checked\",\"newValue\":true,\"oldValue\":false,"
                          + "\"status\":\"changed\"},{\"field\":\"default\",\"newValue\":[\"value1\",\"value2\"],"
                          + "\"oldValue\":\"null\",\"status\":\"changed\"},{\"field\":\"id\",\"newValue\":\"null\","
                          + "\"oldValue\":45,\"status\":\"changed\"},{\"field\":\"key1\",\"oldValue\":\"value1\","
                          + "\"status\":\"removed\"},{\"field\":\"key2\",\"newValue\":\"value2\",\"status\":\"added\"},"
                          + "{\"field\":\"numbers1\",\"status\":\"unchanged\",\"value\":[1,2,3,4]},{"
                          + "\"field\":\"numbers2\",\"newValue\":[22,33,44,55],\"oldValue\":[2,3,4,5],"
                          + "\"status\":\"changed\"},{\"field\":\"numbers3\",\"oldValue\":[3,4,5],"
                          + "\"status\":\"removed\"},{\"field\":\"numbers4\",\"newValue\":[4,5,6],\"status\":\"added\"}"
                          + ",{\"field\":\"obj1\",\"newValue\":{\"isNested\":true,\"nestedKey\":\"value\"},"
                          + "\"status\":\"added\"},{\"field\":\"setting1\",\"newValue\":\"Another value\","
                          + "\"oldValue\":\"Some value\",\"status\":\"changed\"},{\"field\":\"setting2\","
                          + "\"newValue\":300,\"oldValue\":200,\"status\":\"changed\"},{\"field\":\"setting3\","
                          + "\"newValue\":\"none\",\"oldValue\":true,\"status\":\"changed\"}]";
        assertEquals(expected, Differ.generate(file1yaml, file2yaml, formatJson));
    }

    @Test
    void testGenerateWithUnknownExtension() {
        assertThrowsExactly(IOException.class, () -> {
            Differ.generate(file1txt, file2txt, formatStylish);
        });
        assertThrowsExactly(IOException.class, () -> {
            Differ.generate(file1txt, file2json, formatStylish);
        });
        assertThrowsExactly(IOException.class, () -> {
            Differ.generate(file1json, file2txt, formatStylish);
        });
    }

    @Test
    void testGenerateWithDifferentExtension() {
        assertThrowsExactly(IOException.class, () -> {
            Differ.generate(file1json, file2yaml, formatStylish);
        });
    }

    @Test
    void testGenerateWithAnotherExtension() {
        assertThrowsExactly(IOException.class, () -> {
            Differ.generate(file1json, file2yaml, formatStylish);
        });
    }

    @Test
    void testDiffer() {
        Differ dif = new Differ();
        assertEquals(Differ.class, dif.getClass());
    }
}
