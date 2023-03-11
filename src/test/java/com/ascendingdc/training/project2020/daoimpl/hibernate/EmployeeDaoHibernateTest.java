package com.ascendingdc.training.project2020.daoimpl.hibernate;

import com.ascendingdc.training.project2020.dao.hibernate.EmployeeDao;
import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= SpringBootAppInitializer.class)
public class EmployeeDaoHibernateTest extends AbstractDaoHibernateTest {
    private Logger logger = LoggerFactory.getLogger(EmployeeDaoHibernateTest.class);

//    @Autowired
    private EmployeeDao employeeDao;

    private Employee employee;
//    private EmployeeDetail employeeDetail;
    private Account account1;
    private Account account2;

    private String employeeName;

    @BeforeAll
    public static void setupOnce() {
        //employeeDao = new EmployeeDaoImpl();
    }

    @BeforeEach
    public void setup() {
        employeeDao = new EmployeeDaoHibernateImpl();
        employeeName = "rhang";  //""xhuang";
    }

    @AfterEach
    public void teardown() {
        employeeDao = null;
    }

    @Test
    public void testFindAllEmployees() {
        List<Employee> employeeList = employeeDao.getEmployees();
        logger.info("###$$$@@@, the total employee size = {}", employeeList.size());
    }

    @Test
    public void testFindemployeeByName() {
        Employee employee = employeeDao.getEmployeeByName(employeeName);
        assertEquals(employeeName, employee.getName());
    }
}
