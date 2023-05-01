package com.ascendingdc.training.project2020.dto;

import com.ascendingdc.training.project2020.entity.Department;

import java.util.List;
import java.util.Objects;

public class DepartmentDto {
    public DepartmentDto() {}
    public DepartmentDto(String name, String description, String location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    private Long id;

    private String name;

    private String description;

    private String location;

    private List<EmployeeDto> employeeDtoList;

    public Department convertDepartmentDtoToDepartment() {
        Department department = new Department();
        if(getId() != null) {
            department.setId(getId());
        }
        department.setDescription(getDescription());
        department.setName(getName());
        department.setLocation(getLocation());
        return department;
    }

    public List<EmployeeDto> getEmployeeDtoList() {
        return employeeDtoList;
    }

    public void setEmployeeDtoList(List<EmployeeDto> employeeDtoList) {
        this.employeeDtoList = employeeDtoList;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDto that = (DepartmentDto) o;
        return id == that.id &&
                name.equals(that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, location);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
               '}';
    }

//    @Override
//    public String toString() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String str = null;
//        try {
//            str = objectMapper.writeValueAsString(this);
//        }
//        catch(JsonProcessingException jpe) {
//            jpe.printStackTrace();
//        }
//
//        return str;
//    }


}
