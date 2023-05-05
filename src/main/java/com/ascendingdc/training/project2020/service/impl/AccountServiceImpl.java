/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dao.hibernate.AccountDao;
import com.ascendingdc.training.project2020.dto.AccountDto;
import com.ascendingdc.training.project2020.dto.EmployeeDto;
import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Employee;
import com.ascendingdc.training.project2020.service.AccountService;
import com.ascendingdc.training.project2020.util.DtoAndEntityConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    @Qualifier("accountSpringDataJPADao")
    private AccountDao accountDao;

    public AccountDto save(AccountDto accountDto, EmployeeDto employeeDto) {
        Account account = DtoAndEntityConvertUtil.convertAccountDtoToAccount(accountDto);
        Employee employee = DtoAndEntityConvertUtil.convertEmployeeDtoToEmployee(employeeDto);
        Account savedAccount = accountDao.save(account, employee);
        AccountDto savedAccountDto = DtoAndEntityConvertUtil.convertAccountToAccountDto(savedAccount);
        return savedAccountDto;
    }

    public List<AccountDto> getAccounts() {
        List<Account> accountList = accountDao.getAccounts();
        List<AccountDto> accountDtoList = getAccountDtoListByAccountList(accountList);
        return accountDtoList;
    }

    private List<AccountDto> getAccountDtoListByAccountList(List<Account> accountList) {
        List<AccountDto> accountDtoList = new ArrayList<>();
        for(Account account : accountList) {
            AccountDto accountDto = DtoAndEntityConvertUtil.convertAccountToAccountDto(account);
            accountDtoList.add(accountDto);
        }
        return accountDtoList;
    }

    public AccountDto getAccountById(Long id) {
        Account account = accountDao.getAccountById(id);
        AccountDto accountDto = DtoAndEntityConvertUtil.convertAccountToAccountDto(account);
        return accountDto;
    }
}
