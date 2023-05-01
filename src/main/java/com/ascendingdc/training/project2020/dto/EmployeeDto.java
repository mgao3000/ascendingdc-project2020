package com.ascendingdc.training.project2020.dto;

import com.ascendingdc.training.project2020.entity.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class EmployeeDto {
    public EmployeeDto() { }
    public EmployeeDto(String name, String firstName, String lastName, String email, String address) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    private Long id;

    private String name;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private LocalDate hiredDate;

    private DepartmentDto departmentDto;

    public Employee convertEmployeeDtoToEmployee() {
        Employee employee = new Employee();
        if(getId() != null)
            employee.setId(getId());
        employee.setName(getName());
        employee.setAddress(getAddress());
        employee.setEmail(getEmail());
        employee.setFirstName(getFirstName());
        employee.setLastName(getLastName());
        employee.setHiredDate(getHiredDate());
        return employee;
    }

    public DepartmentDto getDepartmentDto() {
        return departmentDto;
    }

    public void setDepartmentDto(DepartmentDto departmentDto) {
        this.departmentDto = departmentDto;
    }

    //    private Date hiredDate;

//    private Long departmentId;

//    public Long getDepartmentId() {
//        return departmentId;
//    }
//
//    public void setDepartmentId(Long departmentId) {
//        this.departmentId = departmentId;
//    }

    public List<AccountDto> getAccountDtoList() {
        return accountDtoList;
    }

    public void setAccountDtoList(List<AccountDto> accountDtoList) {
        this.accountDtoList = accountDtoList;
    }

    private List<AccountDto> accountDtoList;

    public LocalDate getHiredDate() {
//    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDate hiredDate) {
//    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto employee = (EmployeeDto) o;
        return id == employee.id &&
                name.equals(employee.name) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(address, employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstName, lastName, email, address);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
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
