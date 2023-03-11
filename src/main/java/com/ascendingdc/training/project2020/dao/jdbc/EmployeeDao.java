package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.model.Department;
import com.ascendingdc.training.project2020.model.Employee;

import java.util.List;

public interface EmployeeDao {
    Employee save(Employee employee, Department department);
    Integer updateEmployeeAddress(String name, String address);
    Employee update(Employee employee);
    boolean deleteByName(String name);
    boolean delete(Employee Employee);
    List<Employee> getEmployees();
    Employee getEmployeeById(Long id);
    Employee getEmployeeByName(String employeeName);
    Employee getEmployeeAndDepartmentById(Long id);

}
