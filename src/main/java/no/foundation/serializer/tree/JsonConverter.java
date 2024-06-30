package no.foundation.serializer.tree;

import java.lang.reflect.Field;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import no.foundation.serializer.exceptions.JsonException;

final class JsonConverter {

    JsonNode convert(Object value) {
        if (isBasicType(value)) {
            return new JsonValue<>(value);
        } else if (value instanceof Collection<?> collection) {
            return convertCollection(collection);
        } else if (value instanceof Map<?, ?> map) {
            return convertMap(map);
        }
        return convertObject(value);
    }

    private JsonObject convertObject(Object value) {
        try {
            JsonObject object = new JsonObject();
            Field[] fields = value.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String key = field.getName();
                object.put(key, convert(field.get(value)));
            }
            return object;
        } catch (IllegalAccessException e) {
            throw new JsonException("Unable to convert value type to object, cause: ", e);
        }
    }

    private JsonObject convertMap(Map<?, ?> map) {
        JsonObject obj = new JsonObject();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            obj.put(key, convert(value));
        }
        return obj;
    }

    private JsonArray convertCollection(Collection<?> collection) {
        JsonArray array = new JsonArray();
        for (Object value : collection) {
            array.add(convert(value));
        }
        return array;
    }

    private boolean isBasicType(Object value) {
        return value == null
                || value instanceof String
                || value instanceof Number
                || value instanceof Boolean
                || value instanceof Date
                || value instanceof TimeZone
                || value instanceof Temporal;
    }
}
