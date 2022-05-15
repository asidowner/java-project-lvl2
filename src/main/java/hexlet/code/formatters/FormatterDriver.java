package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public interface FormatterDriver {
    String formatText(List<Map<String, Object>> list);
}
