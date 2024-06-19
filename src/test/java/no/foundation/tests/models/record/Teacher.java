package no.foundation.tests.models.record;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Teacher(
        Long id,
        String name
) {

    @Contract(value = " -> new", pure = true)
    public static @NotNull TeacherBuilder builder() {
        return new TeacherBuilder();
    }

    public static class TeacherBuilder {
        private Long id;
        private String name;

        public TeacherBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TeacherBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Teacher build() {
            return new Teacher(id, name);
        }
    }
}
