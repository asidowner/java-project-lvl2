package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {

    public static String generate(String pathToFirstFile, String pathToSecondFile, String format) throws IOException {
        Map<String, Object> firstJson = getJsonAsMap(pathToFirstFile);
        Map<String, Object> secondJson = getJsonAsMap(pathToSecondFile);

        return "{\n%s}".formatted(Stream.concat(firstJson.keySet().stream(), secondJson.keySet().stream())
                .distinct()
                .sorted(Comparator.comparing(String::format))
                .map(key -> {
                    if (firstJson.containsKey(key) && secondJson.containsKey(key)) {
                        if (firstJson.get(key).equals(secondJson.get(key))) {
                            return formatLine(" ", key, secondJson.get(key));
                        } else {
                            return formatLine("-", key, firstJson.get(key))
                                    + formatLine("+", key, secondJson.get(key));
                        }
                    } else if (secondJson.containsKey(key)) {
                        return formatLine("+", key, secondJson.get(key));
                    } else {
                        return formatLine("-", key, firstJson.get(key));
                    }
                }).collect(Collectors.joining()));
    }

    private static String formatLine(String diff, String key, Object value) {
        return "   " + diff + " " + formatKeyValue(key, value) + "\n";
    }

    private static String formatKeyValue(String key, Object value) {
        return key + ": " + value;
    }

    private static Map<String, Object> getJsonAsMap(String filePath) throws IOException {
        String file = Files.readString(Path.of(filePath));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, new TypeReference<Map<String, Object>>() {
        });
    }
}
