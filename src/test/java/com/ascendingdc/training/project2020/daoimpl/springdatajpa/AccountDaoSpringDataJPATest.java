package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.daoimpl.hibernate.AbstractDaoHibernateTest;
import com.ascendingdc.training.project2020.daoimpl.hibernate.AccountDaoHibernateImpl;
import com.ascendingdc.training.project2020.daoimpl.hibernate.EmployeeDaoHibernateImpl;
import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Employee;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountDaoSpringDataJPATest extends AbstractDaoSpringDataJPATest {
    private Logger logger = LoggerFactory.getLogger(AccountDaoSpringDataJPATest.class);

    private Employee employee;
    //    private EmployeeDetail employeeDetail;
    private Account account1;
    private Account account2;

    @BeforeAll
    public static void setupOnce() {
    }

    @AfterAll
    public static void teardownOnce() {
    }

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void teardown() {

    }


    @Test
    public void testSaveAccount() {
        //No.1 Create a temp account
        Employee randomEmployee = getRandomEmployee();
        Account account = createAccountEntity("Checking_acct_test", BigDecimal.valueOf(10033));
        Account savedAccount = accountDao.save(account, randomEmployee);
        assertNotNull(savedAccount.getId(), "A saved account should have a ID with NULL value");
        assertEquals(savedAccount.getAccountType(), account.getAccountType());
        assertEquals(savedAccount.getBalance(), account.getBalance());

        //No.2 Now delete the temp created account
        boolean deleteFlag = accountDao.delete(savedAccount);
        assertTrue(deleteFlag, "delete Account should be successful");
    }

    @Test
    public void testUpdateAccount() {
        Account originalAccount = getRandomAccount();
        String originalAccountType = originalAccount.getAccountType();
        BigDecimal originalBalance = originalAccount.getBalance();

        originalAccount.setAccountType(originalAccountType + "_updated");
        originalAccount.setBalance(originalBalance.add(BigDecimal.valueOf(50000)));

        Account updatedAccount = accountDao.update(originalAccount);
        assertAccount(updatedAccount, originalAccount);

        //set the values back to original
        originalAccount.setAccountType(originalAccountType);
        originalAccount.setBalance(originalBalance);

        Account backToOriginalAccount = accountDao.update(originalAccount);
        assertAccount(backToOriginalAccount, originalAccount);
    }

    @Test
    public void testDeleteAccount() {
        //No.1 Create a temp account
        Employee randomEmployee = getRandomEmployee();
        Account account = createAccountEntity("Checking_acct_test", BigDecimal.valueOf(10033));
        Account savedAccount = accountDao.save(account, randomEmployee);
        assertNotNull(savedAccount.getId(), "A saved account should have a ID with NULL value");
        assertEquals(savedAccount.getAccountType(), account.getAccountType());
        assertEquals(savedAccount.getBalance(), account.getBalance());

        boolean deleteFlag = accountDao.delete(savedAccount);
        assertTrue(deleteFlag);

        //Now retrieve the deleted account, should return a null
        Account retrievedAccount = accountDao.getAccountById(savedAccount.getId());
        assertNull(retrievedAccount);
    }

    @Test
    public void testFindAllAccounts() {
        List<Account> accountList = accountDao.getAccounts();
        assertEquals(4, accountList.size());
        logger.info("###$$$@@@, the total account size = {}", accountList.size());
    }

    @Test
    public void testFindAccountById() {
        Account randomAccount = getRandomAccount();
        Account retrievedAccount = accountDao.getAccountById(randomAccount.getId());
        assertAccount(randomAccount, retrievedAccount);
    }

    @Test
    public void testFindAccountAndEmployeeByAccountId() {
        Account randomAccount = getRandomAccount();
        Account retrievedAccount = accountDao.findAccountAndEmployeeByAccountId(randomAccount.getId());
        assertAccount(randomAccount, retrievedAccount);

        Employee associatedEmployee = employeeDao.getEmployeeById(retrievedAccount.getEmployee().getId());
        assertEmployee(associatedEmployee, retrievedAccount.getEmployee());
    }
}
