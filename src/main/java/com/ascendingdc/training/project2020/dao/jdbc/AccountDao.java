package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.dto.AccountDto;
import com.ascendingdc.training.project2020.dto.EmployeeDto;

import java.util.List;

public interface AccountDao {
    AccountDto save(AccountDto account, EmployeeDto employee);
    List<AccountDto> getAccounts();
    AccountDto getAccountById(Long id);
}
