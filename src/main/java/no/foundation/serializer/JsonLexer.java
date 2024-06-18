package no.foundation.serializer;

import no.foundation.serializer.exceptions.JsonException;

import java.util.ArrayList;
import java.util.List;

class JsonLexer {

    private int index;

    JsonLexer() {
        this.index = 0;
    }

    List<JsonToken> tokenize(String expr) {
        List<JsonToken> tokens = new ArrayList<>();
        while (index < expr.length()) {
            char c = expr.charAt(index);
            switch (c) {
                case '{' -> {
                    tokens.add(tokenize(JsonToken.TokenType.LEFT_BRACE, String.valueOf(c)));
                    index++;
                }
                case '}' -> {
                    tokens.add(tokenize(JsonToken.TokenType.RIGHT_BRACE, String.valueOf(c)));
                    index++;
                }
                case '[' -> {
                    tokens.add(tokenize(JsonToken.TokenType.LEFT_BRACKET, String.valueOf(c)));
                    index++;
                }
                case ']' -> {
                    tokens.add(tokenize(JsonToken.TokenType.RIGHT_BRACKET, String.valueOf(c)));
                    index++;
                }
                case ',' -> {
                    tokens.add(tokenize(JsonToken.TokenType.COMMA, String.valueOf(c)));
                    index++;
                }
                case ':' -> {
                    tokens.add(tokenize(JsonToken.TokenType.COLON, String.valueOf(c)));
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
        return tokens;
    }

    private JsonToken tokenizeLiteral(String expr) {
        StringBuilder sb = new StringBuilder();
        while (index < expr.length() && Character.isLetter(expr.charAt(index))) {
            sb.append(expr.charAt(index++));
        }
        String value = sb.toString();
        return switch (value) {
            case "true" -> tokenize(JsonToken.TokenType.TRUE, value);
            case "false" -> tokenize(JsonToken.TokenType.FALSE, value);
            case "null" -> tokenize(JsonToken.TokenType.NULL, value);
            default -> throw new JsonException("Unexpected token: " + value);
        };
    }

    private JsonToken tokenizeNumber(String expr) {
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
        return tokenize(JsonToken.TokenType.NUMBER, sb.toString());
    }

    private JsonToken tokenizeString(String expr) {
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
        return tokenize(JsonToken.TokenType.STRING, sb.toString());
    }

    private JsonToken tokenize(JsonToken.TokenType type, String value) {
        return new JsonToken(type, value);
    }
}
