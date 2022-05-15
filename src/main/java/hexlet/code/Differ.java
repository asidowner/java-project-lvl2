package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FilenameUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {

    public static String generate(String pathToFirstFile, String pathToSecondFile, String format) throws IOException {
        checkExtensionsFile(pathToFirstFile, pathToSecondFile);

        Map<String, Object> firstFile = getMapFromFile(pathToFirstFile);
        Map<String, Object> secondFile = getMapFromFile(pathToSecondFile);


        List<Map<String, Object>> list;

        list = Stream.concat(firstFile.keySet().stream(), secondFile.keySet().stream())
                .distinct()
                .sorted(Comparator.comparing(String::format))
                .map(key -> {
                    Object value1 = Optional.ofNullable(firstFile.get(key)).orElse("null");
                    Object value2 = Optional.ofNullable(secondFile.get(key)).orElse("null");
                    Map<String, Object> map = new HashMap<>();

                    if (firstFile.containsKey(key) && secondFile.containsKey(key)) {
                        if (value1.equals(value2)) {
                            map.put("status", "unchanged");
                            map.put("field", key);
                            map.put("value", value1);
                        } else {
                            map.put("status", "changed");
                            map.put("field", key);
                            map.put("oldValue", value1);
                            map.put("newValue", value2);
                        }
                    } else if (secondFile.containsKey(key)) {
                        map.put("status", "added");
                        map.put("field", key);
                        map.put("newValue", value2);
                    } else {
                        map.put("status", "removed");
                        map.put("field", key);
                        map.put("oldValue", value1);
                    }
                    return map;
                }).toList();

        Formatter formatter = new Formatter(format);

        return formatter.formatText(list);
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

    private static Map<String, Object> getMapFromFile(String filePath) throws IOException {
        String file = Files.readString(Path.of(filePath));
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(file, new TypeReference<Map<String, Object>>() {
        });

    }
}
