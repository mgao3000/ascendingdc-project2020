package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.Employee;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= SpringBootAppInitializer.class)
@SpringBootTest
public class EmployeeDtoDaoSpringDataJPATest extends AbstractDaoSpringDataJPATest {
    private Logger logger = LoggerFactory.getLogger(EmployeeDtoDaoSpringDataJPATest.class);

//    @Autowired
//    @Qualifier("employeeSpringDataJPADao")
//    private EmployeeDao employeeDao;
//
//    @Autowired
//    @Qualifier("employeeSpringDataJPADao")
//    private DepartmentDao departmentDao;

    private Employee employee;
//    private EmployeeDetail employeeDetail;
    private Account account1;
    private Account account2;

    private String employeeName;

    @BeforeAll
    public static void setupOnce() {
    }

    @AfterAll
    public static void teardownOnce() {

    }

    @BeforeEach
    public void setup() {
//        employeeDao = new EmployeeDaoHibernateImpl();
        employeeName = "rhang";  //""xhuang";

    }

//    protected Department getRandomDepartment() {
//        List<Department> departmentList = departmentDao.getDepartments();
//        Department randomDepartment = null;
//        if(departmentList != null && departmentList.size() > 0) {
//            int randomIndex = getRandomInt(0, departmentList.size());
//            randomDepartment = departmentList.get(randomIndex);
//        }
//        return randomDepartment;
//    }

    @AfterEach
    public void teardown() {
//        employeeDao = null;
//        departmentDao = null;
    }

    @Test
    public void testFindAllEmployees() {
        List<Employee> employeeList = employeeDao.getEmployees();
        assertEquals(4, employeeList.size());
        logger.info("###$$$@@@, the total employee size = {}", employeeList.size());
    }

//    @Test
//    public void testFindEmployeeByName() {
//        Employee employee = employeeDao.getEmployeeByName(employeeName);
//        assertEquals(employeeName, employee.getName());
//    }

    @Test
    public void testSaveEmployee() {
        //No.1 Create a temp Department
        int randomInt = getRandomInt(1, 1000);
        Department department = createDepartmentEntity("IT-test-" + randomInt, "Virginia");
        Department departmentSaved = departmentDao.save(department);
        assertNotNull(departmentSaved.getId(), "A saved department should have a ID with NULL value");
        assertEquals(department.getDescription(), departmentSaved.getDescription(), "The description value should be the same");
        logger.info("Department = {}", departmentSaved);
        logger.info("Saved Department = {}", departmentSaved);
//        Department department = getRandomDepartment();

        // No.2 Create a temp Employee
        Employee tempEmployee = createEmployeeEntity("Jhon", "Doe", "105 Spring Road");
        departmentSaved.addEmployee(tempEmployee);
        Employee savedEmployee = employeeDao.save(tempEmployee, departmentSaved);
        assertNotNull(savedEmployee.getId(), "A saved Employee should have a ID with NULL value");
        assertEmployee(savedEmployee, tempEmployee);

        //No.3 Now delete temp Employee and temp Deaprtment
//        departmentSaved.removeEmployee(savedEmployee);
//        boolean deleteFlag = employeeDao.delete(savedEmployee);
//        assertTrue(deleteFlag, "delete Employee should be successful");

        boolean deleteFlag = departmentDao.delete(departmentSaved);
        assertTrue(deleteFlag, "delete Department should be successful");
    }

    @Test
    public void testUpdateEmployeeAddressByEmployeeName() {
        Employee retrievedEmployee = getRandomEmployee();
        String originalAddress = retrievedEmployee.getAddress();
        String modifiedAddress = originalAddress + "_updated";
        int updateResult = employeeDao.updateEmployeeAddressByEmployeeName(retrievedEmployee.getName(),
                modifiedAddress);
        assertTrue(updateResult > 0);
        Employee modifiedEmployee = employeeDao.getEmployeeByName(retrievedEmployee.getName());
        assertEquals(modifiedAddress, modifiedEmployee.getAddress());

        updateResult = employeeDao.updateEmployeeAddressByEmployeeName(retrievedEmployee.getName(),
                originalAddress);
        assertTrue(updateResult > 0);
        Employee secondEmployee = employeeDao.getEmployeeByName(retrievedEmployee.getName());
        assertEquals(originalAddress, secondEmployee.getAddress());

    }

