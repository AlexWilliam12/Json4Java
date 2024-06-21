package no.foundation.tests;

import no.foundation.serializer.JsonSerializer;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonEncoderSpecificTypesTest {

    @Test
    public void encodeArrayWithArrays() {
        List<?> matrix = List.of(
                List.of(1, 2, 3),
                List.of(4, 5, 6),
                List.of(7, 8, 9)
        );

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(matrix);
        String encodedWellFormatted = serializer.encode(matrix, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeBasicTypes() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("string", "example");
        map.put("double", 123.45);
        map.put("integer", 42);
        map.put("boolean", true);
        map.put("null", null);

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(map);
        String encodedWellFormatted = serializer.encode(map, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeEmptyArray() {
        List<?> list = List.of();

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(list);
        String encodedWellFormatted = serializer.encode(list, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeEmptyObject() {
        Map<?, ?> map = Map.of();

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(map);
        String encodedWellFormatted = serializer.encode(map, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeEmptyObjectArray() {
        List<Map<?, ?>> list = List.of(
                Map.of()
        );

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(list);
        String encodedWellFormatted = serializer.encode(list, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeNestedObject() {
        Map<?, ?> map = Map.of(
                "person", Map.of(
                        "name", "Alice",
                        "address", Map.of(
                                "street", "123 Main St",
                                "city", "Wonderland"
                        )
                )
        );

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(map);
        String encodedWellFormatted = serializer.encode(map, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeObjectArray() {
        List<?> list = List.of(
                Map.of("name", "John", "age", 30),
                Map.of("name", "Jane", "age", 25)
        );

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(list);
        String encodedWellFormatted = serializer.encode(list, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeObjectWithAllTypes() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("string", "example");
        map.put("double", 123.45);
        map.put("integer", 42);
        map.put("boolean", true);
        map.put("null", null);
        map.put("object", Map.of("key", "value"));
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add("two");
        list.add(null);
        list.add(false);
        map.put("array", list);

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(map);
        String encodedWellFormatted = serializer.encode(map, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeObjectWithArrays() {
        Map<?, ?> map = Map.of(
                "fruits", List.of("apple", "banana", "cherry"),
                "vegetables", List.of("carrot", "broccoli", "peas")
        );

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(map);
        String encodedWellFormatted = serializer.encode(map, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeSimpleArray() {
        JsonSerializer serializer = new JsonSerializer();
    }

    @Test
    public void encodeSimpleObject() {
        List<String> list = List.of("apple", "banana", "cherry");

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(list);
        String encodedWellFormatted = serializer.encode(list, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }

    @Test
    public void encodeTimes() {
        Map<?, ?> map = Map.of(
                "localDate", LocalDate.parse("2024-06-18"),
                "localTime", LocalTime.parse("14:30:00"),
                "localDateTime", LocalDateTime.parse("2024-06-18T14:30:00"),
                "offsetTime", OffsetTime.parse("14:30:00-04:00"),
                "offsetDateTime", OffsetDateTime.parse("2024-06-18T14:30:00-04:00"),
                "zonedDateTime", ZonedDateTime.parse("2024-06-18T14:30:00-04:00[America/New_York]"),
                "instant", Instant.parse("2024-06-18T18:30:00Z")
        );

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(map);
        String encodedWellFormatted = serializer.encode(map, true);

        System.out.println("Encoded: " + encoded);
        System.out.println();
        System.out.println("Encoded well formatted: " + encodedWellFormatted);
    }
}
