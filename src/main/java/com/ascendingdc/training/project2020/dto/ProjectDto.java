package com.ascendingdc.training.project2020.dto;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.ascendingdc.training.project2020.entity.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectDto {
//    private Logger logger = LoggerFactory.getLogger(Project.class);

    public ProjectDto() {
    }

    public ProjectDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private Long id;

    private String name;

    private String description;

    private LocalDate createDate;

    private List<StudentDto> studentDtoList = new ArrayList<StudentDto>();

    public Project convertProjectDtoToProject() {
        Project project = new Project();
        if(getId() != null)
            project.setId(getId());
        project.setName(getName());
        project.setDescription(getDescription());
        project.setCreateDate(getCreateDate());
        return project;
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
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
        if (!(o instanceof ProjectDto)) return false;
        ProjectDto project = (ProjectDto) o;
        return Objects.equals(getId(), project.getId()) && Objects.equals(getName(), project.getName()) && Objects.equals(getDescription(), project.getDescription()) && Objects.equals(getCreateDate(), project.getCreateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getCreateDate());
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                '}';
    }

//    @Override
//    public int compareTo(Project project) {
//        int equalIntValue = 0;
//        if(project != null && project.getId() != null && this.getId() != null)
//            equalIntValue = (int)(this.getId() - project.getId());
//        return equalIntValue;
//    }
}
