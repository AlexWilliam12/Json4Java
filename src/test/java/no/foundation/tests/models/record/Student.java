package no.foundation.tests.models.record;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Student(
        Long id,
        String name,
        List<Course> courses
) {

    @Contract(value = " -> new", pure = true)
    public static @NotNull StudentBuilder builder() {
        return new StudentBuilder();
    }

    public static class StudentBuilder {

        private Long id;
        private String name;
        private List<Course> courses;

        public StudentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StudentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder courses(List<Course> courses) {
            this.courses = courses;
            return this;
        }

        public Student build() {
            return new Student(id, name, courses);
        }
    }
}
