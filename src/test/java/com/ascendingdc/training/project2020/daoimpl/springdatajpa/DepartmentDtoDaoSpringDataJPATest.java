package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.DepartmentDetail;
import com.ascendingdc.training.project2020.entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= SpringBootAppInitializer.class)
@SpringBootTest
public class DepartmentDtoDaoSpringDataJPATest extends AbstractDaoSpringDataJPATest {
    private Logger logger = LoggerFactory.getLogger(DepartmentDtoDaoSpringDataJPATest.class);

//    @Autowired
//    @Qualifier("departmentSpringDataJPADao")
//    private DepartmentDao departmentDao;

//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;

//    @Autowired

    private Department testDept;
    private String deptName;
    private Employee emp1;
    private Employee emp2;
    private Account acct1;
    private Account acct2;

    private DepartmentDetail deptDetail;

    @BeforeAll
    public static void setupOnce() {

        //departmentDao = new DepartmentDaoImpl();
    }

    @BeforeEach
    public void setup() {
//        departmentDao = new DepartmentDaoHibernateImpl();

        deptName = "HR-Test";
        /*
        Logic 1: create a department
        Logic 2. create two employees
        Logic 3. add two employees to the department
        Logic 4. use departmentDao to save department. Attention: the associated
                 two employees are saved too due to cascade saving feature
         */
        testDept = createDepartmentEntity(deptName, "Fairfax, VA");

        emp1 = createEmployeeEntity("Doe", "Joe", "123 Road, VA");
        emp2 = createEmployeeEntity("Frank", "Wolf", "456 Road, VA");

        acct1 = createAccountEntity("emp1-acct-type", BigDecimal.valueOf(10001.1));

//        emp1.getAccounts().add(acct1);
//        acct1.setEmployee(emp1);
        emp1.addAccount(acct1);

//        testDept.getEmployees().add(emp1);
//        emp1.setDepartment(testDept);
        testDept.addEmployee(emp1);

//        testDept.getEmployees().add(emp2);
//        emp2.setDepartment(testDept);
        testDept.addEmployee(emp2);

        acct2 = createAccountEntity("emp2-acct-type", BigDecimal.valueOf(20001.2));

//        emp2.getAccounts().add(acct2);
//        acct2.setEmployee(emp2);
        emp2.addAccount(acct2);

        deptDetail = createDepartmentDetailEntity(2222, 101, "dept detail dummy desc");

        testDept.setDepartmentDetail(deptDetail);
        deptDetail.setDepartment(testDept);

        //Now do the final part, save both dept1 and two employees in one shot
        testDept = departmentDao.save(testDept);
        logger.info("==== within setup, testDept.getId()={}", testDept.getId());
        logger.info("==== within setup, testDept={}", testDept);
    }

    @Test
    public void dummy() {
        logger.info("========= dummy test");
    }

    @AfterEach
    public void teardown() {
///        Department retrievedDept = departmentDao.getDepartmentAndEmployeesAndAccounts(testDept.getName());
//        Set<Employee> employeeSet = retrievedDept.getEmployees();
//        for(Employee employee : employeeSet) {
////            Set<Account> accountSet = employee.getAccounts();
////            for(Account account : accountSet) {
////                accountRepository.delete(account);
////            }
//            employeeRepository.delete(employee);
//        }
///        departmentDao.delete(retrievedDept);
        departmentDao.delete(testDept);
    }

    @Test
    public void testUpdateDepartment() {
        testDept.setDescription(testDept.getDescription() + "_update");
        testDept.setLocation(testDept.getLocation() + "_update");
        Department updatedDepartment = departmentDao.update(testDept);
        logger.info(" ===== Now, updatedDepartment = {}", updatedDepartment);
        assertEquals(testDept.getDescription(), updatedDepartment.getDescription());
        assertEquals(testDept.getLocation(), updatedDepartment.getLocation());
    }

    @Test
    public void verifySavingDepartmentWithEmployeesTest() {
//        logger.info("==== within verifySavingDepartmentWithEmployeesTest, testDept.getId()={}", testDept.getId());
        Department dept = departmentDao.getDepartmentEagerByName(deptName);
        assertEquals(deptName, deptName);
        assertEquals(2, dept.getEmployees().size());
        logger.info("###@@@+++  retrieved department = {}", dept);
//        for(Employee emp : dept1.getEmployees()) {
//            logger.info("###@@@+++  With Department={}, associated employee={}",
//                    dept.getName(), emp);
//        }
    }

    @Test
    public void getDepartmentEagerByDeptIdTest() {
//        logger.info("==== within getDepartmentEagerByDeptIdTest, testDept.getId()={}", testDept.getId());
        Department department = departmentDao.getDepartmentEagerByDeptId(testDept.getId());
        assertNotNull(department);
        assertEquals(department.getName(), testDept.getName());
        assertTrue(department.getEmployees().size() > 0);
        assertEquals(2, department.getEmployees().size());
    }

    @Test
    public void getDepartmentsTest() {
        List<Department> departmentList = departmentDao.getDepartments();
        assertEquals(5, departmentList.size(), "There should be only 5 departments");
//        displayDeptWithEmployeeInfo(departmentList);
    }

    @Test
    public void getDepartmentsWithoutJoinFetchHQLTest() {
        List<Department> departmentList = departmentDao.getDepartmentsWithoutJoinFetch();
        assertEquals(5, departmentList.size());
        displayDepartmentListWithoutChildren(departmentList);
    }

    private void displayDepartmentListWithoutChildren(List<Department> departmentList) {
        int index = 1;
        for(Department department : departmentList) {
            logger.info("===== Department No.{} = {}", index++, department);
        }
    }

