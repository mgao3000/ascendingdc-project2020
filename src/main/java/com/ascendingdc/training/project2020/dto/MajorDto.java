package com.ascendingdc.training.project2020.dto;

import com.ascendingdc.training.project2020.entity.Major;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private List<StudentDto> studentList = new ArrayList<StudentDto>();

    public Major convertMajorDtoToMajor() {
        Major major = new Major();
        if(getId() != null)
            major.setId(getId());
        major.setName(getName());
        major.setDescription(getDescription());
        return major;
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StudentDto> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDto> studentList) {
        this.studentList = studentList;
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
