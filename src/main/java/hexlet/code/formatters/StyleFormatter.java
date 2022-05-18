package hexlet.code.formatters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StyleFormatter {
    String formatText(List<Map<String, Object>> list) throws IOException;
}
