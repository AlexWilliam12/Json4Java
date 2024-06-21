package no.foundation.serializer;

import no.foundation.serializer.tree.JsonNode;

import java.util.Objects;

/**
 * Encoder class for converting objects to JSON strings.
 * This class is final and cannot be subclassed.
 */
final class JsonEncoder {

    private final ObjectPartitioner partitioner;
    private final JsonPrinter printer;

    /**
     * Constructs a new JsonEncoder.
     * Initializes the ObjectPartitioner and JsonPrinter.
     */
    JsonEncoder() {
        this.partitioner = new ObjectPartitioner();
        this.printer = new JsonPrinter();
    }

    String encode(JsonNode node, boolean formatted) {
        // TODO: documentation
        return printer.print(node, formatted);
    }

    String encode(JsonNode node) {
        // TODO: documentation
        return printer.print(node, false);
    }

    /**
     * Encodes the specified object into a JSON string.
     *
     * @param value the object to encode.
     * @return the JSON string representation of the object.
     * @throws NullPointerException if the specified object is null.
     */
    String encode(Object value) {
        JsonNode node = partitioner.partition(Objects.requireNonNull(value));
        return printer.print(node, false);
    }

    /**
     * Encodes the specified object into a JSON string.
     *
     * @param value     the object to encode.
     * @param formatted whether the JSON string should be formatted.
     * @return the JSON string representation of the object.
     * @throws NullPointerException if the specified object is null.
     */
    String encode(Object value, boolean formatted) {
        JsonNode node = partitioner.partition(Objects.requireNonNull(value));
        return printer.print(node, formatted);
    }
}
