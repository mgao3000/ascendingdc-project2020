package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.model.Account;
import com.ascendingdc.training.project2020.model.Employee;

import java.util.List;

public interface AccountDao {
    Account save(Account account, Employee employee);
    List<Account> getAccounts();
    Account getAccountById(Long id);
}
