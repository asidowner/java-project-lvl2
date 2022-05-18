package hexlet.code;

import hexlet.code.formatters.StyleFormatter;
import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class Formatter {

    public static String formatText(List<Map<String, Object>> list, String format) throws IOException {
        StyleFormatter formatterDriver = switch (format) {
            case "stylish" -> new Stylish();
            case "plain" -> new Plain();
            case "json" -> new Json();
            default -> throw new IOException("Unknown format for result set");
        };

        return formatterDriver.formatText(list);
    }
}
