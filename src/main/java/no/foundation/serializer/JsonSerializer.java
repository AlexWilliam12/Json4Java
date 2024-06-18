package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class JsonSerializer {

    private final JsonEncoder encoder;
    private final JsonDecoder decoder;

    public JsonSerializer() {
        this.encoder = new JsonEncoder();
        this.decoder = new JsonDecoder();
    }

    public String encode(Object value) {
        return encoder.encode(value);
    }

    public String encode(Object value, boolean formatted) {
        return encoder.encode(value, formatted);
    }

    public <T> T decode(File file, Class<T> c) throws JsonException, IOException {
        return decoder.decode(file, c);
    }

    public Object decode(File file) throws JsonException, IOException {
        return decoder.decode(file);
    }

    public <T> T decode(InputStream stream, Class<T> c, boolean autoClose) throws JsonException, IOException {
        return decoder.decode(stream, c, autoClose);
    }

    public Object decode(InputStream stream, boolean autoClose) throws JsonException, IOException {
        return decoder.decode(stream, autoClose);
    }

    public <T> T decode(InputStream stream, Class<T> c) throws JsonException, IOException {
        return decoder.decode(stream, c, true);
    }

    public Object decode(InputStream stream) throws JsonException, IOException {
        return decoder.decode(stream, true);
    }

    public <T> T decode(String src, Class<T> c) throws JsonException {
        return decoder.decode(src, c);
    }

    public Object decode(String src) throws JsonException {
        return decoder.decode(src);
    }
}
