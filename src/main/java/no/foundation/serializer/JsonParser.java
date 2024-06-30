package no.foundation.serializer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import no.foundation.serializer.JsonToken.TokenType;
import no.foundation.serializer.exceptions.JsonException;
import no.foundation.serializer.tree.JsonArray;
import no.foundation.serializer.tree.JsonNode;
import no.foundation.serializer.tree.JsonObject;
import no.foundation.serializer.tree.JsonValue;

final class JsonParser {

    private final List<JsonToken> tokens;
    private int index;

    JsonParser(List<JsonToken> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }

    JsonNode parse() {
        JsonNode node = parseValue();
        this.index = 0;
        return node;
    }

    private JsonNode parseValue() {
        if (check(TokenType.LEFT_BRACE)) {
            return parseObject();
        } else if (check(TokenType.LEFT_BRACKET)) {
            return parseArray();
        } else if (check(TokenType.STRING)) {
            String value = expect(TokenType.STRING).value();
            return new JsonValue<>(value);
        } else if (check(TokenType.NUMBER)) {
            String value = expect(TokenType.NUMBER).value();
            return new JsonValue<>(parseNumber(value));
        } else if (check(TokenType.TRUE)) {
            String value = expect(TokenType.TRUE).value();
            return new JsonValue<>(Boolean.parseBoolean(value));
        } else if (check(TokenType.FALSE)) {
            String value = expect(TokenType.FALSE).value();
            return new JsonValue<>(Boolean.parseBoolean(value));
        } else if (check(TokenType.NULL)) {
            expect(TokenType.NULL);
            return new JsonValue<>(null);
        } else {
            TokenType type = tokens.get(index).type();
            throw new JsonException("Unexpected token: " + type);
        }
    }

    private JsonObject parseObject() {
        expect(TokenType.LEFT_BRACE);
        JsonObject obj = new JsonObject();
        while (!check(TokenType.RIGHT_BRACE)) {
            String key = expect(TokenType.STRING).value();
            expect(TokenType.COLON);
            obj.put(key, parseValue());
            if (!check(TokenType.RIGHT_BRACE)) {
                expect(TokenType.COMMA);
            }
        }
        expect(TokenType.RIGHT_BRACE);
        return obj;
    }

    private JsonArray parseArray() {
        expect(TokenType.LEFT_BRACKET);
        JsonArray array = new JsonArray();
        while (!check(TokenType.RIGHT_BRACKET)) {
            array.add(parseValue());
            if (!check(TokenType.RIGHT_BRACKET)) {
                expect(TokenType.COMMA);
            }
        }
        expect(TokenType.RIGHT_BRACKET);
        return array;
    }

    private Number parseNumber(String value) {
        return value.contains(".")
                ? new BigDecimal(value)
                : new BigInteger(value);
    }

    private JsonToken expect(TokenType type) {
        if (!check(type)) {
            TokenType token = tokens.get(index).type();
            String message = "Expected Token %s but found %s".formatted(type, token);
            throw new JsonException(message);
        }
        return tokens.get(index++);
    }

    private boolean check(TokenType type) {
        return index < tokens.size() && tokens.get(index).type() == type;
    }
}
