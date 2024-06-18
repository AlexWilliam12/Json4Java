package no.foundation.serializer.tree;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject extends JsonNode implements Map<String, JsonNode> {

    private final Map<String, JsonNode> pairs;

    public JsonObject() {
        this.pairs = new LinkedHashMap<>();
    }

    public Map<String, JsonNode> getPairs() {
        return pairs;
    }

    @Override
    public int size() {
        return pairs.size();
    }

    @Override
    public boolean isEmpty() {
        return pairs.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return pairs.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return pairs.containsValue(value);
    }

    @Override
    public JsonNode get(Object key) {
        return pairs.get(key);
    }

    @Override
    public JsonNode put(String key, JsonNode value) {
        return pairs.put(key, value);
    }

    @Override
    public JsonNode remove(Object key) {
        return pairs.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends JsonNode> map) {
        pairs.putAll(map);
    }

    @Override
    public void clear() {
        pairs.clear();
    }

    @Override
    public Set<String> keySet() {
        return pairs.keySet();
    }

    @Override
    public Collection<JsonNode> values() {
        return pairs.values();
    }

    @Override
    public Set<Entry<String, JsonNode>> entrySet() {
        return pairs.entrySet();
    }

    @Override
    public String toString() {
        if (pairs.isEmpty()) {
            return "{}";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (var entry : pairs.entrySet()) {
            builder.append('"');
            builder.append(entry.getKey());
            builder.append("\": ");
            builder.append(entry.getValue());
            builder.append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append("}");
        return builder.toString();
    }
}
