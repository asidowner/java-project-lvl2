package hexlet.code;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.Map;

public class Parser {
    public static Map<String, Object> parseContent(String content, String dataFormat) throws IOException {
        ObjectMapper objectMapper;

        if (dataFormat.equals("json")) {
            objectMapper = new ObjectMapper(new JsonFactory());
        } else if (dataFormat.matches("yml|yaml")) {
            objectMapper = new ObjectMapper(new YAMLFactory());
        } else {
            throw new IOException("Unsupported dataFormat");
        }

        return objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {
        });
    }
}
