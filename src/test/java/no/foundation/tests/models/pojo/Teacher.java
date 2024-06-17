package no.foundation.tests.models.pojo;

import no.foundation.tests.models.ObjectPrinter;

public class Teacher {

    private Long id;
    private String name;

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

    @Override
    public String toString() {
        return ObjectPrinter.print(this);
    }
}
