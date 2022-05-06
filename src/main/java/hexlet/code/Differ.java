package hexlet.code;

import java.util.ArrayList;
import java.util.Arrays;

public class Differ {

    public static ArrayList<String> generate(String pathToFirstFile, String pathToSecondFile, String format) {
        return new ArrayList<String>(Arrays.asList("a", "b", format));
    }
}
