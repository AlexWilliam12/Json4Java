package no.foundation.serializer.tree;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class representing a JSON object node.
 * This class extends JsonNode and implements the Map interface with String keys and JsonNode values.
 */
public final class JsonObject implements Map<String, JsonNode>, JsonNode {

    private final Map<String, JsonNode> pairs;

    /**
     * Constructs a new empty JsonObject.
     * Uses a LinkedHashMap to maintain insertion order of key-value pairs.
     */
    public JsonObject() {
        this.pairs = new LinkedHashMap<>();
    }

    /**
     * Returns the internal map of key-value pairs in this JsonObject.
     *
     * @return the map of key-value pairs in this JsonObject.
     */
    @Contract(pure = true)
    public Map<String, JsonNode> getPairs() {
        return pairs;
    }

    /**
     * Returns the number of key-value pairs in this JsonObject.
     *
     * @return the number of key-value pairs in this JsonObject.
     */
    @Contract(pure = true)
    @Override
    public int size() {
        return pairs.size();
    }

    /**
     * Checks if this JsonObject is empty (contains no key-value pairs).
     *
     * @return true if this JsonObject is empty, false otherwise.
     */
    @Contract(pure = true)
    @Override
    public boolean isEmpty() {
        return pairs.isEmpty();
    }

    /**
     * Checks if this JsonObject contains the specified key.
     *
     * @param key the key to check for presence in this JsonObject.
     * @return true if this JsonObject contains the specified key, false otherwise.
     */
    @Contract(pure = true)
    @Override
    public boolean containsKey(Object key) {
        return pairs.containsKey(key);
    }

    /**
     * Checks if this JsonObject contains the specified value.
     *
     * @param value the value to check for presence in this JsonObject.
     * @return true if this JsonObject contains the specified value, false otherwise.
     */
    @Contract(pure = true)
    @Override
    public boolean containsValue(Object value) {
        return pairs.containsValue(value);
    }

    /**
     * Retrieves the JsonNode associated with the specified key from this JsonObject.
     *
     * @param key the key whose associated value is to be returned.
     * @return the JsonNode associated with the specified key, or null if no mapping exists for the key.
     */
    @Override
    public JsonNode get(Object key) {
        return pairs.get(key);
    }

    /**
     * Associates the specified value with the specified key in this JsonObject.
     *
     * @param key   the key with which the specified value is to be associated.
     * @param value the value to be associated with the specified key.
     * @return the previous value associated with the key, or null if there was no mapping for the key.
     */
    @Override
    public JsonNode put(String key, JsonNode value) {
        return pairs.put(key, value);
    }

    /**
     * Removes the mapping for the specified key from this JsonObject if present.
     *
     * @param key the key whose mapping is to be removed from the JsonObject.
     * @return the JsonNode previously associated with the key, or null if there was no mapping for the key.
     */
    @Override
    public JsonNode remove(Object key) {
        return pairs.remove(key);
    }

    /**
     * Copies all the mappings from the specified map to this JsonObject.
     *
     * @param map the map whose mappings are to be added to this JsonObject.
     */
    @Override
    public void putAll(@NotNull Map<? extends String, ? extends JsonNode> map) {
        pairs.putAll(map);
    }

    /**
     * Removes all the mappings from this JsonObject.
     */
    @Override
    public void clear() {
        pairs.clear();
    }

    /**
     * Returns a set view of the keys contained in this JsonObject.
     *
     * @return a set view of the keys contained in this JsonObject.
     */
    @Contract(pure = true)
    @Override
    public @NotNull Set<String> keySet() {
        return pairs.keySet();
    }

    /**
     * Returns a collection view of the values contained in this JsonObject.
     *
     * @return a collection view of the values contained in this JsonObject.
     */
    @Contract(pure = true)
    @Override
    public @NotNull Collection<JsonNode> values() {
        return pairs.values();
    }

    /**
     * Returns a set view of the mappings contained in this JsonObject.
     *
     * @return a set view of the mappings contained in this JsonObject.
     */
    @Contract(pure = true)
    @Override
    public @NotNull Set<Entry<String, JsonNode>> entrySet() {
        return pairs.entrySet();
    }

    /**
     * Returns the string representation of this JsonObject.
     *
     * @return the string representation of this JsonObject.
     */
    @Override
    public @NotNull String toString() {
        if (pairs.isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Entry<String, JsonNode> entry : pairs.entrySet()) {
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            sb.append('"');
            sb.append(key);
            sb.append("\": ");
            sb.append(value);
            sb.append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), "}");
        return sb.toString();
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull JsonObjectBuilder builder() {
        return new JsonObjectBuilder();
    }

    public static final class JsonObjectBuilder {
        private final Map<String, Object> values;

        @Contract(pure = true)
        private JsonObjectBuilder() {
            this.values = new LinkedHashMap<>();
        }

        public JsonObjectBuilder put(String key, Object value) {
            values.put(key, value);
            return this;
        }

        public @NotNull JsonObject build() {
            JsonConverter converter = new JsonConverter();
            JsonObject obj = new JsonObject();
            for (Entry<String, Object> entry : values.entrySet()) {
                obj.put(entry.getKey(), converter.convert(entry.getValue()));
            }
            return obj;
        }
    }
}
