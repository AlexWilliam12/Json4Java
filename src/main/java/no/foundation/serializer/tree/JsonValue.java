package no.foundation.serializer.tree;

import org.jetbrains.annotations.Nullable;

import java.time.temporal.Temporal;
import java.util.Date;
import java.util.TimeZone;

/**
 * A record representing a JSON value node.
 * This record implements JsonNode and holds a single value of type T.
 *
 * @param <T> the type of the value held by this JsonValue.
 */
public record JsonValue<T>(T value) implements JsonNode {

    /**
     * Returns the string representation of this JsonValue.
     *
     * @return the string representation of this JsonValue.
     */
    @Override
    public @Nullable String toString() {
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
