package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;
import no.foundation.serializer.tree.JsonNode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Utility class for decoding JSON data from various sources.
 */
final class JsonDecoder {

    private final JsonLexer lexer;

    /**
     * Constructs a {@code JsonDecoder} with an internal {@link JsonLexer} instance.
     */
    @Contract(pure = true)
    JsonDecoder() {
        this.lexer = new JsonLexer();
    }

    /**
     * Decodes JSON data from a file into an object of the specified class.
     *
     * @param file the file containing JSON data
     * @param c    the class type to convert the JSON data into
     * @param <T>  the type of the resulting object
     * @return the decoded object
     * @throws JsonException if there is an error during JSON decoding
     * @throws IOException   if an I/O error occurs
     */
    <T> T decode(final File file, final Class<T> c) throws JsonException, IOException {
        String src = readInput(new FileInputStream(file), true);
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonNode node = new JsonParser(tokens).parse();
        return new ObjectConverter().convert(node, c);
    }

    /**
     * Decodes JSON data from a file into a {@link JsonNode}.
     *
     * @param file the file containing JSON data
     * @return the decoded {@link JsonNode}
     * @throws JsonException if there is an error during JSON decoding
     * @throws IOException   if an I/O error occurs
     */
    JsonNode decode(final File file) throws JsonException, IOException {
        String src = readInput(new FileInputStream(file), true);
        List<JsonToken> tokens = lexer.tokenize(src);
        return new JsonParser(tokens).parse();
    }

    /**
     * Decodes JSON data from an input stream into an object of the specified class.
     *
     * @param stream    the input stream containing JSON data
     * @param c         the class type to convert the JSON data into
     * @param autoClose flag indicating whether to automatically close the input stream after reading
     * @param <T>       the type of the resulting object
     * @return the decoded object
     * @throws JsonException if there is an error during JSON decoding
     * @throws IOException   if an I/O error occurs
     */
    <T> T decode(final InputStream stream, final Class<T> c, final boolean autoClose) throws JsonException, IOException {
        String src = readInput(stream, autoClose);
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonNode node = new JsonParser(tokens).parse();
        return new ObjectConverter().convert(node, c);
    }

    /**
     * Decodes JSON data from an input stream into a {@link JsonNode}.
     *
     * @param stream    the input stream containing JSON data
     * @param autoClose flag indicating whether to automatically close the input stream after reading
     * @return the decoded {@link JsonNode}
     * @throws JsonException if there is an error during JSON decoding
     * @throws IOException   if an I/O error occurs
     */
    JsonNode decode(final InputStream stream, final boolean autoClose) throws JsonException, IOException {
        String src = readInput(stream, autoClose);
        List<JsonToken> tokens = lexer.tokenize(src);
        return new JsonParser(tokens).parse();
    }

    /**
     * Decodes JSON data from a string into an object of the specified class.
     *
     * @param src the JSON data string
     * @param c   the class type to convert the JSON data into
     * @param <T> the type of the resulting object
     * @return the decoded object
     * @throws JsonException if there is an error during JSON decoding
     */
    <T> T decode(final String src, final Class<T> c) throws JsonException {
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonNode node = new JsonParser(tokens).parse();
        return new ObjectConverter().convert(node, c);
    }

    /**
     * Decodes JSON data from a string into a {@link JsonNode}.
     *
     * @param src the JSON data string
     * @return the decoded {@link JsonNode}
     * @throws JsonException if there is an error during JSON decoding
     */
    JsonNode decode(final String src) throws JsonException {
        List<JsonToken> tokens = lexer.tokenize(src);
        return new JsonParser(tokens).parse();
    }

    /**
     * Reads input from an input stream and returns it as a string.
     *
     * @param stream    the input stream to read from
     * @param autoClose flag indicating whether to automatically close the input stream after reading
     * @return the string read from the input stream
     * @throws IOException if an I/O error occurs
     */
    private @NotNull String readInput(final InputStream stream, final boolean autoClose) throws IOException {
        @NotNull String result;
        BufferedInputStream reader = null;
        try {
            reader = new BufferedInputStream(stream);
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            while (reader.available() > 0) {
                int read = reader.read(buffer);
                if (read == -1) break;
                sb.append(new String(buffer, 0, read, StandardCharsets.UTF_8));
            }
            result = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (autoClose && reader != null) {
                reader.close();
            }
        }
        return result;
    }
}
