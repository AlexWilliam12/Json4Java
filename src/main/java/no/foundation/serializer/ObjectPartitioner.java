package no.foundation.serializer;

import no.foundation.serializer.annotations.JsonIgnore;
import no.foundation.serializer.tree.JsonArray;
import no.foundation.serializer.tree.JsonNode;
import no.foundation.serializer.tree.JsonObject;
import no.foundation.serializer.tree.JsonValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Utility class that provides methods to partition an object into a JSON representation.
 * This class is final and cannot be subclassed.
 */
final class ObjectPartitioner {

    /**
     * Partitions the given object into a corresponding JSON node.
     * The partitioning logic varies based on whether the object is of a basic type,
     * a collection, or a complex object.
     *
     * @param value the object to partition.
     * @return the JSON node representing the partitioned object.
     */
    JsonNode partition(Object value) {
        if (ObjectTypeProvider.isBasicType(value)) {
            return partitionValue(value);
        } else if (value instanceof Collection<?> collection) {
            return partitionList(collection);
        } else if (value instanceof Map<?, ?> map) {
            return partitionMap(map);
        }
        return partitionObject(value);
    }

    private @NotNull JsonObject partitionMap(@NotNull Map<?, ?> map) {
        // TODO: documentation and test
        JsonObject obj = new JsonObject();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            obj.put(key, partition(value));
        }
        return obj;
    }

    /**
     * Partitions a complex object into a JSON object.
     * This method iterates over the object's fields and partitions each field's value.
     *
     * @param obj the complex object to partition.
     * @return the JSON object representing the partitioned fields.
     */
    private @NotNull JsonObject partitionObject(@NotNull Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        JsonObject object = new JsonObject();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                JsonIgnore annotation = field.getAnnotation(JsonIgnore.class);
                if (isIgnoredField(annotation)) continue;
                String key = field.getName();
                Object value = field.get(obj);
                object.put(key, partition(value));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return object;
    }

    /**
     * Partitions a collection into a JSON array.
     * This method iterates over the collection's elements and partitions each element.
     *
     * @param collection the collection to partition.
     * @return the JSON array representing the partitioned elements.
     */
    private @NotNull JsonArray partitionList(@NotNull Collection<?> collection) {
        JsonArray array = new JsonArray();
        for (Object value : collection) {
            array.add(partition(value));
        }
        return array;
    }

    /**
     * Partitions a basic value into a JSON value.
     * This method wraps the given value into a JSON value object.
     *
     * @param value the basic value to partition.
     * @return the JSON value representing the partitioned value.
     */
    @Contract("_ -> new")
    private @NotNull JsonValue<?> partitionValue(Object value) {
        return new JsonValue<>(value);
    }

    private boolean isIgnoredField(JsonIgnore annotation) {
        return annotation != null && annotation.onlyEncoder();
    }
}
