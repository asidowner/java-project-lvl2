package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FilenameUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {
    private static final int INDENT = 3;

    public static String generate(String pathToFirstFile, String pathToSecondFile, String format) throws IOException {
        if (!FilenameUtils.getExtension(pathToFirstFile).equals(FilenameUtils.getExtension(pathToSecondFile))) {
            throw new IOException("The files must have the same extensions");
        }

        if (!format.matches("stylish")) {
            throw new IOException("Unknown format for result set");
        }

        Map<String, Object> firstFile = getMapFromFile(pathToFirstFile);
        Map<String, Object> secondFile = getMapFromFile(pathToSecondFile);

        String paragraph = Stream.concat(firstFile.keySet().stream(), secondFile.keySet().stream())
                .distinct()
                .sorted(Comparator.comparing(String::format))
                .map(key -> {
                    Object value1 = Optional.ofNullable(firstFile.get(key)).orElse("null");
                    Object value2 = Optional.ofNullable(secondFile.get(key)).orElse("null");

                    if (firstFile.containsKey(key) && secondFile.containsKey(key)) {
                        if (value1.equals(value2)) {
                            return formatLine(" ", key, value2, format);
                        } else {
                            return formatLine("-", key, value1, format)
                                    + formatLine("+", key, value2, format);
                        }
                    } else if (secondFile.containsKey(key)) {
                        return formatLine("+", key, value2, format);
                    } else {
                        return formatLine("-", key, value1, format);
                    }
                }).collect(Collectors.joining());

        return formatParagraph(paragraph, format);
    }


    private static String formatLine(String diff, String key, Object value, String format) {
        return "%s%s %s: %s\n".formatted(" ".repeat(INDENT), diff, key, value);
    }

    private static String formatParagraph(String paragraph, String format) {
        return "{\n%s}".formatted(paragraph);
    }

    private static Map<String, Object> getMapFromFile(String filePath) throws IOException {
        String fileExtension = FilenameUtils.getExtension(filePath);
        if (fileExtension.matches("json|yml")) {
            String file = Files.readString(Path.of(filePath));
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            return objectMapper.readValue(file, new TypeReference<Map<String, Object>>() {
            });

        } else {
            throw new IOException("Unsupported file extension");
        }
    }
}
