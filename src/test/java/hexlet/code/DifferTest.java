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
    private String file1;
    private String file2;
    private String file1FullPath;
    private String emptyFile;
    private String fileWithEmptyObject;
    private String wrongPath;
    private String format = "stylish";

    @BeforeEach
    void setUp() {
        file1 = "src/test/resources/json1.json";
        file2 = "src/test/resources/json2.json";
        emptyFile = "src/test/resources/emptyFile.json";
        fileWithEmptyObject = "src/test/resources/jsonEmptyObject.json";
        file1FullPath = Path.of(file1).toAbsolutePath().toString();
        wrongPath = "12/asda/123assf/qwe";
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
        assertEquals(Differ.generate(file1, file2, format), expected);
        assertEquals(Differ.generate(file1FullPath, file2, format), expected);
    }

    @Test
    void testGenerateWithEmptyFile() {
        assertThrowsExactly(MismatchedInputException.class, () -> {
            Differ.generate(emptyFile, file2, format);
        });
        assertThrowsExactly(MismatchedInputException.class, () -> {
            Differ.generate(file1, emptyFile, format);
        });
    }

    @Test
    void testGenerateWithEmptyObject() {
        assertThrowsExactly(MismatchedInputException.class, () -> {
            Differ.generate(fileWithEmptyObject, file2, format);
        });
        assertThrowsExactly(MismatchedInputException.class, () -> {
            Differ.generate(file1, fileWithEmptyObject, format);
        });
    }

    @Test
    void testGenerateWrongPath() {
        assertThrowsExactly(NoSuchFileException.class, () -> {
            Differ.generate(wrongPath, file2, format);
        });
        assertThrowsExactly(NoSuchFileException.class, () -> {
            Differ.generate(file1, wrongPath, format);
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
        assertEquals(Differ.generate(file1, file1, format), expected);
    }
}
