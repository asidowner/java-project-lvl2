package hexlet.code;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class DifferTest {
    private String file1json;
    private String file2json;
    private String file1FullPath;
    private String emptyFile;
    private String fileWithEmptyObject;
    private String wrongPath;
    private String file1yaml;
    private String file2yaml;
    private String file1txt;
    private String file2txt;
    private String format = "stylish";

    @BeforeEach
    void setUp() {
        file1json = "src/test/resources/file1.json";
        file2json = "src/test/resources/file2.json";
        file1yaml = "src/test/resources/file1.yml";
        file2yaml = "src/test/resources/file2.yml";
        file1txt = "src/test/resources/file1.txt";
        file2txt = "src/test/resources/file2.txt";
        emptyFile = "src/test/resources/emptyFile.json";
        fileWithEmptyObject = "src/test/resources/jsonEmptyObject.json";
        file1FullPath = Path.of(file1json).toAbsolutePath().toString();
        wrongPath = "12/asda/123assf/qwe.json";
    }

    @Test
    void testGenerate() throws IOException {
        String expected = """
                {
                   - follow: false
                     host: hexlet.io
                   - proxy: 123.234.53.22
                   - timeout: 50
                   + timeout: 20
                   + verbose: true
                }""";
        assertEquals(Differ.generate(file1json, file2json, format), expected);
        assertEquals(Differ.generate(file1FullPath, file2json, format), expected);
        assertEquals(Differ.generate(file1yaml, file2yaml, format), expected);
        assertEquals(Differ.generate(file1yaml, file2yaml, format), expected);
    }

    @Test
    void testGenerateWithEmptyFile() {
        assertThrowsExactly(MismatchedInputException.class, () -> {
            Differ.generate(emptyFile, file2json, format);
        });
        assertThrowsExactly(MismatchedInputException.class, () -> {
            Differ.generate(file1json, emptyFile, format);
        });
    }

    @Test
    void testGenerateWithEmptyObject() {
        assertThrowsExactly(NoSuchFileException.class, () -> {
            Differ.generate(fileWithEmptyObject, file2json, format);
        });
        assertThrowsExactly(NoSuchFileException.class, () -> {
            Differ.generate(file1json, fileWithEmptyObject, format);
        });
    }

    @Test
    void testGenerateWrongPath() {
        assertThrowsExactly(NoSuchFileException.class, () -> {
            Differ.generate(wrongPath, file2json, format);
        });
        assertThrowsExactly(NoSuchFileException.class, () -> {
            Differ.generate(file1json, wrongPath, format);
        });
    }

    @Test
    void testGenerateWithUnknownExtension() {
        assertThrowsExactly(IOException.class, () -> {
            Differ.generate(file1txt, file2txt, format);
        });
    }

    @Test
    void testGenerateWithDifferentExtension() {
        assertThrowsExactly(IOException.class, () -> {
            Differ.generate(file1json, file2yaml, format);
        });
    }

    @Test
    void testGenerateSameFiles() throws IOException {
        String expected = """
                {
                     follow: false
                     host: hexlet.io
                     proxy: 123.234.53.22
                     timeout: 50
                }""";
        assertEquals(Differ.generate(file1json, file1json, format), expected);
    }

    @Test
    void testDiffer() {
        Differ dif = new Differ();
        assertEquals(Differ.class, dif.getClass());
    }
}
