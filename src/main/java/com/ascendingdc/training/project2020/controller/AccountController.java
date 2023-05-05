/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.controller;

import com.ascendingdc.training.project2020.dto.AccountDto;
import com.ascendingdc.training.project2020.dto.EmployeeDto;
import com.ascendingdc.training.project2020.service.AccountService;
import com.ascendingdc.training.project2020.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = {"/accounts"})
public class AccountController {
//    @Autowired private Logger logger;
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmployeeService employeeService;

    //@GetMapping(value = "", produces = "application/json")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<AccountDto> getAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping(value = "/employee/{employeeName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<AccountDto> getAccount(@PathVariable String employeeName) {
        EmployeeDto employeeDto = employeeService.getEmployeeByName(employeeName);
        List<AccountDto> accountDtoList = employeeDto.getAccountDtoList();
        return accountDtoList;
    }

    //@PostMapping(value = "/{employeeName}", consumes = "application/json")
    @RequestMapping(value = "/employee/{employeeName}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String createAccount(@PathVariable String employeeName, @RequestBody AccountDto accountDto) {
        logger.debug(String.format("Employee name: %s, account: %s", employeeName, accountDto.toString()));
        String msg = "The account was created.";
        EmployeeDto employeeDto = employeeService.getEmployeeByName(employeeName);
        AccountDto savedAccountDto = accountService.save(accountDto, employeeDto);
        if (savedAccountDto == null) {
            logger.error("The account was not created.");
            msg = "The account was not created.";
        }
        return msg;
    }
}