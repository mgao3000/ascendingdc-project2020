/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.controller;

import com.ascendingdc.training.project2020.dto.EmployeeDto;
import com.ascendingdc.training.project2020.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/ems", "/employees"})
public class EmployeeController {
//    @Autowired private Logger logger;
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<EmployeeDto> getEmployees() {
        return employeeService.getEmployees();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public EmployeeDto getEmployeeByName(@PathVariable String name) {
        return employeeService.getEmployeeByName(name);
    }

    @RequestMapping(value = "/department/{deptName}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable String deptName) {
        logger.debug(String.format("Department name: %s, employee: %s", deptName, employeeDto.toString()));
        EmployeeDto savedEmployeeDto = employeeService.save(employeeDto, deptName);
        if (savedEmployeeDto == null)
            logger.error("The employee was not created.");
        return savedEmployeeDto;
    }
}