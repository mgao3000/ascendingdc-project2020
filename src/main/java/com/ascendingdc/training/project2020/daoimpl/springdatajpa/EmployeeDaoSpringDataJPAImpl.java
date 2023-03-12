package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.EmployeeDao;
import com.ascendingdc.training.project2020.daoimpl.repository.EmployeeRepository;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository("employeeSpringDataJPADao")
public class EmployeeDaoSpringDataJPAImpl implements EmployeeDao {

    private Logger logger = LoggerFactory.getLogger(EmployeeDaoSpringDataJPAImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee, Department department) {
        return null;
    }

    @Override
    public Integer updateEmployeeAddress(String name, String address) {
        return null;
    }

    @Override
    public Employee update(Employee employee) {
        return null;
    }

    @Override
    public boolean deleteByName(String name) {
        return false;
    }

    @Override
    public boolean delete(Employee Employee) {
        return false;
    }

    @Override
    public List<Employee> getEmployees() {
        return null;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return null;
    }

    @Override
    public Employee getEmployeeByName(String employeeName) {
        return null;
    }

    @Override
    public Employee getEmployeeAndDepartmentById(Long id) {
        return null;
    }
}
