package no.foundation.tests;

import no.foundation.serializer.JsonSerializer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class JsonDecoderToObjectTest {

    @Test
    public void decodeToTeacherPojo() {
        String path = "src/test/resources/json_to_object/teacher.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        JsonSerializer serializer = new JsonSerializer();
        String content = ReaderUtils.readFile(file);

        assertDoesNotThrow(() -> {
            System.out.println("File name: " + file.getName());
            no.foundation.tests.models.pojo.Teacher teacher = serializer.decode(content, no.foundation.tests.models.pojo.Teacher.class);
            System.out.println("Decoded: " + teacher);
            System.out.println();
        });
    }

    @Test
    public void decodeToTeacherRecord() {
        String path = "src/test/resources/json_to_object/teacher.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        JsonSerializer serializer = new JsonSerializer();
        String content = ReaderUtils.readFile(file);

        assertDoesNotThrow(() -> {
            System.out.println("File name: " + file.getName());
            no.foundation.tests.models.record.Teacher teacher = serializer.decode(content, no.foundation.tests.models.record.Teacher.class);
            System.out.println("Decoded: " + teacher);
            System.out.println();
        });
    }

    @Test
    public void decodeToStudentPojo() {
        String path = "src/test/resources/json_to_object/student.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        String content = ReaderUtils.readFile(file);
        JsonSerializer serializer = new JsonSerializer();

        assertDoesNotThrow(() -> {
            System.out.println("File name: " + file.getName());
            no.foundation.tests.models.pojo.Student student = serializer.decode(content, no.foundation.tests.models.pojo.Student.class);
            System.out.println("Decoded: " + student);
            System.out.println();
        });
    }

    @Test
    public void decodeToStudentRecord() {
        String path = "src/test/resources/json_to_object/student.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        String content = ReaderUtils.readFile(file);
        JsonSerializer serializer = new JsonSerializer();

        assertDoesNotThrow(() -> {
            System.out.println("File name: " + file.getName());
            no.foundation.tests.models.record.Student student = serializer.decode(content, no.foundation.tests.models.record.Student.class);
            System.out.println("Decoded: " + student);
            System.out.println();
        });
    }

    @Test
    public void decodeToCoursePojo() {
        String path = "src/test/resources/json_to_object/course.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        String content = ReaderUtils.readFile(file);
        JsonSerializer serializer = new JsonSerializer();

        assertDoesNotThrow(() -> {
            System.out.println("File name: " + file.getName());
            no.foundation.tests.models.pojo.Course course = serializer.decode(content, no.foundation.tests.models.pojo.Course.class);
            System.out.println("Decoded: " + course);
            System.out.println();
        });
    }

    @Test
    public void decodeToCourseRecord() {
        String path = "src/test/resources/json_to_object/course.json";
        File file = Path.of(path).toFile();
        assertTrue(file.exists());
        assertTrue(file.isFile());

        assertTrue(file.isFile());
        assertTrue(file.canRead());

        String content = ReaderUtils.readFile(file);
        JsonSerializer serializer = new JsonSerializer();

        assertDoesNotThrow(() -> {
            System.out.println("File name: " + file.getName());
            no.foundation.tests.models.record.Course course = serializer.decode(content, no.foundation.tests.models.record.Course.class);
            System.out.println("Decoded: " + course);
            System.out.println();
        });
    }
}
