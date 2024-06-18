package no.foundation.tests;

import no.foundation.serializer.JsonSerializer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonPrettyEncoderTest {

    @Test
    public void encodeTeacherPojo() {
        no.foundation.tests.models.pojo.Teacher teacher;
        teacher = new no.foundation.tests.models.pojo.Teacher();
        teacher.setId(1L);
        teacher.setName("Oswald");

        System.out.println("Original Object: " + teacher);

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(teacher, true);
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
        String encoded = serializer.encode(teacher, true);
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
        String encoded = serializer.encode(student, true);
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
        String encoded = serializer.encode(student, true);
        assertNotNull(encoded);

        System.out.println("Encoded Object: " + encoded);
    }

    @Test
    public void encodeCoursePojo() {
        no.foundation.tests.models.pojo.Teacher teacher;
        teacher = new no.foundation.tests.models.pojo.Teacher();
        teacher.setId(1L);
        teacher.setName("Oswald");

        no.foundation.tests.models.pojo.Student student1;
        student1 = new no.foundation.tests.models.pojo.Student();
        student1.setId(1L);
        student1.setName("Rick");
        student1.setCourses(List.of());

        no.foundation.tests.models.pojo.Student student2;
        student2 = new no.foundation.tests.models.pojo.Student();
        student2.setId(2L);
        student2.setName("Joe");
        student2.setCourses(List.of());

        no.foundation.tests.models.pojo.Course course;
        course = new no.foundation.tests.models.pojo.Course();
        course.setId(1L);
        course.setName("TADS");
        course.setTeacher(teacher);
        course.setStudents(List.of(student1, student2));

        System.out.println("Original Object: " + course);

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(course, true);
        assertNotNull(encoded);

        System.out.println("Encoded Object: " + encoded);
    }

    @Test
    public void encodeCourseRecord() {
        no.foundation.tests.models.record.Teacher teacher;
        teacher = no.foundation.tests.models.record.Teacher.builder()
                .id(1L)
                .name("Oswald")
                .build();

        no.foundation.tests.models.record.Student student1;
        student1 = no.foundation.tests.models.record.Student.builder()
                .id(1L)
                .name("Rick")
                .courses(List.of())
                .build();

        no.foundation.tests.models.record.Student student2;
        student2 = no.foundation.tests.models.record.Student.builder()
                .id(2L)
                .name("Joe")
                .courses(List.of())
                .build();

        no.foundation.tests.models.record.Course course;
        course = no.foundation.tests.models.record.Course.builder()
                .id(1L)
                .name("TADS")
                .teacher(teacher)
                .students(List.of(student1, student2))
                .build();

        System.out.println("Original Object: " + course);

        JsonSerializer serializer = new JsonSerializer();
        String encoded = serializer.encode(course, true);
        assertNotNull(encoded);

        System.out.println("Encoded Object: " + encoded);
    }
}
