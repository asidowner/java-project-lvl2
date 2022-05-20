package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Files;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class AppTest {
    private String file1json;
    private String file2json;
    private String wrongPath;
    private static String expectedStylish;
    private static final String PATH_TO_FIXTURES = "src/test/resources/fixtures/";
    private final PrintStream standardOut = System.out;

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeAll
    static void setBefore() throws IOException {
        expectedStylish = Files.readString(Path.of(PATH_TO_FIXTURES + "expectedStylish.txt"));
    }

    @BeforeEach
    void setUp() {
        file1json = PATH_TO_FIXTURES + "file1.json";
        file2json = PATH_TO_FIXTURES + "file2.json";
        wrongPath = PATH_TO_FIXTURES + "sqwezsxf/qweasf.json";
        System.setOut(new PrintStream(output));
    }

    @Test
    void mainJson() {
        App.main(file1json, file2json);
        assertEquals(expectedStylish, output.toString(StandardCharsets.UTF_8).trim());
    }

    @Test
    void mainWithWrongJsonFile1() {
        String expectedError = "Oops, something went wrong. Try again with different params. \n"
                + "Problem in: %s".formatted(wrongPath);
        App.main(wrongPath, file2json);
        assertEquals(expectedError, output.toString(StandardCharsets.UTF_8).trim());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
