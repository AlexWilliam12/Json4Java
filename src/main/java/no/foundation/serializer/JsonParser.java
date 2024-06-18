package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;
import no.foundation.serializer.tree.JsonArray;
import no.foundation.serializer.tree.JsonNode;
import no.foundation.serializer.tree.JsonObject;
import no.foundation.serializer.tree.JsonValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

class JsonParser {

    private final List<JsonToken> tokens;
    private int index;

    JsonParser(List<JsonToken> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }

    JsonNode parse() {
        return parseValue();
    }

    private JsonNode parseValue() {
        if (check(JsonToken.TokenType.LEFT_BRACE)) {
            return parseObject();
        } else if (check(JsonToken.TokenType.LEFT_BRACKET)) {
            return parseArray();
        } else if (check(JsonToken.TokenType.STRING)) {
            return new JsonValue<>(expect(JsonToken.TokenType.STRING).value());
        } else if (check(JsonToken.TokenType.NUMBER)) {
            return new JsonValue<>(parseNumber(expect(JsonToken.TokenType.NUMBER).value()));
        } else if (check(JsonToken.TokenType.TRUE)) {
            return new JsonValue<>(Boolean.parseBoolean(expect(JsonToken.TokenType.TRUE).value()));
        } else if (check(JsonToken.TokenType.FALSE)) {
            return new JsonValue<>(Boolean.parseBoolean(expect(JsonToken.TokenType.FALSE).value()));
        } else if (check(JsonToken.TokenType.NULL)) {
            expect(JsonToken.TokenType.NULL);
            return new JsonValue<>(null);
        } else {
            JsonToken.TokenType type = tokens.get(index).type();
            throw new JsonException("Unexpected token: " + type);
        }
    }

    private JsonObject parseObject() {
        expect(JsonToken.TokenType.LEFT_BRACE);
        JsonObject map = new JsonObject();
        while (!check(JsonToken.TokenType.RIGHT_BRACE)) {
            String key = expect(JsonToken.TokenType.STRING).value();
            expect(JsonToken.TokenType.COLON);
            map.put(key, parseValue());
            if (!check(JsonToken.TokenType.RIGHT_BRACE)) {
                expect(JsonToken.TokenType.COMMA);
            }
        }
        expect(JsonToken.TokenType.RIGHT_BRACE);
        return map;
    }

    private JsonArray parseArray() {
        expect(JsonToken.TokenType.LEFT_BRACKET);
        JsonArray array = new JsonArray();
        while (!check(JsonToken.TokenType.RIGHT_BRACKET)) {
            array.add(parseValue());
            if (!check(JsonToken.TokenType.RIGHT_BRACKET)) {
                expect(JsonToken.TokenType.COMMA);
            }
        }
        expect(JsonToken.TokenType.RIGHT_BRACKET);
        return array;
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
