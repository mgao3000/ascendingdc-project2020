package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dao.hibernate.EmployeeDao;
import com.ascendingdc.training.project2020.dto.DepartmentDto;
import com.ascendingdc.training.project2020.dto.EmployeeDto;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.Employee;
import com.ascendingdc.training.project2020.service.EmployeeService;
import com.ascendingdc.training.project2020.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    @Qualifier("employeeSpringDataJPADao")
    private EmployeeDao employeeDao;

    @Override
    public EmployeeDto save(EmployeeDto employeeDto, DepartmentDto departmentDto) {
        Employee employee = DtoAndEntityConvertUtil.convertEmployeeDtoToEmployee(employeeDto);
        Department department = DtoAndEntityConvertUtil.convertDepartmentDtoToDepartment(departmentDto);
        Employee savedEmployee = employeeDao.save(employee, department);
        EmployeeDto savedEmployeeDto = DtoAndEntityConvertUtil.convertEmployeeToEmployeeDto(savedEmployee);
        return savedEmployeeDto;
    }

    @Override
    public Integer updateEmployeeAddressByEmployeeName(String name, String address) {
        Integer updateResultIntValue = employeeDao.updateEmployeeAddressByEmployeeName(name, address);
        return updateResultIntValue;
    }

    @Override
    public EmployeeDto update(EmployeeDto employeeDto) {
        Employee employee = DtoAndEntityConvertUtil.convertEmployeeDtoToEmployee(employeeDto);
        Employee savedEmployee = employeeDao.update(employee);
        EmployeeDto savedEmployeeDto = DtoAndEntityConvertUtil.convertEmployeeToEmployeeDto(savedEmployee);
        return savedEmployeeDto;
    }

    @Override
    public boolean deleteByName(String name) {
        boolean deleteResult = employeeDao.deleteByName(name);
        return deleteResult;
    }

    @Override
    public boolean delete(EmployeeDto employeeDto) {
        Employee employee = DtoAndEntityConvertUtil.convertEmployeeDtoToEmployee(employeeDto);
        boolean deleteResult = employeeDao.delete(employee);
        return deleteResult;
    }

    @Override
    public List<EmployeeDto> getEmployees() {
        List<Employee> employeeList = employeeDao.getEmployees();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for(Employee employee : employeeList) {
            EmployeeDto employeeDto = DtoAndEntityConvertUtil.convertEmployeeToEmployeeDto(employee);
            employeeDtoList.add(employeeDto);
        }
        return employeeDtoList;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeDao.getEmployeeById(id);
        EmployeeDto employeeDto = DtoAndEntityConvertUtil.convertEmployeeToEmployeeDto(employee);
        return employeeDto;
    }

    @Override
    public EmployeeDto getEmployeeByName(String employeeName) {
        Employee employee = employeeDao.getEmployeeByName(employeeName);
        EmployeeDto employeeDto = DtoAndEntityConvertUtil.convertEmployeeToEmployeeDto(employee);
        return employeeDto;
    }

    @Override
    public EmployeeDto getEmployeeAndDepartmentById(Long id) {
        Employee employee = employeeDao.getEmployeeAndDepartmentById(id);
        Department department = employee.getDepartment();
        EmployeeDto employeeDto = DtoAndEntityConvertUtil.convertEmployeeToEmployeeDto(employee);
        DepartmentDto departmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department);
        employeeDto.setDepartmentDto(departmentDto);
        return employeeDto;
    }
}
