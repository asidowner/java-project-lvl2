package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Plain implements FormatterDriver {
    private final String patternAdded = "Property '%s' was added with value: %s";
    private final String patternRemoved = "Property '%s' was removed";
    private final String patternChanged = "Property '%s' was updated. From %s to %s";
    private final String patternUnchanged = "";

    private String formatValue(Object value) {
        if (value.equals("null") || value.getClass().equals(Integer.class) || value.getClass().equals(Boolean.class)) {
            return value.toString();
        } else if (value.getClass().equals(String.class)) {
            return "'%s'".formatted(value);
        } else {
            return "[complex value]";
        }
    }

    @Override
    public String formatText(List<Map<String, Object>> list) {
        return list.stream()
                .map(line -> {
                    Object status = line.get("status");
                    Object field = line.get("field");

                    if (status.equals("added")) {
                        return patternAdded.formatted(field, formatValue(line.get("newValue")));
                    } else if (status.equals("removed")) {
                        return patternRemoved.formatted(field);
                    } else if (status.equals("changed")) {
                        return patternChanged.formatted(field, formatValue(line.get("oldValue")),
                                formatValue(line.get("newValue")));
                    } else if (status.equals("unchanged")) {
                        return patternUnchanged;
                    } else {
                        throw new RuntimeException("Unknown status for diff");
                    }
                })
                .filter(x -> !x.equals(""))
                .collect(Collectors.joining("\n"));
    }
}

