package no.foundation.tests.models.record;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Course(
        Long id,
        String name,
        Teacher teacher,
        List<Student> students
) {

    @Contract(value = " -> new", pure = true)
    public static @NotNull CourseBuilder builder() {
        return new CourseBuilder();
    }

    public static class CourseBuilder {

        private Long id;
        private String name;
        private Teacher teacher;
        private List<Student> students;

        public CourseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder teacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder students(List<Student> students) {
            this.students = students;
            return this;
        }

        public Course build() {
            return new Course(id, name, teacher, students);
        }
    }
}
