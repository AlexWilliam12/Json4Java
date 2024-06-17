package no.foundation.serializer;

record JsonToken(JsonToken.TokenType type, String value) {

    enum TokenType {
        LEFT_BRACKET, RIGHT_BRACKET,
        LEFT_BRACE, RIGHT_BRACE,
        COLON, COMMA,
        STRING, NUMBER,
        FALSE, TRUE, NULL,
        EOF
    }
}