    @Test
    public void testUpdateEmployee() {
        Employee originalEmployee = getRandomEmployee();
        String originalAddress = originalEmployee.getAddress();
        String originalFirstName = originalEmployee.getFirstName();
        String originalLastName = originalEmployee.getLastName();
        String originalName = originalEmployee.getName();

        originalEmployee.setAddress(originalAddress + "_updated");
        originalEmployee.setLastName(originalLastName + "_updated");
        originalEmployee.setFirstName(originalFirstName + "_updated");
        originalEmployee.setName(originalName + "_updated");

        Employee updatedEmployee = employeeDao.update(originalEmployee);

        assertEquals(updatedEmployee.getId(), originalEmployee.getId());
        assertEquals(updatedEmployee.getAddress(), originalAddress + "_updated");
        assertEquals(updatedEmployee.getFirstName(), originalFirstName + "_updated");
        assertEquals(updatedEmployee.getLastName(), originalLastName + "_updated");
        assertEquals(updatedEmployee.getName(), originalName + "_updated");

        //set the values back to original
        originalEmployee.setAddress(originalAddress);
        originalEmployee.setLastName(originalLastName);
        originalEmployee.setFirstName(originalFirstName);
        originalEmployee.setName(originalName);

        Employee backToOriginalEmployee = employeeDao.update(originalEmployee);
        assertEquals(backToOriginalEmployee.getId(), originalEmployee.getId());
        assertEquals(backToOriginalEmployee.getAddress(), originalEmployee.getAddress());
        assertEquals(backToOriginalEmployee.getFirstName(), originalEmployee.getFirstName());
        assertEquals(backToOriginalEmployee.getLastName(), originalEmployee.getLastName());
        assertEquals(backToOriginalEmployee.getName(), originalEmployee.getName());

    }

    @Test
    public void testDeleteEmployeeByName() {
        Department randomDepartment = getRandomDepartment();
        Employee tempemployee = createEmployeeEntity("John", "doe", "123 Road, Fairfax");
        Employee savedEmployee = employeeDao.save(tempemployee, randomDepartment);
        assertNotNull(savedEmployee.getId(), " a successfully saved employee should have a valid ID");

        boolean deleteResult = employeeDao.deleteByName(savedEmployee.getName());
        assertTrue(deleteResult);

        //Now retrieve the deleted employee, should return a null
        Employee retrievedEmployee = employeeDao.getEmployeeById(savedEmployee.getId());
        assertNull(retrievedEmployee);
    }

    @Test
    public void testDeleteEmployee() {
        Department randomDepartment = getRandomDepartment();
        Employee tempemployee = createEmployeeEntity("John", "doe", "123 Road, Fairfax");
        Employee savedEmployee = employeeDao.save(tempemployee, randomDepartment);
        assertNotNull(savedEmployee.getId(), " a successfully saved employee should have a valid ID");

        boolean deleteResult = employeeDao.delete(savedEmployee);
        assertTrue(deleteResult);

        //Now retrieve the deleted employee, should return a null
        Employee retrievedEmployee = employeeDao.getEmployeeById(savedEmployee.getId());
        assertNull(retrievedEmployee);
    }

    @Test
    public void testFindEmployeeById() {
        Employee randomEmployee = getRandomEmployee();
        Employee retrievedEmployee = employeeDao.getEmployeeById(randomEmployee.getId());
        assertEmployee(randomEmployee, retrievedEmployee);
    }

    @Test
    public void testFindEmployeeByName() {
        Employee randomEmployee = getRandomEmployee();
        Employee retrievedEmployee = employeeDao.getEmployeeByName(randomEmployee.getName());
        assertEmployee(randomEmployee, retrievedEmployee);
    }

    @Test
    public void testFindEmployeeAndDepartmentByEmployeeId() {
        Employee randomEmployee = getRandomEmployee();
        Employee retrievedEmployee = employeeDao.getEmployeeAndDepartmentById(randomEmployee.getId());
        assertEmployee(randomEmployee, retrievedEmployee);

        Department associatedDepartment = departmentDao.getDepartmentById(retrievedEmployee.getDepartment().getId());
        assertDepartment(associatedDepartment, retrievedEmployee.getDepartment());
    }

}
