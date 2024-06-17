package no.foundation.tests.models.pojo;

import no.foundation.tests.models.ObjectPrinter;

import java.util.List;

public class Student {

    private Long id;
    private String name;
    private List<Course> courses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return ObjectPrinter.print(this);
    }
}
