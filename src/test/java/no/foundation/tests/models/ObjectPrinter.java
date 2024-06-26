package no.foundation.tests.models;

import java.lang.reflect.Field;

public class ObjectPrinter {

    public static String print(Object obj) {
        Class<?> c = obj.getClass();
        String name = c.getSimpleName();

        StringBuilder sb = new StringBuilder(name);
        sb.append('[');

        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            sb.append(field.getName());
            sb.append('=');
            try {
                sb.append(field.get(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            sb.append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
        return sb.toString();
    }

    private ObjectPrinter() {
    }
}