    @Test
    public void getDepartmentByNameTest() {
        deptName = "HR";
        Department department = departmentDao.getDepartmentEagerByName(deptName);
        assertEquals(deptName, department.getName());
        logger.info("== retrieved department ={}", department);
        logger.info("==Associated employee size={}", department.getEmployees().size());
//        for(Employee emp : department.getEmployees()) {
//            logger.info("###@@@+++  With Department={}, associated employee={}",
//                    department.getName(), emp);
//        }
    }

    @Test
    public void testGetDepartmentAndEmployeesByDeptName() {
        Department department = departmentDao.getDepartmentAndEmployeesByDeptName(deptName);
        displayDeptWithEmployeeInfoOnly(department);
    }

//    private void displayObjectList(List<Department> resultList) {
//        logger.info("=========== The total Object list size = {}", resultList.size());
//        for(Department dept : resultList) {
//            logger.info("======== objectArray={}", dept);
//        }
//    }

    private void displayDepartmentListWithEmployeeInfo(List<Department> departmentList) {
        int deptIndex = 1;
        for(Department department : departmentList) {
            logger.info("@@@###===  Dept No.{} = {}", deptIndex++, department);
            displayDeptWithEmployeeInfoOnly(department);
        }
    }

    private void displayDeptWithEmployeeInfoOnly(Department department) {
        Set<Employee> employeeSet = department.getEmployees();
        int employeeIndex = 1;
        for(Employee employee : employeeSet) {
            logger.info("\t@@@###===  Employee No.{} = {}", employeeIndex++, employee);
            logger.info("===========================");
        }
    }

    private void displayDeptWithEmployeeInfo(Department department) {
        Set<Employee> employeeSet = department.getEmployees();
        int employeeIndex = 1;
        for(Employee employee : employeeSet) {
            logger.info("\t@@@###===  Employee No.{} = {}", employeeIndex++, employee);
            int accountIndex = 1;
            for (Account account : employee.getAccounts()) {
                logger.info("\t\t@@@###===  Acount No.{} = {}", accountIndex++, account);
            }
            logger.info("===========================");
        }
    }

    private void displayDepartmentList(List<Department> deptList) {
        int deptIndex = 1;
        for(Department department : deptList) {
            logger.info("@@@###===   Dept No.{} = {}", deptIndex++, department);
            logger.info("===========================");
        }
    }

    @Test
    public void getDepartmentByIdTest() {
        Department retrievedDepartment = departmentDao.getDepartmentById(testDept.getId());
        assertEquals(retrievedDepartment.getId(), testDept.getId(), "id should be same");
        assertEquals(retrievedDepartment.getDescription(), testDept.getDescription(), "description should be same");

    }

    @Test
    public void deleteDepartmentTest() {
        String deptName = "AAA";
        String empName1 = "Frank";
        String empName2 = "Joe";
        Department department = createDepartmentWithEmployees(deptName, empName1, empName2);
        boolean deleteResult = departmentDao.deleteByName(department.getName());
        if(deleteResult)
            logger.info("===== deleteDepartmentTest(), the new create department with name={} is deleted",
                    department.getName());
        else
            logger.info("===== deleteDepartmentTest(), FAILED to delete the new create department with name={} ",
                    department.getName());
    }

    @Test
    public void saveDepartmentHibernateTest() {
        Department department = createDepartmentForTest("IT-test-1", "Virginia", "IT development");
        Department departmentSaved = departmentDao.save(department);
        assertNotNull(departmentSaved.getId(), "A saved department should have a ID with NULL value");
        assertEquals(department.getDescription(), departmentSaved.getDescription(), "The description value should be the same");
        logger.info("Department = {}", departmentSaved);
        logger.info("Saved Department = {}", departmentSaved);
        boolean deleteResult = departmentDao.delete(departmentSaved);
        if(deleteResult)
            logger.info("===== saveDepartmentHibernateTest(), the new create department with name={} is deleted",
                    departmentSaved.getName());
        else
            logger.info("===== saveDepartmentHibernateTest(), FAILED to delete the new create department with name={} ",
                    departmentSaved.getName());

    }

    @Test
    public void saveDepartmentWithDepartmentDetailTest() {
        DepartmentDetail departmentDetail = new DepartmentDetail();
        departmentDetail.setSize(2222);
        departmentDetail.setRevenue(101);
        departmentDetail.setDescription("dept detail dummy desc");

        Department departmentForTest = createDepartmentForTest("dept 111", "location 111", "dept desc 111");
        departmentForTest.setDepartmentDetail(departmentDetail);
        departmentDetail.setDepartment(departmentForTest);

        Department savedDepartment = departmentDao.save(departmentForTest);
        boolean deleteResult = departmentDao.delete(savedDepartment);
        logger.info("deleteResult = {}", deleteResult);
    }

    private Department createDepartmentForTest(String deptName, String location, String deptDesc) {
        Department department = new Department();
        department.setDescription(deptDesc);
        department.setLocation(location);
        department.setName(deptName);
        return department;
    }

    private Department createDepartmentWithEmployees(String deptName, String empName1,
                                                     String empName2) {
        Department dept = new Department();
        dept.setName(deptName);
        dept.setDescription(deptName + " description");
        dept.setLocation("Fairfax, VA");

        Employee emp1 = new Employee();
        emp1.setName(empName1);
        emp1.setAddress("123 Road, VA");
        Employee emp2 = new Employee();
        emp2.setName(empName2);
        emp2.setAddress("456 Road, VA");

        dept.getEmployees().add(emp1);
        emp1.setDepartment(dept);

        dept.getEmployees().add(emp2);
        emp2.setDepartment(dept);

        //Now do the final part, save both dept1 and two employees in one shot
        Department createdDepartment = departmentDao.save(dept);

        return createdDepartment;
    }

}
