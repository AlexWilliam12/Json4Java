package no.foundation.serializer.exceptions;

/**
 * Exception thrown to indicate an error related to JSON processing.
 */
public final class JsonException extends RuntimeException {

    /**
     * Constructs a {@code JsonException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by
     * the {@link #getMessage()} method)
     */
    public JsonException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code JsonException} with the specified cause and a detail
     * message of (cause==null ? null : cause.toString()) (which typically
     * contains the class and detail message of cause). This constructor is
     * useful for exceptions that are little more than wrappers for other
     * throwables.
     *
     * @param throwable the cause (which is saved for later retrieval by the
     * {@link #getCause()} method). (A {@code null} value is permitted, and
     * indicates that the cause is nonexistent or unknown.)
     */
    public JsonException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructs a {@code JsonException} with the specified detail message and
     * cause.
     *
     * @param message the detail message (which is saved for later retrieval by
     * the {@link #getMessage()} method).
     * @param throwable the cause (which is saved for later retrieval by the
     * {@link #getCause()} method). (A {@code null} value is permitted, and
     * indicates that the cause is nonexistent or unknown.)
     */
    public JsonException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
