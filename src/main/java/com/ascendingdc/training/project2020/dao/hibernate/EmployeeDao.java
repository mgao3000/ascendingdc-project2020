package com.ascendingdc.training.project2020.dao.hibernate;

import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.Employee;

import java.util.List;

public interface EmployeeDao {
    Employee save(Employee employee, Department department);
    Integer updateEmployeeAddressByEmployeeName(String name, String address);
    Employee update(Employee employee);
    boolean deleteByName(String name);
    boolean delete(Employee Employee);
    List<Employee> getEmployees();
    Employee getEmployeeById(Long id);
    Employee getEmployeeByName(String employeeName);
    Employee getEmployeeAndDepartmentById(Long id);

}
