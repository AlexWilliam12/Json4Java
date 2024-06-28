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
	if (value == null) {
	   return null;
	} else if (value instanceof Number || value instanceof Boolean) {
	   return value.toString();
	}
	return "\"%s\"".formatted(value);
    }
}
