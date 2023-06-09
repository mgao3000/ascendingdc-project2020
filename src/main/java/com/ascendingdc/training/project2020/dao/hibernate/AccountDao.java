package com.ascendingdc.training.project2020.dao.hibernate;

import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Employee;

import java.util.List;

public interface AccountDao {
    Account save(Account account, Employee employee);

    Account update(Account account);

    boolean delete(Account account);

    List<Account> getAccounts();
    Account getAccountById(Long id);

    Account findAccountAndEmployeeByAccountId(Long id);
}
