package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dto.DepartmentDto;
import com.ascendingdc.training.project2020.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto save(EmployeeDto employeeDto, DepartmentDto departmentDto);
    EmployeeDto save(EmployeeDto employeeDto, String deptName);
    Integer updateEmployeeAddressByEmployeeName(String name, String address);
    EmployeeDto update(EmployeeDto employeeDto);
    boolean deleteByName(String name);
    boolean delete(EmployeeDto employeeDto);
    List<EmployeeDto> getEmployees();
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto getEmployeeByName(String employeeName);
    EmployeeDto getEmployeeAndDepartmentById(Long id);

}
