package no.foundation.serializer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import no.foundation.serializer.exceptions.JsonException;
import no.foundation.serializer.tree.JsonNode;

final class JsonDecoder {

    private final JsonLexer lexer;

    JsonDecoder() {
        this.lexer = new JsonLexer();
    }

    <T> T decode(File file, Class<T> type) throws JsonException, IOException {
        String src = readInput(new FileInputStream(file), true);
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonParser parser = new JsonParser(tokens);
        JsonNode node = parser.parse();
        JsonConverter converter = new JsonConverter();
        return converter.convert(node.getOriginalType(), type);
    }

    JsonNode decode(File file) throws JsonException, IOException {
        String src = readInput(new FileInputStream(file), true);
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonParser parser = new JsonParser(tokens);
        return parser.parse();
    }

    <T> T decode(InputStream stream, Class<T> type, boolean autoClose) throws JsonException, IOException {
        String src = readInput(stream, autoClose);
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonParser parser = new JsonParser(tokens);
        JsonNode node = parser.parse();
        JsonConverter converter = new JsonConverter();
        return converter.convert(node.getOriginalType(), type);
    }

    JsonNode decode(InputStream stream, boolean autoClose) throws JsonException, IOException {
        String src = readInput(stream, autoClose);
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonParser parser = new JsonParser(tokens);
        return parser.parse();
    }

    <T> T decode(String src, Class<T> type) throws JsonException {
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonParser parser = new JsonParser(tokens);
        JsonNode node = parser.parse();
        JsonConverter converter = new JsonConverter();
        return converter.convert(node.getOriginalType(), type);
    }

    JsonNode decode(String src) throws JsonException {
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonParser parser = new JsonParser(tokens);
        return parser.parse();
    }

    private String readInput(InputStream stream, boolean autoClose) throws IOException {
        BufferedInputStream reader = null;
        try {
            reader = new BufferedInputStream(stream);
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            while (reader.available() > 0) {
                int read = reader.read(buffer);
                if (read == -1) {
                    break;
                }
                sb.append(new String(buffer, 0, read, StandardCharsets.UTF_8));
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (autoClose && reader != null) {
                reader.close();
            }
        }
    }
}
