package hexlet.code;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Tree {

    public static List<Map<String, Object>> build(Map<String, Object> firstData, Map<String, Object> secondData) {
        return Stream.concat(firstData.keySet().stream(), secondData.keySet().stream())
                .distinct()
                .sorted(Comparator.comparing(String::format))
                .map(key -> {
                    Object value1 = Optional.ofNullable(firstData.get(key)).orElse("null");
                    Object value2 = Optional.ofNullable(secondData.get(key)).orElse("null");

                    if (firstData.containsKey(key) && secondData.containsKey(key)) {
                        if (value1.equals(value2)) {
                            return Map.of("field", key, "status", "unchanged", "value", value1);
                        } else {
                            return Map.of("field", key, "status", "changed", "oldValue", value1,
                                    "newValue", value2);
                        }
                    } else if (secondData.containsKey(key)) {
                        return Map.of("field", key, "status", "added", "newValue", value2);
                    } else {
                        return Map.of("field", key, "status", "removed", "oldValue", value1);
                    }
                }).toList();
    }
}
