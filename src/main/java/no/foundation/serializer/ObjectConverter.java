package no.foundation.serializer;

import no.foundation.serializer.tree.JsonNode;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class ObjectConverter {

    @SuppressWarnings({"unchecked"})
    <T> T convert(Object value, Class<T> type) {
        if (value == null) {
            return null;
        }
        if (value instanceof JsonNode node) {
            value = node.getOriginalType();
        }
        if (type.equals(value.getClass())) {
            return (T) value;
        } else if (value instanceof Map<?, ?> map) {
            return convertMap(map, type);
        } else if (value instanceof Number number) {
            return (T) ObjectTypeProvider.convertNumber(number, type);
        } else if (ObjectTypeProvider.isTemporal(type)) {
            return (T) ObjectTypeProvider.convertTemporal(type, value);
        } else if (value instanceof Collection<?> collection) {
            return convertCollection(collection, type);
        }
        return null;
    }

    private <T> T convertMap(Map<?, ?> map, Class<T> c) {
        return c.isRecord()
                ? convertToRecord(map, c)
                : convertToPojo(map, c);
    }

    private <T> T convertToPojo(Map<?, ?> map, Class<T> c) {
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
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T convertToRecord(Map<?, ?> map, Class<T> c) {
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
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T convertCollection(Collection<?> collection, Class<T> type) {
        List<Object> list = new ArrayList<>();
        for (Object value : collection) {
            Object converted = convert(value, type);
            list.add(converted);
        }
        return (T) list;
    }

    private Class<?> getGenericType(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            return (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }
}
