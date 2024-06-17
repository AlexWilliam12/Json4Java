package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;

import java.util.List;

class JsonDecoder {

    static <T> T decode(String src, Class<T> c) throws JsonException {
        JsonLexer lexer = new JsonLexer();
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonParser parser = new JsonParser(tokens);
        Object parsed = parser.parse();
        ObjectConverter converter = new ObjectConverter();
        return converter.convert(parsed, c);
    }

    static Object decode(String src) throws JsonException {
        JsonLexer lexer = new JsonLexer();
        List<JsonToken> tokens = lexer.tokenize(src);
        JsonParser parser = new JsonParser(tokens);
        return parser.parse();
    }
}
