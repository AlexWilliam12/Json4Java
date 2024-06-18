package no.foundation.serializer.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JsonNode {
    protected final boolean isRoot;

    protected JsonNode(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public Object getOriginalType(Object obj) {
        if (obj instanceof JsonValue<?> value) {
            return value.getValue();
        } else if (obj instanceof JsonArray array) {
            List<JsonNode> values = array.getValues();
            List<Object> list = new ArrayList<>();
            for (JsonNode value : values) {
                list.add(getOriginalType(value));
            }
            return list;
        } else if (obj instanceof JsonObject object) {
            Map<String, JsonNode> pairs = object.getPairs();
            Map<String, Object> map = new HashMap<>();
            for (var entry : pairs.entrySet()) {
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                map.put(key, getOriginalType(value));
            }
            return map;
        }
        return null;
    }
}
