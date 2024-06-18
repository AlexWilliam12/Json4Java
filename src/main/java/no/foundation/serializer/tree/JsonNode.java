package no.foundation.serializer.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JsonNode {
    public Object getOriginalType() {
        return switch (this) {
            case JsonValue<?> value -> value.getValue();
            case JsonArray array -> {
                List<JsonNode> values = array.getValues();
                List<Object> list = new ArrayList<>();
                for (JsonNode value : values) {
                    list.add(value.getOriginalType());
                }
                yield list;
            }
            case JsonObject object -> {
                Map<String, JsonNode> pairs = object.getPairs();
                Map<String, Object> map = new HashMap<>();
                for (var entry : pairs.entrySet()) {
                    String key = entry.getKey();
                    JsonNode value = entry.getValue();
                    map.put(key, value.getOriginalType());
                }
                yield map;
            }
            default -> null;
        };
    }
}
