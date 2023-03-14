package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.EmployeeDao;
import com.ascendingdc.training.project2020.daoimpl.repository.EmployeeRepository;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("employeeSpringDataJPADao")
public class EmployeeDaoSpringDataJPAImpl implements EmployeeDao {

    private Logger logger = LoggerFactory.getLogger(EmployeeDaoSpringDataJPAImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee, Department department) {
        department.addEmployee(employee);
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee;
    }

    @Override
    public Integer updateEmployeeAddressByEmployeeName(String name, String address) {
        return employeeRepository.updateAddressByName(address, name);
    }

    @Override
    public Employee update(Employee employee) {
        Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        return employeeRepository.deleteByName(name) > 0;
    }

    @Override
    public boolean delete(Employee employee) {
        boolean successFlag = false;
        try {
            employeeRepository.delete(employee);
            successFlag = true;
        } catch (IllegalArgumentException iae) {
            //do nothing
        } catch (OptimisticLockingFailureException olfe) {
            //do nothing
        }
        return successFlag;
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAllEmployeesWithAssociatedAccounts();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Employee employee = null;
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if(employeeOptional.isPresent()) {
            employee = employeeOptional.get();
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByName(String employeeName) {
        return employeeRepository.findByName(employeeName);
    }

    @Override
    public Employee getEmployeeAndDepartmentById(Long id) {
        return employeeRepository.findEmployeeWithDepartmentByEmployeeId(id);
    }
}
