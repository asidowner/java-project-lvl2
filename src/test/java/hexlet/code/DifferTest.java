package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
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
    private static String expectedStylish;
    private static String expectedPlain;
    private static String expectedJson;
    private static final String PATH_TO_FIXTURES = "src/test/resources/fixtures/";

    @BeforeAll
    static void setBefore() throws IOException {
        expectedStylish = Files.readString(Path.of(PATH_TO_FIXTURES + "expectedStylish.txt"));
        expectedPlain = Files.readString(Path.of(PATH_TO_FIXTURES + "expectedPlain.txt"));
        expectedJson = Files.readString(Path.of(PATH_TO_FIXTURES + "expectedJson.txt"));
    }

    @BeforeEach
    void setUp() {
        file1json = PATH_TO_FIXTURES + "file1.json";
        file2json = PATH_TO_FIXTURES + "file2.json";
        file1yaml = PATH_TO_FIXTURES + "file1.yml";
        file2yaml = PATH_TO_FIXTURES + "file2.yaml";
        file1txt =  PATH_TO_FIXTURES + "file1.txt";
        file2txt =  PATH_TO_FIXTURES + "file2.txt";
        file1FullPath = Path.of(file1json).toAbsolutePath().toString();
    }

    @Test
    void testGenerateStylish() throws IOException {
        assertEquals(Differ.generate(file1json, file2json), expectedStylish);
        assertEquals(Differ.generate(file1FullPath, file2json, formatStylish), expectedStylish);
        assertEquals(Differ.generate(file1yaml, file2yaml, formatStylish), expectedStylish);
    }

    @Test
    void testGeneratePlain() throws IOException {
        assertEquals(expectedPlain, Differ.generate(file1yaml, file2yaml, formatPlain));
    }

    @Test
    void testGenerateJson() throws IOException {
        assertEquals(expectedJson, Differ.generate(file1yaml, file2yaml, formatJson));
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
