package no.foundation.serializer.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A sealed interface representing a node in a JSON structure. Implementing
 * classes must be one of: JsonValue, JsonArray, or JsonObject.
 */
public sealed interface JsonNode permits JsonValue, JsonArray, JsonObject {

    /**
     * Returns the original Java object representation of this JSON node.
     * Depending on the implementing class, this method returns: - For
     * JsonValue: the wrapped value. - For JsonArray: a List<Object> containing
     * original types of elements. - For JsonObject: a Map<String, Object>
     * containing original types of values.
     *
     * @return the original Java object representation of this JSON node.
     */
    default Object getOriginalType() {
        return switch (this) {
            case JsonValue<?> value ->
                value.value();
            case JsonArray array -> {
                List<JsonNode> nodes = array.getValues();
                List<Object> list = new ArrayList<>(array.size());
                for (JsonNode node : nodes) {
                    list.add(node.getOriginalType());
                }
                yield list;
            }
            case JsonObject obj -> {
                Map<String, JsonNode> pairs = obj.getPairs();
                Map<String, Object> map = new LinkedHashMap<>(pairs.size());
                for (var pair : pairs.entrySet()) {
                    String key = pair.getKey();
                    JsonNode node = pair.getValue();
                    map.put(key, node.getOriginalType());
                }
                yield map;
            }
        };
    }
}
