package no.foundation.tests;

import no.foundation.serializer.JsonSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class JsonDecoderToMapTest {

    private static File directory;

    @BeforeAll
    public static void setup() {
        String path = "src/test/resources/json_to_map";
        directory = Path.of(path).toFile();
    }

    @Test
    public void decodeAllToMap() {
        assertTrue(directory.exists());
        assertTrue(directory.isDirectory());
        assertTrue(directory.canRead());

        File[] files = directory.listFiles();
        assertNotNull(files);
        assertTrue(files.length > 0);

        JsonSerializer serializer = new JsonSerializer();

        for (File file : files) {
            assertTrue(file.isFile());
            assertTrue(file.canRead());

            String content = ReaderUtils.readFile(file);

            assertDoesNotThrow(() -> {
                System.out.println("File name: " + file.getName());
                Object decoded = serializer.decode(content);
                System.out.println("Decoded: " + decoded);
                System.out.println();
            });
        }
    }
}
