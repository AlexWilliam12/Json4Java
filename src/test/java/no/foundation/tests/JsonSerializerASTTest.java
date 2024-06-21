package no.foundation.tests;

import no.foundation.serializer.JsonSerializer;
import no.foundation.serializer.tree.JsonArray;
import no.foundation.serializer.tree.JsonObject;
import no.foundation.serializer.tree.JsonValue;
import org.junit.jupiter.api.Test;

public class JsonSerializerASTTest {

    @Test
    public void encodeObject() {
        JsonObject node = new JsonObject();

        node.put("string", new JsonValue<>("Hello, World"));
        node.put("double", new JsonValue<>(123.1));
        node.put("integer", new JsonValue<>(10));
        node.put("long", new JsonValue<>(1_000_000_000_000L));
        node.put("null", new JsonValue<>(null));
        node.put("boolean", new JsonValue<>(false));

        node.put("object", JsonObject.builder()
                .put("key", "value")
                .build());

        node.put("array", JsonArray.of(1, 2, 3));

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(node, true);

        System.out.println("Encoded: " + encoded);
    }
}
