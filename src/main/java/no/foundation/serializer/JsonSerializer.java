package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;

public final class JsonSerializer {

    public String encode(Object value) {
        JsonPrinter printer = new JsonPrinter(false);
        return printer.print(value);
    }

    public String encode(Object value, boolean formatted) {
        JsonPrinter printer = new JsonPrinter(formatted);
        return printer.print(value);
    }

    public <T> T decode(String src, Class<T> c) throws JsonException {
        return JsonDecoder.decode(src, c);
    }

    public Object decode(String src) throws JsonException {
        return JsonDecoder.decode(src);
    }
}
