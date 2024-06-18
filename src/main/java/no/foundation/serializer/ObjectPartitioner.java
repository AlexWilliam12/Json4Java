package no.foundation.serializer;

import no.foundation.serializer.tree.JsonArray;
import no.foundation.serializer.tree.JsonNode;
import no.foundation.serializer.tree.JsonObject;
import no.foundation.serializer.tree.JsonValue;

import java.lang.reflect.Field;
import java.util.Collection;

class ObjectPartitioner {

    JsonNode partition(Object value, boolean isRoot) {
        if (ObjectTypeProvider.isBasicType(value)) {
            return partitionValue(value);
        } else if (value instanceof Collection<?> collection) {
            return partitionList(collection, isRoot);
        }
        return partitionObject(value, isRoot);
    }

    private JsonObject partitionObject(Object obj, boolean isRoot) {
        Field[] fields = obj.getClass().getDeclaredFields();
        JsonObject object = new JsonObject(isRoot);
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String key = field.getName();
                Object value = field.get(obj);
                object.put(key, partition(value, false));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return object;
    }

    private JsonArray partitionList(Collection<?> collection, boolean isRoot) {
        JsonArray array = new JsonArray(isRoot);
        for (Object value : collection) {
            array.add(partition(value, false));
        }
        return array;
    }

    private JsonValue<?> partitionValue(Object value) {
        return new JsonValue<>(value);
    }
}
