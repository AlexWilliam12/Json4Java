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

import static no.foundation.serializer.JsonToken.TokenType;

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
    JsonParser(List<JsonToken> tokens) {
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

    /**
     * Parses an object from the tokens.
     *
     * @return the parsed JSON object.
     */
    private @NotNull JsonObject parseObject() {
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

    /**
     * Parses an array from the tokens.
     *
     * @return the parsed JSON array.
     */
    private @NotNull JsonArray parseArray() {
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

    /**
     * Parses a number from a string value.
     *
     * @param value the string representation of the number.
     * @return the parsed number.
     */
    private @NotNull Number parseNumber(@NotNull String value) {
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
    private JsonToken expect(TokenType type) {
        if (!check(type)) {
            TokenType tokenType = tokens.get(index).type();
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
    private boolean check(TokenType type) {
        return index < tokens.size() && tokens.get(index).type() == type;
    }
}
