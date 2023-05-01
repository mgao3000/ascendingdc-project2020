package com.ascendingdc.training.project2020.util;

import com.ascendingdc.training.project2020.dto.*;
import com.ascendingdc.training.project2020.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DtoAndEntityConvertUtil {

    public static Department convertDepartmentDtoToDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        if(departmentDto.getId() != null) {
            department.setId(departmentDto.getId());
        }
        department.setDescription(departmentDto.getDescription());
        department.setName(departmentDto.getName());
        department.setLocation(departmentDto.getLocation());
        return department;
    }

    public static DepartmentDto convertDepartmentToDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDescription(department.getDescription());
        departmentDto.setId(department.getId());
        departmentDto.setLocation(department.getLocation());
        departmentDto.setName(department.getName());
        List<EmployeeDto> employeeDtoList = getEmployeeDtoList(department.getEmployees());
        departmentDto.setEmployeeDtoList(employeeDtoList);
        return departmentDto;
    }

    public static List<EmployeeDto> getEmployeeDtoList(Set<Employee> employees) {
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

    private static List<AccountDto> getAccountDtoList(Set<Account> accounts) {
        List<AccountDto> accountDtoList = new ArrayList<>();
        if(accounts != null) {
            for(Account account : accounts) {
                AccountDto accountDto = account.convertAccountToAccountDto();
                accountDtoList.add(accountDto);
            }
        }
        return accountDtoList;
    }

    public static DepartmentDetail convertDepartmentDetailDtoToDepartmentDetail(DepartmentDetailDto departmentDetailDto) {
        DepartmentDetail departmentDetail = new DepartmentDetail();
        departmentDetail.setDescription(departmentDetailDto.getDescription());
        if(departmentDetailDto.getId() != null)
            departmentDetail.setId(departmentDetailDto.getId());
        departmentDetail.setRevenue(departmentDetailDto.getRevenue());
        departmentDetail.setSize(departmentDetailDto.getSize());
        return departmentDetail;
    }

    public static DepartmentDetailDto convertDepartmentDetailToDepartmentDetailDto(DepartmentDetail departmentDetail) {
        DepartmentDetailDto departmentDetailDto = new DepartmentDetailDto();
        departmentDetailDto.setId(departmentDetail.getId());
        departmentDetailDto.setDescription(departmentDetail.getDescription());
        departmentDetailDto.setRevenue(departmentDetail.getRevenue());
        departmentDetailDto.setSize(departmentDetail.getSize());
        return departmentDetailDto;
    }

    public static Account convertAccountDtoToAccount(AccountDto accountDto) {
        Account account = new Account();
        if(accountDto.getId() != null)
            account.setId(accountDto.getId());
        account.setBalance(accountDto.getBalance());
        account.setAccountType(accountDto.getAccountType());
        return account;
    }

    public static AccountDto convertAccountToAccountDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBalance(account.getBalance());
        return accountDto;
    }

    public static Employee convertEmployeeDtoToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        if(employeeDto.getId() != null)
            employee.setId(employeeDto.getId());
        employee.setName(employeeDto.getName());
        employee.setAddress(employeeDto.getAddress());
        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setHiredDate(employeeDto.getHiredDate());
        return employee;
    }

    public static EmployeeDto convertEmployeeToEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setHiredDate(employee.getHiredDate());
        return employeeDto;
    }

    public static Major convertMajorDtoToMajor(MajorDto majorDto) {
        Major major = new Major();
        if(majorDto.getId() != null)
            major.setId(majorDto.getId());
        major.setName(majorDto.getName());
        major.setDescription(majorDto.getDescription());
        return major;
    }

    public static MajorDto convertMajorToMajorDto(Major major) {
        MajorDto majorDto = new MajorDto();
        majorDto.setId(major.getId());
        majorDto.setName(major.getName());
        majorDto.setDescription(major.getDescription());
        return majorDto;
    }

    public static Student convertStudentDtoToStudent(StudentDto studentDto) {
        Student student = new Student();
        if(studentDto.getId() != null)
            student.setId(studentDto.getId());
        student.setAddress(studentDto.getAddress());
        student.setEmail(studentDto.getEmail());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setLoginName(studentDto.getLoginName());
        student.setEnrolledDate(studentDto.getEnrolledDate());
        student.setPassword(studentDto.getPassword());
        return student;
    }

    public static StudentDto convertStudentToStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setLoginName(student.getLoginName());
        studentDto.setPassword(student.getPassword());
        studentDto.setEmail(student.getEmail());
        studentDto.setAddress(student.getAddress());
        studentDto.setEnrolledDate(student.getEnrolledDate());
        return studentDto;
    }

    public static Project convertProjectDtoToProject(ProjectDto projectDto) {
        Project project = new Project();
        if(projectDto.getId() != null)
            project.setId(projectDto.getId());
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setCreateDate(projectDto.getCreateDate());
        return project;
    }

    public static ProjectDto convertProjectToProjectDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setCreateDate(project.getCreateDate());
        return projectDto;
    }
}
