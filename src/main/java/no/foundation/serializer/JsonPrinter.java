package no.foundation.serializer;

import no.foundation.serializer.tree.JsonArray;
import no.foundation.serializer.tree.JsonNode;
import no.foundation.serializer.tree.JsonObject;
import no.foundation.serializer.tree.JsonValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class that provides methods for printing JSON nodes in a formatted or unformatted style.
 * This class is final and cannot be subclassed.
 */
final class JsonPrinter {

    /**
     * Prints the given JSON node as a string.
     * The output can be formatted or unformatted based on the specified flag.
     *
     * @param node      the JSON node to print.
     * @param formatted whether the output should be formatted.
     * @return the string representation of the JSON node.
     */
    String print(final JsonNode node, final boolean formatted) {
        String result;
        if (formatted) {
            result = printValue(node, 0);
        } else {
            result = node.toString();
        }
        return result;
    }

    /**
     * Prints the value of the given JSON node with the specified indentation.
     * The output is formatted based on the type of the JSON node.
     *
     * @param node   the JSON node to print.
     * @param indent the indentation level.
     * @return the string representation of the JSON node's value.
     */
    private @Nullable String printValue(@NotNull final JsonNode node, final int indent) {
        return switch (node) {
            case JsonValue<?> value -> value.toString();
            case JsonArray array -> printArray(array, indent);
            case JsonObject object -> printObject(object, indent);
        };
    }

    /**
     * Prints a JSON object with the specified indentation.
     * The output includes the object's fields and their values.
     *
     * @param object the JSON object to print.
     * @param indent the indentation level.
     * @return the string representation of the JSON object.
     */
    private @NotNull String printObject(@NotNull final JsonObject object, final int indent) {
        @NotNull String result = "{}";
        if (!object.isEmpty()) {
            String tabs = "  ".repeat(indent);
            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            var entries = object.entrySet();
            for (var entry : entries) {
                sb.append(tabs);
                sb.append("  \"");
                sb.append(entry.getKey());
                sb.append("\": ");
                sb.append(printValue(entry.getValue(), indent + 1));
                sb.append(",\n");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("\n");
            sb.append(tabs);
            sb.append("}");
            result = sb.toString();
        }
        return result;
    }

    /**
     * Prints a JSON array with the specified indentation.
     * The output includes the array's elements.
     *
     * @param array  the JSON array to print.
     * @param indent the indentation level.
     * @return the string representation of the JSON array.
     */
    private @NotNull String printArray(@NotNull final JsonArray array, final int indent) {
        @NotNull String result = "[]";
        if (!array.isEmpty()) {
            String tabs = "  ".repeat(indent);
            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
            for (JsonNode node : array) {
                sb.append(tabs);
                sb.append("  ");
                sb.append(printValue(node, indent + 1));
                sb.append(",\n");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("\n");
            sb.append(tabs);
            sb.append("]");
            result = sb.toString();
        }
        return result;
    }
}
