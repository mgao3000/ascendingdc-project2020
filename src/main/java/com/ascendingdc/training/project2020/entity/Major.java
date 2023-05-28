package com.ascendingdc.training.project2020.entity;

import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "major")
public class Major {

    public Major() {
    }

    public Major(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="name")
    private String name;

    @Column(name ="description")
    private String description;

//  private int age;

    public MajorDto convertMajorToMajorDto() {
        MajorDto majorDto = new MajorDto();
        majorDto.setId(getId());
        majorDto.setName(getName());
        majorDto.setDescription(getDescription());
        List<StudentDto> studentDtoList = getMajorDtoListFromMajorSet(getStudents());
        majorDto.setStudentDtoList(studentDtoList);
        return majorDto;
    }

    private List<StudentDto> getMajorDtoListFromMajorSet(Set<Student> students) {
        List<StudentDto> studentDtoList = new ArrayList<>();
        for(Student student : students) {
            StudentDto studentDto = student.convertStudentToStudentDto();
            studentDtoList.add(studentDto);
        }
        return studentDtoList;
    }

    //    public Major convertMajorDtoToMajor(MajorDto majorDto) {
//        Major major = new Major();
//        if(majorDto.getId() != null)
//            major.setId(majorDto.getId());
//        major.setName(majorDto.getName());
//        major.setDescription(majorDto.getDescription());
//        return major;
//    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @OneToMany(mappedBy = "major", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
//    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "major", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
//    @OneToMany(mappedBy = "major")
//    @OneToMany(mappedBy = "major", fetch = FetchType.EAGER)
///    @OneToMany(mappedBy = "major", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Student> students;


//    @PreRemove
//    private removeStudentFromMajor() {
//        for(Student student : getStudents()) {
//            student.getMajor();
//        }
//    }
    public Set<Student> getStudents() {
        if(students == null)
            students = new HashSet<Student>();
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    /*
     *  this is a convenient utility kind of method to add a student to this specific major
     */
    public void addStudent(Student student) {
        this.getStudents().add(student);
        student.setMajor(this);
    }

    /*
     *  this is a convenient utility kind of method to remove a student to this specific major
     */
    public void removeStudent(Student student) {
        this.getStudents().remove(student);
        student.setMajor(null);
    }

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


//    id                BIGSERIAL NOT NULL,
//    name              VARCHAR(30) not null unique,
//    description       VARCHAR(150)


}
