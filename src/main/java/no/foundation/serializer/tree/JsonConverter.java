package no.foundation.serializer.tree;

import no.foundation.serializer.ObjectTypeProvider;
import no.foundation.serializer.exceptions.JsonException;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

class JsonConverter {

    JsonNode convert(Object value) {
        if (ObjectTypeProvider.isBasicType(value)) {
            return new JsonValue<>(value);
        } else if (value instanceof Collection<?> collection) {
            return convertCollection(collection);
        } else if (value instanceof Map<?, ?> map) {
            return convertMap(map);
        } else {
            throw new JsonException("");
        }
    }

    private @NotNull JsonObject convertMap(@NotNull Map<?, ?> map) {
        JsonObject obj = new JsonObject();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            obj.put(key, convert(value));
        }
        return obj;
    }

    private @NotNull JsonArray convertCollection(@NotNull Collection<?> collection) {
        JsonArray array = new JsonArray();
        for (Object value : collection) {
            array.add(convert(value));
        }
        return array;
    }
}
