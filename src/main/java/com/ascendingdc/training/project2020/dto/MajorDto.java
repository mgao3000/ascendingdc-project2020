package com.ascendingdc.training.project2020.dto;

import com.ascendingdc.training.project2020.entity.Major;
import com.ascendingdc.training.project2020.entity.Student;

import java.util.*;

public class MajorDto {

    public MajorDto() {
    }

    public MajorDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private Long id;

    private String name;

    private String description;

    private List<StudentDto> studentDtoList = new ArrayList<StudentDto>();

    public Major convertMajorDtoToMajor() {
        Major major = new Major();
        if(getId() != null)
            major.setId(getId());
        major.setName(getName());
        major.setDescription(this.getDescription());
        Set<Student> studentSet = getStudentSetByStudentDtoList(this.getStudentDtoList());
        major.setStudents(studentSet);
        return major;
    }

    private Set<Student> getStudentSetByStudentDtoList(List<StudentDto> studentDtoList) {
        Set<Student> studentSet = new HashSet<>();
        for(StudentDto studentDto : studentDtoList) {
            Student student = studentDto.convertStudentDtoToStudent();
            studentSet.add(student);
        }
        return studentSet;
    }

//    public MajorDto convertMajorToMajorDto(Major major) {
//        MajorDto majorDto = new MajorDto();
//        majorDto.setId(major.getId());
//        majorDto.setName(major.getName());
//        majorDto.setDescription(major.getDescription());
//        return majorDto;
//    }

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
//        if(description != null && description.length() > 10) {
//            return description + " mike";
//        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StudentDto> getStudentDtoList() {
        return studentDtoList;
    }

    public void setStudentDtoList(List<StudentDto> studentDtoList) {
        this.studentDtoList = studentDtoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MajorDto)) return false;
        MajorDto major = (MajorDto) o;
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
