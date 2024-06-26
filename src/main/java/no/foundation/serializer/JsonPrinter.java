package no.foundation.serializer;

import no.foundation.serializer.tree.JsonArray;
import no.foundation.serializer.tree.JsonNode;
import no.foundation.serializer.tree.JsonObject;
import no.foundation.serializer.tree.JsonValue;

final class JsonPrinter {

    String print(JsonNode node, boolean formatted) {
        if (formatted) {
            return printValue(node, 0);
        }
        return node.toString();
    }

    private String printValue(JsonNode node, int indent) {
        return switch (node) {
            case JsonValue<?> value ->
                value.toString();
            case JsonArray array ->
                printArray(array, indent);
            case JsonObject object ->
                printObject(object, indent);
        };
    }

    private String printObject(JsonObject object, int indent) {
        String result = "{}";
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

    private String printArray(JsonArray array, int indent) {
        String result = "[]";
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
