package no.foundation.serializer.tree;

import java.time.temporal.Temporal;
import java.util.Date;
import java.util.TimeZone;

public class JsonValue<T> extends JsonNode {
    private final T value;

    public JsonValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value instanceof Temporal
                || value instanceof Date
                || value instanceof TimeZone
                || value instanceof String
        ) {
            return "\"%s\"".formatted(value.toString());
        }
        return value == null ? null : value.toString();
    }
}
