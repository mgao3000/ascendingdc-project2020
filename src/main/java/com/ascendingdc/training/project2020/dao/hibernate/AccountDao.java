package com.ascendingdc.training.project2020.dao.hibernate;

import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Employee;

import java.util.List;

public interface AccountDao {
    Account save(Account account, Employee employee);
    List<Account> getAccounts();
    Account getAccountById(Long id);
}
