package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;
import no.foundation.serializer.tree.JsonNode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

class JsonDecoder {

    private final JsonLexer lexer;

    JsonDecoder() {
        this.lexer = new JsonLexer();
    }

    <T> T decode(File file, Class<T> c) throws JsonException, IOException {
        String src = readInput(new FileInputStream(file), true);
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonNode node = new JsonParser(tokens).parse();
        return new ObjectConverter().convert(node, c);
    }

    Object decode(File file) throws JsonException, IOException {
        String src = readInput(new FileInputStream(file), true);
        List<JsonToken> tokens = lexer.tokenize(src);
        return new JsonParser(tokens).parse();
    }

    <T> T decode(InputStream stream, Class<T> c, boolean autoClose) throws JsonException, IOException {
        String src = readInput(stream, autoClose);
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonNode node = new JsonParser(tokens).parse();
        return new ObjectConverter().convert(node, c);
    }

    Object decode(InputStream stream, boolean autoClose) throws JsonException, IOException {
        String src = readInput(stream, autoClose);
        List<JsonToken> tokens = lexer.tokenize(src);
        return new JsonParser(tokens).parse();
    }

    <T> T decode(String src, Class<T> c) throws JsonException {
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonNode node = new JsonParser(tokens).parse();
        return new ObjectConverter().convert(node, c);
    }

    Object decode(String src) throws JsonException {
        List<JsonToken> tokens = lexer.tokenize(src);
        return new JsonParser(tokens).parse();
    }

    private String readInput(InputStream stream, boolean autoClose) throws IOException {
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
