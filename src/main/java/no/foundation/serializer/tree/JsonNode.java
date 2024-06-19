package no.foundation.serializer.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A sealed interface representing a node in a JSON structure.
 * Implementing classes must be one of: JsonValue, JsonArray, or JsonObject.
 */
public sealed interface JsonNode permits JsonValue, JsonArray, JsonObject {

    /**
     * Returns the original Java object representation of this JSON node.
     * Depending on the implementing class, this method returns:
     * - For JsonValue: the wrapped value.
     * - For JsonArray: a List<Object> containing original types of elements.
     * - For JsonObject: a Map<String, Object> containing original types of values.
     *
     * @return the original Java object representation of this JSON node.
     */
    default Object getOriginalType() {
        return switch (this) {
            case JsonValue<?> value -> value.value();
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
                Map<String, Object> map = new LinkedHashMap<>();
                for (var entry : pairs.entrySet()) {
                    String key = entry.getKey();
                    JsonNode value = entry.getValue();
                    map.put(key, value.getOriginalType());
                }
                yield map;
            }
        };
    }
}
