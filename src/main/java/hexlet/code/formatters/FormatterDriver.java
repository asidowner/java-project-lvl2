package hexlet.code.formatters;

public interface FormatterDriver {
    String returnAddedLine(String key, Object value);

    String returnRemovedLine(String key, Object value);
    String returnUnchangedLine(String key, Object value);
    String returnChangedLine(String key, Object value1, Object value2);

    String returnParagraph(String text);
}
