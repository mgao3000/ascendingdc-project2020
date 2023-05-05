/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.controller;

import com.ascendingdc.training.project2020.dto.AccountDto;
import com.ascendingdc.training.project2020.dto.DepartmentDto;
import com.ascendingdc.training.project2020.dto.DepartmentDetailDto;
import com.ascendingdc.training.project2020.dto.EmployeeDto;
import com.ascendingdc.training.project2020.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = {"/departments", "/depts"})
public class DepartmentController {
//    @Autowired private Logger logger;
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<DepartmentDto> getDepartments() {
//        DepartmentService anotherDepartmentService = new DepartmentService();
//        List<Department> departments = anotherDepartmentService.findAllDepartments();
        List<DepartmentDto> departments = departmentService.getDepartments();
        logger.info("###$$$, departments.size={}", departments.size());
        displayDepartments(departments);
        return departments;
    }

    @RequestMapping(value = "/with-children", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<DepartmentDto> getDepartmentsWithChildren() {
        List<DepartmentDto> departments = departmentService.getDepartmentsWithChildren();
        return departments;
    }

    @RequestMapping(value = "/{deptName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public DepartmentDto getDepartment(@PathVariable String deptName) {
        DepartmentDto department = departmentService.getDepartmentEagerByName(deptName);
        return department;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DepartmentDto createDepartment(@RequestBody DepartmentDto departmentDto) {
        logger.debug("Department: " + departmentDto.toString());
        DepartmentDetailDto departmentDetailDto = departmentDto.getDepartmentDetailDto();
        DepartmentDto savedDepartmentDto = departmentService.save(departmentDto);
        if (savedDepartmentDto != null)
            logger.error("The department was not created., input departmentDto={}", departmentDto);
        return savedDepartmentDto;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public DepartmentDto updateDepartment(@RequestBody DepartmentDto departmentDto) {
        logger.debug("Department: " + departmentDto.toString());
        DepartmentDto updatedDepartmentDto = departmentService.update(departmentDto);
        if (updatedDepartmentDto != null)
            logger.error("The department was not updated. input updatedDepartmentDto = {}", updatedDepartmentDto);
        return updatedDepartmentDto;
    }

//    @RequestMapping(value = "/{deptName}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(value = "/{deptName}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String deleteDepartment(@PathVariable("deptName") String deptName) {
        logger.debug("Department name: " + deptName);
        String msg = "The department with name = " + deptName + " was deleted.";
        boolean isSuccess = departmentService.deleteByName(deptName);
        if (!isSuccess)
            msg = "The department with name = " + deptName + " was not deleted.";
        return msg;
    }

    private void displayDepartments(List<DepartmentDto> departments) {
        int index = 1;
        for(DepartmentDto departmentDto : departments) {
            displayDepartment(index++, departmentDto);
        }
    }

    private void displayDepartment(int i, DepartmentDto departmentDto) {
        logger.info("================== Department No.{} =====================", i);
        logger.info("\t department={}", departmentDto);
        List<EmployeeDto> employeeDtoList = departmentDto.getEmployeeDtoList();
        displayEmployees(employeeDtoList);

    }

    private void displayEmployees(List<EmployeeDto> employees) {
        int index = 1;
        for(EmployeeDto employeeDto : employees) {
            displayEmployee(index++, employeeDto);
        }

    }

    private void displayEmployee(int index, EmployeeDto employeeDto) {
        logger.info("================== Employee No.{} =====================", index);
        logger.info("\t Employee={}", employeeDto);
        List<AccountDto> accountDtoList = employeeDto.getAccountDtoList();
        displayAccounts(accountDtoList);
    }

    private void displayAccounts(List<AccountDto> accountDtoList) {
        int index = 1;
        for(AccountDto accountDto : accountDtoList) {
            displayAccount(index++, accountDto);
        }
    }

    private void displayAccount(int i, AccountDto accountDto) {
        logger.info("================== Account No.{} =====================", i);
        logger.info("\t Account={}", accountDto);
    }
}