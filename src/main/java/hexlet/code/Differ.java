package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FilenameUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {

    public static String generate(String pathToFirstFile, String pathToSecondFile, String format) throws IOException {
        if (!FilenameUtils.getExtension(pathToFirstFile).equals(FilenameUtils.getExtension(pathToSecondFile))) {
            throw new IOException("The files must have the same extensions");
        }

        Map<String, Object> firstFile = getMapFromFile(pathToFirstFile);
        Map<String, Object> secondFile = getMapFromFile(pathToSecondFile);

        return "{\n%s}".formatted(Stream.concat(firstFile.keySet().stream(), secondFile.keySet().stream())
                .distinct()
                .sorted(Comparator.comparing(String::format))
                .map(key -> {
                    if (firstFile.containsKey(key) && secondFile.containsKey(key)) {
                        if (firstFile.get(key).equals(secondFile.get(key))) {
                            return formatLine(" ", key, secondFile.get(key));
                        } else {
                            return formatLine("-", key, firstFile.get(key))
                                    + formatLine("+", key, secondFile.get(key));
                        }
                    } else if (secondFile.containsKey(key)) {
                        return formatLine("+", key, secondFile.get(key));
                    } else {
                        return formatLine("-", key, firstFile.get(key));
                    }
                }).collect(Collectors.joining()));
    }

    private static String formatLine(String diff, String key, Object value) {
        return "   " + diff + " " + formatKeyValue(key, value) + "\n";
    }

    private static String formatKeyValue(String key, Object value) {
        return key + ": " + value;
    }

    private static Map<String, Object> getMapFromFile(String filePath) throws IOException {
        String fileExtension = FilenameUtils.getExtension(filePath);
        if (fileExtension.equals("json")) {
            return getMapFromJson(filePath);

        } else if (fileExtension.equals("yml")) {
            return getMapFromYaml(filePath);
        } else {
            throw new IOException("Unsupported file extension");
        }
    }


    private static Map<String, Object> getMapFromJson(String filePath) throws IOException {
        String file = Files.readString(Path.of(filePath));
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(file, new TypeReference<Map<String, Object>>() {
        });
    }

    private static Map<String, Object> getMapFromYaml(String filePath) throws IOException {
        String file = Files.readString(Path.of(filePath));
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(file, new TypeReference<Map<String, Object>>() {
        });
    }
}
