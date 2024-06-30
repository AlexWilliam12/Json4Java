package no.foundation.serializer;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import no.foundation.serializer.annotations.JsonIgnore;
import no.foundation.serializer.tree.JsonArray;
import no.foundation.serializer.tree.JsonNode;
import no.foundation.serializer.tree.JsonObject;
import no.foundation.serializer.tree.JsonValue;

final class JsonTreeAssembler {

    JsonNode assemble(Object value) {
        if (TypeProvider.isBasicType(value)) {
            return assembleValue(value);
        } else if (value instanceof Collection<?> collection) {
            return assembleList(collection);
        } else if (value instanceof Map<?, ?> map) {
            return assembleMap(map);
        }
        return assembleObject(value);
    }

    private JsonObject assembleMap(Map<?, ?> map) {
        JsonObject obj = new JsonObject();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            obj.put(key, assemble(value));
        }
        return obj;
    }

    private JsonObject assembleObject(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        JsonObject object = new JsonObject();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                JsonIgnore annotation = field.getAnnotation(JsonIgnore.class);
                if (isIgnoredField(annotation)) {
                    continue;
                }
                String key = field.getName();
                Object value = field.get(obj);
                object.put(key, assemble(value));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return object;
    }

    private JsonArray assembleList(Collection<?> collection) {
        JsonArray array = new JsonArray();
        for (Object value : collection) {
            array.add(assemble(value));
        }
        return array;
    }

    private JsonValue<?> assembleValue(Object value) {
        return new JsonValue<>(value);
    }

    private boolean isIgnoredField(JsonIgnore annotation) {
        return annotation != null && annotation.onlyEncoder();
    }
}
