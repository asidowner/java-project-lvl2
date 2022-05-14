package hexlet.code;

import hexlet.code.formatters.FormatterDriver;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;

public final class Formatter {
    private String  paragraph = "";
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

    public void setUnchangedLine(String key, Object value) {
        paragraph = paragraph + formatterDriver.returnUnchangedLine(key, value);
    }
    public void setChangedLine(String key, Object value1, Object value2) {
        paragraph = paragraph + formatterDriver.returnChangedLine(key, value1, value2);
    }
    public void setRemovedLine(String key, Object value) {
        paragraph = paragraph + formatterDriver.returnRemovedLine(key, value);
    }
    public void setAddedLine(String key, Object value) {
        paragraph = paragraph + formatterDriver.returnAddedLine(key, value);
    }

    public String getParagraph() {
        return formatterDriver.returnParagraph(paragraph);
    }
}
