package no.foundation.tests;

import no.foundation.serializer.JsonSerializer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JsonDecoderSpecificTypesTest {

    @Test
    public void decodeArrayWithArrays() throws IOException {
        String path = "src/test/resources/json_to_map/array_with_arrays.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        List<?> list = serializer.decode(file, List.class);

        System.out.println("Decoded: " + list);

        assertFalse(list.isEmpty());
        assertEquals(3, list.size());

        Object first = list.getFirst();
        assertInstanceOf(Collection.class, first);
        assertEquals(3, ((Collection<?>) first).size());

        Object last = list.getLast();
        assertInstanceOf(Collection.class, last);
        assertEquals(3, ((Collection<?>) last).size());
    }

    @Test
    public void decodeBasicTypes() throws IOException {
        String path = "src/test/resources/json_to_map/basic_types.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        Map<?, ?> map = serializer.decode(file, Map.class);

        System.out.println("Decoded: " + map);

        assertFalse(map.isEmpty());
        assertEquals(5, map.size());
    }

    @Test
    public void decodeEmptyArray() throws IOException {
        String path = "src/test/resources/json_to_map/empty_array.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();

        List<?> list = serializer.decode(file, List.class);
        System.out.println("Decoded: " + list);

        Set<?> set = serializer.decode(file, Set.class);
        System.out.println("Decoded: " + set);

        Collection<?> collection = serializer.decode(file, Collection.class);
        System.out.println("Decoded: " + collection);

        assertTrue(list.isEmpty());
        assertTrue(set.isEmpty());
        assertTrue(collection.isEmpty());
    }

    @Test
    public void decodeEmptyObject() throws IOException {
        String path = "src/test/resources/json_to_map/empty_object.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        Map<?, ?> map = serializer.decode(file, Map.class);

        System.out.println("Decoded: " + map);

        assertTrue(map.isEmpty());
    }

    @Test
    public void decodeEmptyObjectArray() throws IOException {
        String path = "src/test/resources/json_to_map/empty_object_array.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        List<?> list = serializer.decode(file, List.class);

        System.out.println("Decoded: " + list);

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());

        Object value = list.getFirst();
        assertInstanceOf(Map.class, value);
        assertTrue(((Map<?, ?>) value).isEmpty());
    }

    @Test
    public void decodeNestedObject() throws IOException {
        String path = "src/test/resources/json_to_map/nested_object.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        Map<?, ?> map = serializer.decode(file, Map.class);

        System.out.println("Decoded: " + map);

        assertFalse(map.isEmpty());
        assertEquals(1, map.size());
    }

    @Test
    public void decodeObjectArray() throws IOException {
        String path = "src/test/resources/json_to_map/object_array.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        List<?> list = serializer.decode(file, List.class);

        System.out.println("Decoded: " + list);

        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }

    @Test
    public void decodeObjectWithAllTypes() throws IOException {
        String path = "src/test/resources/json_to_map/object_with_all_types.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        Map<?, ?> map = serializer.decode(file, Map.class);

        System.out.println("Decoded: " + map);

        assertFalse(map.isEmpty());
        assertEquals(7, map.size());
    }

    @Test
    public void decodeObjectWithArrays() throws IOException {
        String path = "src/test/resources/json_to_map/object_with_arrays.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        Map<?, ?> map = serializer.decode(file, Map.class);

        System.out.println("Decoded: " + map);

        assertFalse(map.isEmpty());
        assertEquals(2, map.size());
    }

    @Test
    public void decodeSimpleArray() throws IOException {
        String path = "src/test/resources/json_to_map/simple_array.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        List<?> list = serializer.decode(file, List.class);

        System.out.println("Decoded: " + list);

        assertFalse(list.isEmpty());
        assertEquals(3, list.size());
    }

    @Test
    public void decodeSimpleObject() throws IOException {
        String path = "src/test/resources/json_to_map/simple_object.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        Map<?, ?> map = serializer.decode(file, Map.class);

        System.out.println("Decoded: " + map);

        assertFalse(map.isEmpty());
        assertEquals(3, map.size());
    }

    @Test
    public void decodeTimes() throws IOException {
        String path = "src/test/resources/json_to_map/times.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        System.out.println("File name: " + file.getName());

        JsonSerializer serializer = new JsonSerializer();
        Map<?, ?> map = serializer.decode(file, Map.class);

        System.out.println("Decoded: " + map);

        assertFalse(map.isEmpty());
        assertEquals(7, map.size());

        assertDoesNotThrow(() -> {
            String localDate = map.get("localDate").toString();
            System.out.println("LocalDate: " + LocalDate.parse(localDate));

            String localTime = map.get("localTime").toString();
            System.out.println("LocalTime: " + LocalTime.parse(localTime));

            String localDateTime = map.get("localDateTime").toString();
            System.out.println("LocalDateTime: " + LocalDateTime.parse(localDateTime));

            String offsetTime = map.get("offsetTime").toString();
            System.out.println("OffSetTime: " + OffsetTime.parse(offsetTime));

            String offsetDateTime = map.get("offsetDateTime").toString();
            System.out.println("OffsetDateTime: " + OffsetDateTime.parse(offsetDateTime));

            String zonedDateTime = map.get("zonedDateTime").toString();
            System.out.println("ZonedDateTime: " + ZonedDateTime.parse(zonedDateTime));

            String instant = map.get("instant").toString();
            System.out.println("Instant: " + Instant.parse(instant));
        });
    }
}
