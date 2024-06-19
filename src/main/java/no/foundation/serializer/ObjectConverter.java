package no.foundation.serializer;

import no.foundation.serializer.exceptions.ObjectConverterException;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Map;

final class ObjectConverter<T> {

    private final Class<T> target;

    ObjectConverter(Class<T> target) {
        this.target = target;
    }

    T convert(Object src) {
        return convert(src, target);
    }

    private <E> E convert(Object src, Class<E> type) {
        if (src == null) {
            return null;
        }
        if (type.isInstance(src)) {
            return type.cast(src);
        } else if (src instanceof Map<?, ?> map) {
            return convertMap(map, type);
        } else if (src instanceof Number number) {
            return type.cast(ObjectTypeProvider.convertNumber(number, type));
        } else if (ObjectTypeProvider.isTemporal(src)) {
            return type.cast(ObjectTypeProvider.convertTemporal(type, src));
        } else if (src instanceof Collection<?> collection) {
            return convertCollection(collection, type);
        } else {
            throw new ObjectConverterException("Unsupported type: " + type);
        }
    }

    private <E> E convertMap(Map<?, ?> map, Class<E> type) {
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

    private <E> E convertToPojo(final Map<?, ?> map, final Class<E> type) {
        try {
            Constructor<E> constructor = type.getConstructor();
            constructor.setAccessible(true);
            E instance = constructor.newInstance();
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                if (map.containsKey(name)) {
                    Object value = map.get(name);
                    field.set(instance, convert(value, field.getType()));
                    if (value instanceof Collection<?> collection) {
                        field.set(instance, convertCollection(collection, field.getType(), getGenericType(field.getGenericType())));
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

    private <E> E convertToRecord(final Map<?, ?> map, final Class<E> type) {
        try {
            RecordComponent[] components = type.getRecordComponents();
            Class<?>[] types = new Class<?>[components.length];
            Object[] args = new Object[components.length];
            for (int i = 0; i < components.length; i++) {
                String name = components[i].getName();
                types[i] = components[i].getType();
                // TODO: Convert names to camel case
                Object value = map.get(name);
                if (value instanceof Collection<?> collection) {
                    Type genericType = components[i].getGenericType();
                    args[i] = convertCollection(collection, components[i].getType(), getGenericType(genericType));
                } else {
                    args[i] = convert(value, components[i].getType());
                }
            }
            Constructor<E> constructor = type.getConstructor(types);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <E> E convertCollection(final Collection<?> collection, final Class<E> type) {
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

    private <E> E convertCollection(final Collection<?> collection, final Class<E> listType, Class<?> elementType) {
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

    private Class<?> getGenericType(final Type type) {
        Class<?> result = null;
        if (type instanceof ParameterizedType parameterizedType) {
            result = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
        return result;
    }
}
