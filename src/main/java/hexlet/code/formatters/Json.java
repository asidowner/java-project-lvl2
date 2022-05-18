package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class Json implements StyleFormatter {
    @Override
    public String formatText(List<Map<String, Object>> list) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setConfig(objectMapper.getSerializationConfig()
                .with(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS));
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new IOException("Problem on format processing");
        }
    }
}
