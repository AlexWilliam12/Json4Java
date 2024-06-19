package no.foundation.tests;

import no.foundation.serializer.JsonSerializer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("rawtypes")
public class JsonDecoderToSpecificTypesTest {

    @Test
    public void decodeArrayWithArrays() throws IOException {
        List list1 = List.of(
                List.of(1, 2, 3),
                List.of(4, 5, 6),
                List.of(7, 8, 9)
        );

        System.out.println("Original: " + list1);

        String path = "src/test/resources/json_to_map/array_with_arrays.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        JsonSerializer serializer = new JsonSerializer();
        List list2 = serializer.decode(file, List.class);

        System.out.println("Decoded: " + list2);
        assertEquals(list1.size(), list2.size());

        for (int i = 0; i < list2.size(); i++) {
            List subList1 = (List) list1.get(i);
            List subList2 = (List) list2.get(i);
            assertEquals(subList1.size(), subList2.size());
            for (int j = 0; j < subList1.size(); j++) {
                Number n1 = (Number) subList1.get(j);
                Number n2 = (Number) subList2.get(j);
                assertEquals(n1.intValue(), n2.intValue());
            }
        }
    }

    @Test
    public void decodeBasicTypes() throws IOException {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("string", "example");
        map1.put("double", 123.45);
        map1.put("integer", 42);
        map1.put("boolean", true);
        map1.put("null", null);

        System.out.println("Original: " + map1);

        String path = "src/test/resources/json_to_map/basic_types.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        JsonSerializer serializer = new JsonSerializer();
        Map map2 = serializer.decode(file, Map.class);

        System.out.println("Decoded: " + map2);
        assertEquals(map1.size(), map2.size());

        Set entries1 = map1.entrySet();
        Set entries2 = map2.entrySet();

        int length = entries1.size();
        for (int i = 0; i < length; i++) {
            Map.Entry entry1 = (Map.Entry) entries1.iterator().next();
            Map.Entry entry2 = (Map.Entry) entries2.iterator().next();
            assertEquals(entry1.getKey(), entry2.getKey());
            assertEquals(entry1.getValue(), entry2.getValue());
        }
    }

    @Test
    public void decodeEmptyArray() throws IOException {
        String path = "src/test/resources/json_to_map/empty_array.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        JsonSerializer serializer = new JsonSerializer();

        List list = serializer.decode(file, List.class);
        System.out.println("Decoded: " + list);

        Set set = serializer.decode(file, Set.class);
        System.out.println("Decoded: " + set);

        Collection collection = serializer.decode(file, Collection.class);
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

        JsonSerializer serializer = new JsonSerializer();
        Map map = serializer.decode(file, Map.class);

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

        JsonSerializer serializer = new JsonSerializer();
        List list = serializer.decode(file, List.class);

        System.out.println("Decoded: " + list);

        assertEquals(1, list.size());
        Object value = list.getFirst();
        assertInstanceOf(Map.class, value);
        assertTrue(((Map) value).isEmpty());
    }

    @Test
    public void decodeNestedObject() throws IOException {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("person", Map.of(
                "name", "Alice",
                "address", Map.of(
                        "street", "123 Main St",
                        "city", "Wonderland"
                )
        ));

        System.out.println("Original: " + map1);

        String path = "src/test/resources/json_to_map/nested_object.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        JsonSerializer serializer = new JsonSerializer();
        Map map2 = serializer.decode(file, TreeMap.class);

        System.out.println("Decoded: " + map2);
    }

    @Test
    public void decodeObjectArray() throws IOException {
        String path = "src/test/resources/json_to_map/object_array.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        JsonSerializer serializer = new JsonSerializer();
        List list = serializer.decode(file, List.class);

        System.out.println("Decoded: " + list);
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }

    @Test
    public void decodeObjectWithAllTypes() throws IOException {
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("string", "text");
        map1.put("double", 123.45);
        map1.put("integer", 42);
        map1.put("boolean", true);
        map1.put("null", null);
        map1.put("object", Map.of("key", "value"));
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add("two");
        list.add(null);
        list.add(false);
        map1.put("array", list);

        System.out.println("Original: " + map1);

        String path = "src/test/resources/json_to_map/object_with_all_types.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        JsonSerializer serializer = new JsonSerializer();
        Map map2 = serializer.decode(file, Map.class);

        System.out.println("Decoded: " + map2);
    }

    @Test
    public void decodeObjectWithArrays() {
    }

    @Test
    public void decodeSimpleArray() {
    }

    @Test
    public void decodeSimpleObject() {
    }

    @Test
    public void decodeTimes() {
    }
}
