package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static no.foundation.serializer.JsonToken.TokenType;

/**
 * Lexer class for tokenizing a JSON string into a list of JSON tokens.
 * This class is final and cannot be subclassed.
 */
final class JsonLexer {

    private int index;

    /**
     * Constructs a new JsonLexer.
     * Initializes the index to zero.
     */
    @Contract(pure = true)
    JsonLexer() {
        this.index = 0;
    }

    /**
     * Tokenizes the given JSON expression string into a list of JSON tokens.
     *
     * @param expr the JSON expression to tokenize.
     * @return the list of JSON tokens.
     * @throws JsonException if an unexpected character is encountered.
     */
    @NotNull
    List<JsonToken> tokenize(@NotNull String expr) {
        List<JsonToken> tokens = new ArrayList<>();
        while (index < expr.length()) {
            char c = expr.charAt(index);
            switch (c) {
                case '{' -> {
                    tokens.add(tokenize(TokenType.LEFT_BRACE, String.valueOf(c)));
                    index++;
                }
                case '}' -> {
                    tokens.add(tokenize(TokenType.RIGHT_BRACE, String.valueOf(c)));
                    index++;
                }
                case '[' -> {
                    tokens.add(tokenize(TokenType.LEFT_BRACKET, String.valueOf(c)));
                    index++;
                }
                case ']' -> {
                    tokens.add(tokenize(TokenType.RIGHT_BRACKET, String.valueOf(c)));
                    index++;
                }
                case ',' -> {
                    tokens.add(tokenize(TokenType.COMMA, String.valueOf(c)));
                    index++;
                }
                case ':' -> {
                    tokens.add(tokenize(TokenType.COLON, String.valueOf(c)));
                    index++;
                }
                case '"' -> tokens.add(tokenizeString(expr));
                default -> {
                    if (c == '-' || Character.isDigit(c)) tokens.add(tokenizeNumber(expr));
                    else if (c == 't' || c == 'f' || c == 'n') tokens.add(tokenizeLiteral(expr));
                    else if (Character.isWhitespace(c)) index++;
                    else throw new JsonException("Unexpected character '%c'".formatted(c));
                }
            }
        }
        index = 0;
        return tokens;
    }

    /**
     * Tokenizes a literal (true, false, null) from the JSON expression.
     *
     * @param expr the JSON expression.
     * @return the JSON token representing the literal.
     * @throws JsonException if an unexpected token is encountered.
     */
    private @NotNull JsonToken tokenizeLiteral(@NotNull String expr) {
        StringBuilder sb = new StringBuilder();
        while (index < expr.length() && Character.isLetter(expr.charAt(index))) {
            sb.append(expr.charAt(index++));
        }
        String value = sb.toString();
        return switch (value) {
            case "true" -> tokenize(TokenType.TRUE, value);
            case "false" -> tokenize(TokenType.FALSE, value);
            case "null" -> tokenize(TokenType.NULL, value);
            default -> throw new JsonException("Unexpected token: " + value);
        };
    }

    /**
     * Tokenizes a number from the JSON expression.
     *
     * @param expr the JSON expression.
     * @return the JSON token representing the number.
     */
    private @NotNull JsonToken tokenizeNumber(@NotNull String expr) {
        StringBuilder sb = new StringBuilder();
        if (expr.charAt(index) == '-') {
            sb.append('-');
            index++;
        }
        while (index < expr.length() && Character.isDigit(expr.charAt(index))) {
            sb.append(expr.charAt(index++));
        }
        if (expr.charAt(index) == '.') {
            sb.append(expr.charAt(index++));
            while (index < expr.length()) {
                char c = expr.charAt(index);
                if (!Character.isDigit(c)) break;
                sb.append(expr.charAt(index++));
            }
        }
        return tokenize(TokenType.NUMBER, sb.toString());
    }

    /**
     * Tokenizes a string from the JSON expression.
     *
     * @param expr the JSON expression.
     * @return the JSON token representing the string.
     */
    private @NotNull JsonToken tokenizeString(@NotNull String expr) {
        StringBuilder sb = new StringBuilder();
        index++;
        char prev = '\0';
        while (index < expr.length()) {
            char c = expr.charAt(index);
            if (c == '"' && prev != '\\') break;
            sb.append(c);
            prev = c;
            index++;
        }
        index++;
        return tokenize(TokenType.STRING, sb.toString());
    }

    /**
     * Creates a new JSON token with the specified type and value.
     *
     * @param type  the type of the JSON token.
     * @param value the value of the JSON token.
     * @return the created JSON token.
     */
    @Contract("_, _ -> new")
    private @NotNull JsonToken tokenize(TokenType type, String value) {
        return new JsonToken(type, value);
    }
}
