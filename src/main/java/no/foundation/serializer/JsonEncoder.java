package no.foundation.serializer;

import java.util.Objects;
import no.foundation.serializer.tree.JsonNode;

final class JsonEncoder {

    private final JsonTreeAssembler partitioner;
    private final JsonPrinter printer;

    JsonEncoder() {
        this.partitioner = new JsonTreeAssembler();
        this.printer = new JsonPrinter();
    }

    String encode(JsonNode node, boolean formatted) {
        return printer.print(node, formatted);
    }

    String encode(JsonNode node) {
        return printer.print(node, false);
    }

    String encode(Object value) {
        JsonNode node = partitioner.assemble(Objects.requireNonNull(value));
        return printer.print(node, false);
    }

    String encode(Object value, boolean formatted) {
        JsonNode node = partitioner.assemble(Objects.requireNonNull(value));
        return printer.print(node, formatted);
    }
}
