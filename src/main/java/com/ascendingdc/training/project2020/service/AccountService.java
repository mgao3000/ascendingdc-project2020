package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dto.AccountDto;
import com.ascendingdc.training.project2020.dto.EmployeeDto;
//import com.ascendingdc.training.project2020.entity.Account;
//import com.ascendingdc.training.project2020.entity.Employee;

import java.util.List;

public interface AccountService {
    AccountDto save(AccountDto accountDto, EmployeeDto employeeDto);

    List<AccountDto> getAccounts();

    AccountDto getAccountById(Long id);
}
