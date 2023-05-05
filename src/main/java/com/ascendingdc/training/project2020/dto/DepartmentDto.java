package com.ascendingdc.training.project2020.dto;

import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.DepartmentDetail;
import com.ascendingdc.training.project2020.entity.Employee;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    private DepartmentDetailDto departmentDetailDto;

    public Department convertDepartmentDtoToDepartment() {
        Department department = new Department();
        if(getId() != null) {
            department.setId(getId());
        }
        department.setDescription(getDescription());
        department.setName(getName());
        department.setLocation(getLocation());
        DepartmentDetail departmentDetail = getDepartmentDetailByDepartmentDetailDto(getDepartmentDetailDto());
        Set<Employee> employeeSet = getEmployeeSetByEmployeeDtoList(getEmployeeDtoList());
        department.setDepartmentDetail(departmentDetail);
        department.setEmployees(employeeSet);
        return department;
    }

    private Set<Employee> getEmployeeSetByEmployeeDtoList(List<EmployeeDto> employeeDtoList) {
        Set<Employee> employeeSet = new HashSet<>();
        for(EmployeeDto employeeDto : employeeDtoList) {
            Employee employee = convertEmployeeDtoToEmployee(employeeDto);
            employeeSet.add(employee);
        }
        return employeeSet;
    }

    private Employee convertEmployeeDtoToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        if(employeeDto.getId() != null)
            employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setAddress(employeeDto.getAddress());
        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setHiredDate(employeeDto.getHiredDate());
        Set<Account> accountSet = getAccountSetByAccountDtoList(employeeDto.getAccountDtoList());
        employee.setAccounts(accountSet);
        return employee;
    }

    private Set<Account> getAccountSetByAccountDtoList(List<AccountDto> accountDtoList) {
        Set<Account> accountset = new HashSet<>();
        for(AccountDto accountDto : accountDtoList) {
            Account account = convertAccountDtoToAccount(accountDto);
            accountset.add(account);
        }
        return accountset;
    }

    private Account convertAccountDtoToAccount(AccountDto accountDto) {
        Account account = new Account();
        if(accountDto.getId() != null)
            account.setId(accountDto.getId());
        account.setBalance(accountDto.getBalance());
        account.setAccountType(accountDto.getAccountType());
        return account;
    }

    private DepartmentDetail getDepartmentDetailByDepartmentDetailDto(DepartmentDetailDto departmentDetailDto) {
        DepartmentDetail departmentDetail = convertDepartmentDetailDtoToDepartmentDetail(departmentDetailDto);
        return departmentDetail;
    }

    private DepartmentDetail convertDepartmentDetailDtoToDepartmentDetail(DepartmentDetailDto departmentDetailDto) {
        DepartmentDetail departmentDetail = new DepartmentDetail();
        departmentDetail.setDescription(departmentDetailDto.getDescription());
        if(departmentDetailDto.getId() != null)
            departmentDetail.setId(departmentDetailDto.getId());
        departmentDetail.setRevenue(departmentDetailDto.getRevenue());
        departmentDetail.setSize(departmentDetailDto.getSize());
        return departmentDetail;
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

    public DepartmentDetailDto getDepartmentDetailDto() {
        return departmentDetailDto;
    }

    public void setDepartmentDetailDto(DepartmentDetailDto departmentDetailDto) {
        this.departmentDetailDto = departmentDetailDto;
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
