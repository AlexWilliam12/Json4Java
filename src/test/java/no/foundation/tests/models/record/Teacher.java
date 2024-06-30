package no.foundation.tests.models.record;

public record Teacher(
        Long id,
        String name) {

    public static TeacherBuilder builder() {
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
