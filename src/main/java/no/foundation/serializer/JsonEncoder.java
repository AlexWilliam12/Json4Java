package no.foundation.serializer;

import no.foundation.serializer.tree.JsonNode;

import java.util.Objects;

class JsonEncoder {

    private final ObjectPartitioner partitioner;
    private final JsonPrinter printer;

    JsonEncoder() {
        this.partitioner = new ObjectPartitioner();
        this.printer = new JsonPrinter();
    }

    String encode(Object value) {
        JsonNode node = partitioner.partition(Objects.requireNonNull(value));
        return printer.print(node, false);
    }

    String encode(Object value, boolean formatted) {
        JsonNode node = partitioner.partition(Objects.requireNonNull(value));
        return printer.print(node, formatted);
    }
}
