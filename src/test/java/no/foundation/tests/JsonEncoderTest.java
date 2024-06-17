package no.foundation.tests;

import no.foundation.serializer.JsonSerializer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonEncoderTest {

    @Test
    public void encodeTeacherPojo() {
        no.foundation.tests.models.pojo.Teacher teacher;
        teacher = new no.foundation.tests.models.pojo.Teacher();
        teacher.setId(1L);
        teacher.setName("Oswald");

        System.out.println("Original Object: " + teacher);

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(teacher);
        assertNotNull(encoded);

        System.out.println("Encoded Object: " + encoded);
    }

    @Test
    public void encodeTeacherRecord() {
        no.foundation.tests.models.record.Teacher teacher;
        teacher = no.foundation.tests.models.record.Teacher.builder()
                .id(1L)
                .name("Oswald")
                .build();

        System.out.println("Original Object: " + teacher);

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(teacher);
        assertNotNull(encoded);

        System.out.println("Encoded Object: " + encoded);
    }

    @Test
    public void encodeStudentPojo() {
        no.foundation.tests.models.pojo.Teacher teacher;
        teacher = new no.foundation.tests.models.pojo.Teacher();
        teacher.setId(1L);
        teacher.setName("Oswald");

        no.foundation.tests.models.pojo.Course course;
        course = new no.foundation.tests.models.pojo.Course();
        course.setId(1L);
        course.setName("TADS");
        course.setTeacher(teacher);
        course.setStudents(List.of());

        no.foundation.tests.models.pojo.Student student;
        student = new no.foundation.tests.models.pojo.Student();
        student.setId(1L);
        student.setName("Rick");
        student.setCourses(List.of(course));

        System.out.println("Original Object: " + student);

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(student);
        assertNotNull(encoded);

        System.out.println("Encoded Object: " + encoded);
    }

    @Test
    public void encodeStudentRecord() {
        no.foundation.tests.models.record.Teacher teacher;
        teacher = no.foundation.tests.models.record.Teacher.builder()
                .id(1L)
                .name("Oswald")
                .build();

        no.foundation.tests.models.record.Course course;
        course = no.foundation.tests.models.record.Course.builder()
                .id(1L)
                .name("TADS")
                .teacher(teacher)
                .students(List.of())
                .build();

        no.foundation.tests.models.record.Student student;
        student = no.foundation.tests.models.record.Student.builder()
                .id(1L)
                .name("Rick")
                .courses(List.of(course))
                .build();

        System.out.println("Original Object: " + student);

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(student);
        assertNotNull(encoded);

        System.out.println("Encoded Object: " + encoded);
    }
}
