package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class JsonParser {

    private final List<JsonToken> tokens;
    private int index;

    JsonParser(List<JsonToken> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }

    Object parse() {
        return parseValue();
    }

    private Object parseValue() {
        if (check(JsonToken.TokenType.LEFT_BRACE)) {
            return parseObject();
        } else if (check(JsonToken.TokenType.LEFT_BRACKET)) {
            return parseArray();
        } else if (check(JsonToken.TokenType.STRING)) {
            return expect(JsonToken.TokenType.STRING).value();
        } else if (check(JsonToken.TokenType.NUMBER)) {
            return parseNumber(expect(JsonToken.TokenType.NUMBER).value());
        } else if (check(JsonToken.TokenType.TRUE)) {
            return Boolean.parseBoolean(expect(JsonToken.TokenType.TRUE).value());
        } else if (check(JsonToken.TokenType.FALSE)) {
            return Boolean.parseBoolean(expect(JsonToken.TokenType.FALSE).value());
        } else if (check(JsonToken.TokenType.NULL)) {
            return expect(JsonToken.TokenType.NULL).value();
        } else {
            JsonToken.TokenType type = tokens.get(index).type();
            throw new JsonException("Unexpected token: " + type);
        }
    }

    private Map<String, Object> parseObject() {
        expect(JsonToken.TokenType.LEFT_BRACE);
        Map<String, Object> map = new LinkedHashMap<>();
        while (!check(JsonToken.TokenType.RIGHT_BRACE)) {
            String key = expect(JsonToken.TokenType.STRING).value();
            expect(JsonToken.TokenType.COLON);
            Object value = parseValue();
            map.put(key, value);
            if (!check(JsonToken.TokenType.RIGHT_BRACE)) {
                expect(JsonToken.TokenType.COMMA);
            }
        }
        expect(JsonToken.TokenType.RIGHT_BRACE);
        return map;
    }

    private List<?> parseArray() {
        expect(JsonToken.TokenType.LEFT_BRACKET);
        List<Object> list = new ArrayList<>();
        while (!check(JsonToken.TokenType.RIGHT_BRACKET)) {
            list.add(parseValue());
            if (!check(JsonToken.TokenType.RIGHT_BRACKET)) {
                expect(JsonToken.TokenType.COMMA);
            }
        }
        expect(JsonToken.TokenType.RIGHT_BRACKET);
        return list;
    }

    private Number parseNumber(String value) {
        return value.contains(".")
                ? new BigDecimal(value)
                : new BigInteger(value);
    }

    private JsonToken expect(JsonToken.TokenType type) {
        if (!check(type)) {
            JsonToken.TokenType tokenType = tokens.get(index).type();
            String message = "Expected Token %s but found %s".formatted(type, tokenType);
            throw new JsonException(message);
        }
        return tokens.get(index++);
    }

    private boolean check(JsonToken.TokenType type) {
        return index < tokens.size() && tokens.get(index).type() == type;
    }
}
