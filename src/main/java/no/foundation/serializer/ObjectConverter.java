package no.foundation.serializer;

import no.foundation.serializer.tree.JsonNode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Utility class that provides methods to convert objects between various types.
 * This class is final and cannot be subclassed.
 */
final class ObjectConverter {

    /**
     * Converts an object to the specified target type.
     * If the value is null, the result is null.
     * Conversion logic varies based on the type of the value and the target type.
     *
     * @param value the object to convert.
     * @param type  the target type class.
     * @param <T>   the target type.
     * @return the converted object of the target type, or null if the value is null.
     */
    @Contract("null, _ -> null")
    @SuppressWarnings({"unchecked"})
    <T> T convert(Object value, Class<T> type) {
        T result = null;
        if (value != null) {
            if (value instanceof JsonNode node) {
                value = node.getOriginalType();
            }
            if (type.equals(value.getClass())) {
                result = (T) value;
            } else if (value instanceof Map<?, ?> map) {
                result = convertMap(map, type);
            } else if (value instanceof Number number) {
                result = (T) ObjectTypeProvider.convertNumber(number, type);
            } else if (ObjectTypeProvider.isTemporal(type)) {
                result = (T) ObjectTypeProvider.convertTemporal(type, value);
            } else if (value instanceof Collection<?> collection) {
                result = convertCollection(collection, type);
            }
        }
        return result;
    }

    /**
     * Converts a map to an instance of the specified type.
     * The type can be a record or a POJO.
     *
     * @param map the map to convert.
     * @param c   the target type class.
     * @param <T> the target type.
     * @return the converted instance of the target type.
     */
    private <T> T convertMap(final Map<?, ?> map, @NotNull final Class<T> c) {
        return c.isRecord()
                ? convertToRecord(map, c)
                : convertToPojo(map, c);
    }

    /**
     * Converts a map to a POJO (Plain Old Java Object).
     * This method creates a new instance of the target type and sets its fields
     * using the values from the map.
     *
     * @param map the map to convert.
     * @param c   the target type class.
     * @param <T> the target type.
     * @return the converted POJO instance.
     */
    private <T> @NotNull T convertToPojo(final Map<?, ?> map, final Class<T> c) {
        @NotNull T result;
        try {
            Constructor<T> constructor = c.getConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                if (map.containsKey(name)) {
                    Object value = map.get(name);
                    if (value instanceof Collection) {
                        field.set(instance, convert(value, getGenericType(field.getGenericType())));
                    } else {
                        field.set(instance, convert(value, field.getType()));
                    }
                }
            }
            result = instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Converts a map to a record.
     * This method creates a new instance of the target record type using its constructor
     * and the values from the map.
     *
     * @param map the map to convert.
     * @param c   the target type class.
     * @param <T> the target type.
     * @return the converted record instance.
     */
    private <T> @NotNull T convertToRecord(final Map<?, ?> map, final Class<T> c) {
        @NotNull T result;
        try {
            RecordComponent[] components = c.getRecordComponents();
            Class<?>[] types = new Class<?>[components.length];
            Object[] args = new Object[components.length];
            for (int i = 0; i < components.length; i++) {
                String name = components[i].getName();
                Class<?> type = components[i].getType();
                types[i] = type;
                Object value = map.get(name);
                if (value instanceof Collection) {
                    Type genericType = components[i].getGenericType();
                    args[i] = convert(value, getGenericType(genericType));
                } else {
                    args[i] = convert(value, type);
                }
            }
            Constructor<T> constructor = c.getConstructor(types);
            constructor.setAccessible(true);
            result = constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Converts a collection to a list of the specified target type.
     * This method iterates over the collection's elements and converts each element.
     *
     * @param collection the collection to convert.
     * @param type       the target type class.
     * @param <T>        the target type.
     * @return the converted list.
     */
    @SuppressWarnings("unchecked")
    private <T> @NotNull T convertCollection(@NotNull final Collection<?> collection, final Class<T> type) {
        List<Object> list = new ArrayList<>();
        for (Object value : collection) {
            Object converted = convert(value, type);
            list.add(converted);
        }
        return (T) list;
    }

    /**
     * Gets the generic type from a parameterized type.
     * If the type is not parameterized, the result is null.
     *
     * @param type the type to get the generic type from.
     * @return the generic type class, or null if the type is not parameterized.
     */
    @Contract("null -> null")
    private Class<?> getGenericType(final Type type) {
        Class<?> result = null;
        if (type instanceof ParameterizedType parameterizedType) {
            result = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
        return result;
    }
}
