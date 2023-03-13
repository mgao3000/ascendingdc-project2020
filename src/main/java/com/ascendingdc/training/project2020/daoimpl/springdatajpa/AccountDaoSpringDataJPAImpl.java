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
import java.util.Optional;

@Repository("accountSpringDataJPADao")
public class AccountDaoSpringDataJPAImpl implements AccountDao {

    private Logger logger = LoggerFactory.getLogger(AccountDaoSpringDataJPAImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account save(Account account, Employee employee) {
        employee.addAccount(account);
        account.setEmployee(employee);
        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {
        Account account = null;
        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()) {
            account = accountOptional.get();
        }
        return account;
    }
}
