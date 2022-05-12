package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class AppTest {
    private String file1;
    private String file2;
    private String wrongPath;
    private String fileWithEmptyObject;
    private final PrintStream standardOut = System.out;

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        file1 = "src/test/resources/json1.json";
        file2 = "src/test/resources/json2.json";
        wrongPath = "sqwezsxf/qweasf";
        fileWithEmptyObject = "src/test/resources/jsonEmptyObject.json";
        System.setOut(new PrintStream(output));
    }

    @Test
    void main() {
        String expected = """
                {
                   - follow: false
                     host: hexlet.io
                   - proxy: 123.234.53.22
                   - timeout: 50
                   + timeout: 20
                   + verbose: true
                }""";

        App.main(file1, file2);
        assertEquals(expected, output.toString(StandardCharsets.UTF_8).trim());
    }

    @Test
    void mainWithWrongFile1() {
        String expected = "Oops, something went wrong. Try again with different params. \n"
                + "Problem in: %s".formatted(wrongPath);
        App.main(wrongPath, file2);
        assertEquals(expected, output.toString(StandardCharsets.UTF_8).trim());
    }

    @Test
    void mainWithWrongFile2() {
        String expected = "Oops, something went wrong. Try again with different params. \n"
                + "Problem in: %s".formatted(wrongPath);
        App.main(file1, wrongPath);
        assertEquals(expected, output.toString(StandardCharsets.UTF_8).trim());
    }

    @Test
    void mainWithEmptyFile() {
        String expected = "Oops, something went wrong. Try again with different params. \n"
                + "Problem in: No content to map due to end-of-input\n"
                + " at [Source: (String)\"\"; line: 1, column: 0]";
        App.main(file1, fileWithEmptyObject);
        assertEquals(expected, output.toString(StandardCharsets.UTF_8).trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
