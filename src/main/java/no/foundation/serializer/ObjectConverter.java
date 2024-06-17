package no.foundation.serializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ObjectConverter {

    public <T> T convert(Object value, Class<T> type) {
        if (value == null) {
            return null;
        }
        if (type.equals(value.getClass())) {
            return type.cast(value);
        } else if (value instanceof Map<?, ?> map) {
            return convertMap(map, type);
        } else if (value instanceof Number number) {
            return type.cast(ObjectTypeProvider.convertNumber(number, type));
        } else if (ObjectTypeProvider.isTemporal(type)) {
            return type.cast(ObjectTypeProvider.convertTemporal(type, value));
        } else if (value instanceof Collection<?> collection) {
            return type.cast(convertCollection(collection, type));
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
                    field.set(instance, convert(value, field.getType()));
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
                args[i] = convert(map.get(name), type);
            }
            Constructor<T> constructor = c.getConstructor(types);
            constructor.setAccessible(true);
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T convertCollection(Collection<?> collection, Class<T> type) {
        List<Object> list = new ArrayList<>();
        for (Object value : collection) {
            list.add(convert(value, value.getClass()));
        }
        return type.cast(list);
    }
}
