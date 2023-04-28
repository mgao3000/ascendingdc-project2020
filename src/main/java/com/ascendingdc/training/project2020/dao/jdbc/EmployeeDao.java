package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.dto.DepartmentDto;
import com.ascendingdc.training.project2020.dto.EmployeeDto;

import java.util.List;

public interface EmployeeDao {
    EmployeeDto save(EmployeeDto employee, DepartmentDto department);
    Integer updateEmployeeAddress(String name, String address);
    EmployeeDto update(EmployeeDto employee);
    boolean deleteByName(String name);
    boolean delete(EmployeeDto Employee);
    List<EmployeeDto> getEmployees();
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto getEmployeeByName(String employeeName);
    EmployeeDto getEmployeeAndDepartmentById(Long id);

}
