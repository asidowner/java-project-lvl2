package hexlet.code;

import hexlet.code.formatters.FormatterDriver;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class Formatter {
    private static FormatterDriver formatterDriver;

    public Formatter(String format) throws IOException {
        if (format.equals("stylish")) {
            formatterDriver = new Stylish();
        } else if (format.equals("plain")) {
            formatterDriver = new Plain();
        } else {
            throw new IOException("Unknown format for result set");
        }
    }

    public String formatText(List<Map<String, Object>> list) {
        return formatterDriver.formatText(list);
    }
}
