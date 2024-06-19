package no.foundation.serializer.exceptions;

/**
 * Exception thrown to indicate an error during object conversion.
 */
public final class ObjectConverterException extends RuntimeException {
    /**
     * Constructs an {@code ObjectConverterException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public ObjectConverterException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an {@code ObjectConverterException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ObjectConverterException(String message) {
        super(message);
    }
}
