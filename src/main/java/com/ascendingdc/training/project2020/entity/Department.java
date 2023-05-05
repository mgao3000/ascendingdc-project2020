package com.ascendingdc.training.project2020.entity;

import com.ascendingdc.training.project2020.dto.AccountDto;
import com.ascendingdc.training.project2020.dto.DepartmentDetailDto;
import com.ascendingdc.training.project2020.dto.DepartmentDto;
import com.ascendingdc.training.project2020.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
//import jarkarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "department", schema = "public")
//@Table()
public class Department {
    public Department() {}
    public Department(String name, String description, String location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    @Id
//    //@SequenceGenerator(name = "department_id_generator", sequenceName = "department_id_seq", allocationSize = 1)
//    //@GeneratedValue(strategy = SEQUENCE, generator = "department_id_generator")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
//    @Cascade({CascadeType.ALL})
//    @Fetch(value = FetchMode.SUBSELECT)
 //   @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
///    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
//    @OneToMany(mappedBy = "department", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "department", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)   // not working !!!
//    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Employee> employees;

    public void addEmployee(Employee employee) {
        this.getEmployees().add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        this.getEmployees().remove(employee);
        employee.setDepartment(null);
    }

//    @OneToOne(fetch = FetchType.EAGER, mappedBy = "department", cascade = CascadeType.ALL) fetch = FetchType.LAZY,
//    @OneToOne(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @OneToOne(mappedBy = "department", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true, fetch = FetchType.EAGER)
    @OneToOne(mappedBy = "department", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private DepartmentDetail departmentDetail;

    public DepartmentDto convertDepartmentToDepartmentDto() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDescription(getDescription());
        departmentDto.setId(getId());
        departmentDto.setLocation(getLocation());
        departmentDto.setName(getName());
        List<EmployeeDto> employeeDtoList = getEmployeeDtoList(getEmployees());
        departmentDto.setEmployeeDtoList(employeeDtoList);
        DepartmentDetailDto departmentDetailDto = getDepartmentDetailDtoByDepartmentDetail(getDepartmentDetail());
        departmentDto.setDepartmentDetailDto(departmentDetailDto);
        return departmentDto;
    }

    private DepartmentDetailDto getDepartmentDetailDtoByDepartmentDetail(DepartmentDetail departmentDetail) {
        DepartmentDetailDto departmentDetailDto = new DepartmentDetailDto();
        departmentDetailDto.setId(departmentDetail.getId());
        departmentDetailDto.setDescription(departmentDetail.getDescription());
        departmentDetailDto.setRevenue(departmentDetail.getRevenue());
        departmentDetailDto.setSize(departmentDetail.getSize());
        return departmentDetailDto;
    }

    private List<EmployeeDto> getEmployeeDtoList(Set<Employee> employees) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        if(employees != null) {
            for (Employee employee : employees) {
                EmployeeDto employeeDto = employee.convertEmployeeToEmployeeDto();
                List<AccountDto> accountDtoList = getAccountDtoList(employee.getAccounts());
                employeeDto.setAccountDtoList(accountDtoList);
                employeeDtoList.add(employeeDto);
            }
        }
        return employeeDtoList;
    }

    private List<AccountDto> getAccountDtoList(Set<Account> accounts) {
        List<AccountDto> accountDtoList = new ArrayList<>();
        if(accounts != null) {
            for(Account account : accounts) {
                AccountDto accountDto = account.convertAccountToAccountDto();
                accountDtoList.add(accountDto);
            }
        }
        return accountDtoList;
    }

    public DepartmentDetail getDepartmentDetail() {
        return departmentDetail;
    }

    public void setDepartmentDetail(DepartmentDetail departmentDetail) {
        this.departmentDetail = departmentDetail;
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

    public Set<Employee> getEmployees() {
//        try {
//            int size = employees.size();
//        }
//        catch (Exception e) {
//            return null;
//        }
        if(employees == null)
            employees = new HashSet<Employee>();
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
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
//                ", No. of Employee ='" + getEmployees().size() + '\'' +
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
