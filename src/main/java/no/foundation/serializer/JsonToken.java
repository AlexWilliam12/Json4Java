package no.foundation.serializer;

/**
 * Record representing a JSON token with a specific type and value.
 */
record JsonToken(
        JsonToken.TokenType type,
        String value
) {

    /**
     * Enum representing the various types of JSON tokens.
     */
    enum TokenType {
        LEFT_BRACKET, RIGHT_BRACKET,
        LEFT_BRACE, RIGHT_BRACE,
        COLON, COMMA,
        STRING, NUMBER,
        FALSE, TRUE, NULL,
    }
}
