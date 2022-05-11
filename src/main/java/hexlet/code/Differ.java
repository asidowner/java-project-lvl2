package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {

    public static String generate(String pathToFirstFile, String pathToSecondFile, String format) throws IOException {
        String firstFile = Files.readString(Path.of(pathToFirstFile));
        String secondFile = Files.readString(Path.of(pathToSecondFile));

        ObjectMapper objectMapper = new ObjectMapper();
        SortedMap<String, Object> firstJson = objectMapper.readValue(firstFile,
                new TypeReference<SortedMap<String, Object>>() {
                });
        SortedMap<String, Object> secondJson = objectMapper.readValue(secondFile,
                new TypeReference<SortedMap<String, Object>>() {
                });

        StringBuilder result = new StringBuilder("{\n");

        Stream.concat(firstJson.keySet().stream(), secondJson.keySet().stream())
                .distinct()
                .sorted(Comparator.comparing(String::format))
                .map(key -> {
                    if (firstJson.containsKey(key) && secondJson.containsKey(key)) {
                        if (firstJson.get(key).equals(secondJson.get(key))) {
                            return formatLine(Map.of(" ", Map.of(key, secondJson.get(key))));
                        } else {
                            Map<String, Map<String, Object>> lines = new LinkedHashMap<>();
                            lines.put("-", Map.of(key, firstJson.get(key)));
                            lines.put("+", Map.of(key, secondJson.get(key)));
                            return formatLine(lines);
                        }
                    } else if (secondJson.containsKey(key)) {
                        return formatLine(Map.of("+", Map.of(key, secondJson.get(key))));
                    } else {
                        return formatLine(Map.of("-", Map.of(key, firstJson.get(key))));
                    }
                })
                .forEach(result::append);

        result.append("}");
        return result.toString();
    }

    private static String formatLine(Map<String, Map<String, Object>> lines) {
        StringBuilder result = new StringBuilder();
        for (String diff : lines.keySet()) {
            for (String key : lines.get(diff).keySet()) {
                result.append(formatLine(diff, key, lines.get(diff).get(key)));
            }
        }
        return result.toString();
    }

    private static String formatLine(String diff, String key, Object value) {
        return "   " + diff + " " + formatKeyValue(key, value) + "\n";
    }

    private static String formatKeyValue(String key, Object value) {
        return key + ": " + value;
    }
}
