package com.ascendingdc.training.project2020.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Major {

    public Major() {
    }

    public Major(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private Long id;

    private String name;

    private String description;

    private List<Student> studentList = new ArrayList<Student>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Major)) return false;
        Major major = (Major) o;
        return Objects.equals(getId(), major.getId()) && Objects.equals(getName(), major.getName()) && Objects.equals(getDescription(), major.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
