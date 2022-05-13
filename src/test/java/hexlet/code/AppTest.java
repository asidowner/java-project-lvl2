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
    private String file1json;
    private String file2json;
    private String wrongPath;
    private final PrintStream standardOut = System.out;

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        file1json = "src/test/resources/file1.json";
        file2json = "src/test/resources/file2.json";
        wrongPath = "sqwezsxf/qweasf.json";
        System.setOut(new PrintStream(output));
    }

    @Test
    void mainJson() {
        String expected = """
                {
                   - follow: false
                     host: hexlet.io
                   - proxy: 123.234.53.22
                   - timeout: 50
                   + timeout: 20
                   + verbose: true
                }""";

        App.main(file1json, file2json);
        assertEquals(expected, output.toString(StandardCharsets.UTF_8).trim());
    }

    @Test
    void mainWithWrongJsonFile1() {
        String expected = "Oops, something went wrong. Try again with different params. \n"
                + "Problem in: %s".formatted(wrongPath);
        App.main(wrongPath, file2json);
        assertEquals(expected, output.toString(StandardCharsets.UTF_8).trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
