package hexlet.code.formatters;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Stylish implements StyleFormatter {
    private final String patternAdded = "  + %s: %s";
    private final String patternRemoved = "  - %s: %s";
    private final String patternChanged = "  - %s: %s\n  + %s: %s";
    private final String patternUnchanged = "    %s: %s";

    private String formatValue(Object value) {
        return value.toString();
    }
    @Override
    public String formatText(List<Map<String, Object>> list) {
        return list.stream()
                .map(line -> {
                    Object status = line.get("status");
                    Object field = line.get("field");

                    if (status.equals("added")) {
                        return patternAdded.formatted(field,     formatValue(line.get("newValue")));
                    } else if (status.equals("removed")) {
                        return patternRemoved.formatted(field,   formatValue(line.get("oldValue")));
                    } else if (status.equals("changed")) {
                        return patternChanged.formatted(field,   formatValue(line.get("oldValue")),
                                field, formatValue(line.get("newValue")));
                    } else if (status.equals("unchanged")) {
                        return patternUnchanged.formatted(field, formatValue(line.get("value")));
                    } else {
                        throw new RuntimeException("Unknown status for diff");
                    }
                }).collect(Collectors.joining("\n", "{\n", "\n}"));
    }
}
