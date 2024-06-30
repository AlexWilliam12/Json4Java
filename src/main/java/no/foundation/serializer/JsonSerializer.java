package no.foundation.serializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import no.foundation.serializer.exceptions.JsonException;
import no.foundation.serializer.tree.JsonNode;

/**
 * Utility class that provides methods for encoding objects to JSON strings and
 * decoding JSON strings to objects
 */
public class JsonSerializer {

    private final JsonEncoder encoder;
    private final JsonDecoder decoder;

    /**
     * Constructs a new JsonSerializer with default encoder and decoder.
     */
    public JsonSerializer() {
        this.encoder = new JsonEncoder();
        this.decoder = new JsonDecoder();
    }

    /**
     * Encodes the given node tree to a JSON string.
     *
     * @param node the object to encode.
     * @return the JSON string representation of the object.
     */
    public synchronized String encode(JsonNode node) {
        return encoder.encode(Objects.requireNonNull(node));
    }

    /**
     * Encodes the given node tree to a JSON string with optional formatting.
     *
     * @param node the object to encode.
     * @param formatted whether the JSON string should be formatted.
     * @return the JSON string representation of the object.
     */
    public synchronized String encode(JsonNode node, boolean formatted) {
        return encoder.encode(Objects.requireNonNull(node), formatted);
    }

    /**
     * Encodes the given object to a JSON string.
     *
     * @param value the object to encode.
     * @return the JSON string representation of the object.
     */
    public synchronized String encode(Object value) {
        return encoder.encode(value);
    }

    /**
     * Encodes the given object to a JSON string with optional formatting.
     *
     * @param value the object to encode.
     * @param formatted whether the JSON string should be formatted.
     * @return the JSON string representation of the object.
     */
    public synchronized String encode(Object value, boolean formatted) {
        return encoder.encode(value, formatted);
    }

    /**
     * Decodes a JSON file to an object of the specified type.
     *
     * @param file the JSON file to decode.
     * @param c the target type class.
     * @param <T> the target type.
     * @return the decoded object.
     * @throws JsonException if an error occurs during decoding.
     * @throws IOException if an I/O error occurs.
     */
    public synchronized <T> T decode(File file, Class<T> c) throws JsonException, IOException {
        return decoder.decode(Objects.requireNonNull(file), c);
    }

    /**
     * Decodes a JSON file to an object.
     *
     * @param file the JSON file to decode.
     * @return the decoded object.
     * @throws JsonException if an error occurs during decoding.
     * @throws IOException if an I/O error occurs.
     */
    public synchronized JsonNode decode(File file) throws JsonException, IOException {
        return decoder.decode(Objects.requireNonNull(file));
    }

    /**
     * Decodes a JSON stream to an object of the specified type with optional
     * auto-closing.
     *
     * @param stream the JSON stream to decode.
     * @param c the target type class.
     * @param autoClose whether to automatically close the stream.
     * @param <T> the target type.
     * @return the decoded object.
     * @throws JsonException if an error occurs during decoding.
     * @throws IOException if an I/O error occurs.
     */
    public synchronized <T> T decode(InputStream stream, Class<T> c, boolean autoClose) throws JsonException, IOException {
        return decoder.decode(Objects.requireNonNull(stream), c, autoClose);
    }

    /**
     * Decodes a JSON stream to an object with optional auto-closing.
     *
     * @param stream the JSON stream to decode.
     * @param autoClose whether to automatically close the stream.
     * @return the decoded object.
     * @throws JsonException if an error occurs during decoding.
     * @throws IOException if an I/O error occurs.
     */
    public synchronized JsonNode decode(InputStream stream, boolean autoClose) throws JsonException, IOException {
        return decoder.decode(Objects.requireNonNull(stream), autoClose);
    }

    /**
     * Decodes a JSON stream to an object of the specified type.
     *
     * @param stream the JSON stream to decode.
     * @param c the target type class.
     * @param <T> the target type.
     * @return the decoded object.
     * @throws JsonException if an error occurs during decoding.
     * @throws IOException if an I/O error occurs.
     */
    public synchronized <T> T decode(InputStream stream, Class<T> c) throws JsonException, IOException {
        return decoder.decode(Objects.requireNonNull(stream), c, true);
    }

    /**
     * Decodes a JSON stream to an object.
     *
     * @param stream the JSON stream to decode.
     * @return the decoded object.
     * @throws JsonException if an error occurs during decoding.
     * @throws IOException if an I/O error occurs.
     */
    public synchronized JsonNode decode(InputStream stream) throws JsonException, IOException {
        return decoder.decode(Objects.requireNonNull(stream), true);
    }

    /**
     * Decodes a JSON string to an object of the specified type.
     *
     * @param src the JSON string to decode.
     * @param c the target type class.
     * @param <T> the target type.
     * @return the decoded object.
     * @throws JsonException if an error occurs during decoding.
     */
    public synchronized <T> T decode(String src, Class<T> c) throws JsonException {
        return decoder.decode(Objects.requireNonNull(src), c);
    }

    /**
     * Decodes a JSON string to an object.
     *
     * @param src the JSON string to decode.
     * @return the decoded object.
     * @throws JsonException if an error occurs during decoding.
     */
    public synchronized JsonNode decode(String src) throws JsonException {
        return decoder.decode(Objects.requireNonNull(src));
    }
}
