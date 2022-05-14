package hexlet.code.formatters;

public final class Plain implements FormatterDriver {
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
    public String returnAddedLine(String key, Object value) {
        return "Property '%s' was added with value: %s\n".formatted(key, formatValue(value));
    }

    @Override
    public String returnRemovedLine(String key, Object value) {
        return "Property '%s' was removed\n".formatted(key);
    }

    @Override
    public String returnUnchangedLine(String key, Object value) {
        return "";
    }

    @Override
    public String returnChangedLine(String key, Object value1, Object value2) {
        return "Property '%s' was updated. From %s to %s\n".formatted(key, formatValue(value1), formatValue(value2));
    }

    @Override
    public String returnParagraph(String text) {
        return "\n%s".formatted(text);
    }
}

