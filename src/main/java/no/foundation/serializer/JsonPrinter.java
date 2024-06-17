package no.foundation.serializer;

import java.lang.reflect.Field;
import java.util.Collection;

class JsonPrinter {

    private final boolean formatted;

    JsonPrinter(boolean formatted) {
        this.formatted = formatted;
    }

    public String print(Object value) {
        if (value instanceof Collection<?> collection) {
            return printArray(collection);
        } else if (ObjectTypeProvider.isBasicType(value)) {
            return printValue(value);
        }
        return printObject(value);
    }

    private String printValue(Object value) {
        StringBuilder sb = new StringBuilder();
        if (ObjectTypeProvider.isString(value) || ObjectTypeProvider.isTemporal(value)) {
            sb.append('"');
            sb.append(value);
            sb.append('"');
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    private String printObject(Object value) {
        try {
            Field[] fields = value.getClass().getDeclaredFields();
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            for (Field field : fields) {
                field.setAccessible(true);
                sb.append('"');
                sb.append(field.getName());
                sb.append('"');
                sb.append(':');
                sb.append(print(field.get(value)));
                sb.append(',');
            }
            sb.replace(sb.length() - 1, sb.length(), "}");
            return sb.toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String printArray(Collection<?> collection) {
        if (collection.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (Object value : collection) {
            sb.append(print(value));
            sb.append(',');
        }
        sb.replace(sb.length() - 1, sb.length(), "]");
        return sb.toString();
    }
}
