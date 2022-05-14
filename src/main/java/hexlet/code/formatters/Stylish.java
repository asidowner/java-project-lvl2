package hexlet.code.formatters;


public final class Stylish implements FormatterDriver {
    private static final int INDENT = 3;

    private String getPattern(String diff, String key, Object value) {
        return "%s%s %s: %s\n".formatted(" ".repeat(INDENT), diff, key, value);
    }

    @Override
    public String returnAddedLine(String key, Object value) {
        return getPattern("+", key, value);
    }

    @Override
    public String returnRemovedLine(String key, Object value) {
        return getPattern("-", key, value);
    }

    @Override
    public String returnUnchangedLine(String key, Object value) {
        return getPattern(" ", key, value);
    }

    @Override
    public String returnChangedLine(String key, Object value1, Object value2) {
        return returnRemovedLine(key, value1) + returnAddedLine(key, value2);
    }

    @Override
    public String returnParagraph(String text) {
        return "{\n%s}".formatted(text);
    }
}
