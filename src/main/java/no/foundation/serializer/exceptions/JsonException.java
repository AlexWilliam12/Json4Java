package no.foundation.serializer.exceptions;

/**
 * Exception thrown to indicate an error related to JSON processing.
 */
public final class JsonException extends RuntimeException {
    /**
     * Constructs a {@code JsonException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public JsonException(String message) {
        super(message);
    }
}
