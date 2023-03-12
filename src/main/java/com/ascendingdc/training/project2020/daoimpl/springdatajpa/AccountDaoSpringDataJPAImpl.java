package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.AccountDao;
import com.ascendingdc.training.project2020.daoimpl.repository.AccountRepository;
import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository("accountSpringDataJPADao")
public class AccountDaoSpringDataJPAImpl implements AccountDao {

    private Logger logger = LoggerFactory.getLogger(AccountDaoSpringDataJPAImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account save(Account account, Employee employee) {
        return null;
    }

    @Override
    public List<Account> getAccounts() {
        return null;
    }

    @Override
    public Account getAccountById(Long id) {
        return null;
    }
}
