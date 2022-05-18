package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;

import java.util.List;
import java.util.Map;


import static hexlet.code.Parser.parseContent;
import static hexlet.code.Tree.build;

public class Differ {

    public static String generate(String pathToFirstFile, String pathToSecondFile, String format) throws IOException {
        checkExtensionsFile(pathToFirstFile, pathToSecondFile);

        Map<String, Object> firstData = getData(pathToFirstFile);
        Map<String, Object> secondData = getData(pathToSecondFile);

        List<Map<String, Object>> list = build(firstData, secondData);

        return Formatter.formatText(list, format);
    }

    public static String generate(String pathToFirstFile, String pathToSecondFile) throws IOException {
        return generate(pathToFirstFile, pathToSecondFile, "stylish");
    }

    private static void checkExtensionsFile(String pathToFirstFile, String pathToSecondFile) throws IOException {
        String firstFileExtension = FilenameUtils.getExtension(pathToFirstFile);
        String secondFileExtension = FilenameUtils.getExtension(pathToSecondFile);

        if (!firstFileExtension.equals(secondFileExtension)) {
            throw new IOException("The files must have the same extensions");
        }
        if (!firstFileExtension.matches("json|yml") || !secondFileExtension.matches("json|yml")) {
            throw new IOException("Unsupported file extension");
        }
    }

    private static Map<String, Object> getData(String filePath) throws IOException {
        String content = Files.readString(Path.of(filePath));
        String extension = FilenameUtils.getExtension(filePath);
        return parseContent(content, extension);
    }
}
