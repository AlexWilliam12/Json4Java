package no.foundation.serializer;

import no.foundation.serializer.annotations.JsonIgnore;
import no.foundation.serializer.exceptions.ObjectConverterException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;

final class ObjectConverter {

    @Contract("null, _ -> null")
    <T> T convert(Object src, Class<T> type) {
        if (src == null) {
            return null;
        }
        if (type.isInstance(src)) {
            return type.cast(src);
        } else if (src instanceof Map<?, ?> map) {
            return convertMap(map, type);
        } else if (src instanceof Number number) {
            return type.cast(ObjectTypeProvider.convertNumber(number, type));
        } else if (ObjectTypeProvider.isTemporal(type)) {
            return type.cast(ObjectTypeProvider.convertTemporal(type, src));
        } else if (src instanceof Collection<?> collection) {
            return convertCollection(collection, type);
        } else {
            throw new ObjectConverterException("Unsupported type: " + type);
        }
    }

    private <T> T convertMap(@NotNull Map<?, ?> map, Class<T> type) {
        if (map.isEmpty() && type.isInstance(Map.class)) {
            return type.cast(Map.of());
        } else {
            Map<Object, Object> instance = ObjectTypeProvider.getMapInstance(type);
            if (instance != null) {
                instance.putAll(map);
                return type.cast(instance);
            } else {
                return type.isRecord()
                        ? convertToRecord(map, type)
                        : convertToPojo(map, type);
            }
        }
    }

    private <T> @NotNull T convertToPojo(Map<?, ?> map, Class<T> type) {
        try {
            Constructor<T> constructor = type.getConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                JsonIgnore annotation = field.getAnnotation(JsonIgnore.class);
                if (isIgnoredField(annotation)) continue;
                String name = field.getName();
                if (map.containsKey(name)) {
                    Object value = map.get(name);
                    Class<?> t = field.getType();
                    field.set(instance, convert(value, t));
                    if (value instanceof Collection<?> collection) {
                        Type genericType = field.getGenericType();
                        field.set(instance, convertCollection(collection, t, getGenericType(genericType)));
                    } else {
                        field.set(instance, convert(value, t));
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> @NotNull T convertToRecord(Map<?, ?> map, Class<T> type) {
        try {
            RecordComponent[] components = type.getRecordComponents();
            Class<?>[] types = new Class<?>[components.length];
            Object[] args = new Object[components.length];
            for (int i = 0; i < components.length; i++) {
                JsonIgnore annotation = components[i].getAnnotation(JsonIgnore.class);
                if (isIgnoredField(annotation)) continue;
                String name = components[i].getName();
                Class<?> t = components[i].getType();
                types[i] = t;
                Object value = map.get(name);
                if (value instanceof Collection<?> collection) {
                    Type genericType = components[i].getGenericType();
                    args[i] = convertCollection(collection, t, getGenericType(genericType));
                } else {
                    args[i] = convert(value, t);
                }
            }
            Constructor<T> constructor = type.getConstructor(types);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T convertCollection(Collection<?> collection, Class<T> type) {
        Collection<Object> list = ObjectTypeProvider.getCollectionInstance(type);
        if (list == null) {
            throw new ObjectConverterException("Invalid collection type provided");
        }
        for (Object value : collection) {
            Object converted = convert(value, value.getClass());
            list.add(converted);
        }
        return type.cast(list);
    }

    private <T> T convertCollection(Collection<?> collection, Class<T> listType, Class<?> elementType) {
        Collection<Object> list = ObjectTypeProvider.getCollectionInstance(listType);
        if (list == null) {
            throw new ObjectConverterException("Invalid collection type provided");
        }
        for (Object value : collection) {
            Object converted = convert(value, elementType);
            list.add(converted);
        }
        return listType.cast(list);
    }

    private Class<?> getGenericType(Type type) {
        Class<?> result = null;
        if (type instanceof ParameterizedType parameterizedType) {
            result = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
        return result;
    }

    private boolean isIgnoredField(JsonIgnore annotation) {
        return annotation != null && annotation.onlyDecoder();
    }
}
