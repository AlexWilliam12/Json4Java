package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;
import no.foundation.serializer.tree.JsonArray;
import no.foundation.serializer.tree.JsonNode;
import no.foundation.serializer.tree.JsonObject;
import no.foundation.serializer.tree.JsonValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Parser class for converting a list of JSON tokens into JSON nodes.
 * This class is final and cannot be subclassed.
 */
final class JsonParser {

    private final List<JsonToken> tokens;
    private int index;

    /**
     * Constructs a new JsonParser with the specified list of tokens.
     *
     * @param tokens the list of JSON tokens to parse.
     */
    @Contract(pure = true)
    JsonParser(final List<JsonToken> tokens) {
        this.tokens = tokens;
        this.index = 0;
    }

    /**
     * Parses the tokens into a JSON node.
     *
     * @return the parsed JSON node.
     */
    JsonNode parse() {
        JsonNode node = parseValue();
        index = 0;
        return node;
    }

    /**
     * Parses the current value based on the current token.
     *
     * @return the parsed JSON node.
     */
    private JsonNode parseValue() {
        JsonNode result;
        if (check(JsonToken.TokenType.LEFT_BRACE)) {
            result = parseObject();
        } else if (check(JsonToken.TokenType.LEFT_BRACKET)) {
            result = parseArray();
        } else if (check(JsonToken.TokenType.STRING)) {
            result = new JsonValue<>(expect(JsonToken.TokenType.STRING).value());
        } else if (check(JsonToken.TokenType.NUMBER)) {
            result = new JsonValue<>(parseNumber(expect(JsonToken.TokenType.NUMBER).value()));
        } else if (check(JsonToken.TokenType.TRUE)) {
            result = new JsonValue<>(Boolean.parseBoolean(expect(JsonToken.TokenType.TRUE).value()));
        } else if (check(JsonToken.TokenType.FALSE)) {
            result = new JsonValue<>(Boolean.parseBoolean(expect(JsonToken.TokenType.FALSE).value()));
        } else if (check(JsonToken.TokenType.NULL)) {
            expect(JsonToken.TokenType.NULL);
            result = new JsonValue<>(null);
        } else {
            JsonToken.TokenType type = tokens.get(index).type();
            throw new JsonException("Unexpected token: " + type);
        }
        return result;
    }

    /**
     * Parses an object from the tokens.
     *
     * @return the parsed JSON object.
     */
    private @NotNull JsonObject parseObject() {
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

    /**
     * Parses an array from the tokens.
     *
     * @return the parsed JSON array.
     */
    private @NotNull JsonArray parseArray() {
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

    /**
     * Parses a number from a string value.
     *
     * @param value the string representation of the number.
     * @return the parsed number.
     */
    private @NotNull Number parseNumber(@NotNull final String value) {
        return value.contains(".")
                ? new BigDecimal(value)
                : new BigInteger(value);
    }

    /**
     * Expects the current token to be of the specified type and returns it.
     * Advances to the next token.
     *
     * @param type the expected token type.
     * @return the current token.
     * @throws JsonException if the current token is not of the expected type.
     */
    private JsonToken expect(final JsonToken.TokenType type) {
        if (!check(type)) {
            JsonToken.TokenType tokenType = tokens.get(index).type();
            String message = "Expected Token %s but found %s".formatted(type, tokenType);
            throw new JsonException(message);
        }
        return tokens.get(index++);
    }

    /**
     * Checks if the current token is of the specified type.
     *
     * @param type the token type to check for.
     * @return true if the current token is of the specified type, false otherwise.
     */
    private boolean check(final JsonToken.TokenType type) {
        return index < tokens.size() && tokens.get(index).type() == type;
    }
}
