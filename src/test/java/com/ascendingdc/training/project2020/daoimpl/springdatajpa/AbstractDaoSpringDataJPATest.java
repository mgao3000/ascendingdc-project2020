package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.*;
import com.ascendingdc.training.project2020.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractDaoSpringDataJPATest {
    private Logger logger = LoggerFactory.getLogger(AbstractDaoSpringDataJPATest.class);

//    protected static MajorDao majorDao;
//
//    protected static StudentDao studentDao;
//
//    protected static ProjectDao projectDao;

    @Autowired
    @Qualifier("majorSpringDataJPADao")
    protected MajorDao majorDao;

    @Autowired
    @Qualifier("studentSpringDataJPADao")
    protected StudentDao studentDao;

    @Autowired
    @Qualifier("projectSpringDataJPADao")
    protected ProjectDao projectDao;

    @Autowired
    @Qualifier("employeeSpringDataJPADao")
    protected EmployeeDao employeeDao;

    @Autowired
    @Qualifier("departmentSpringDataJPADao")
    protected DepartmentDao departmentDao;

    @Autowired
    @Qualifier("accountSpringDataJPADao")
    protected AccountDao accountDao;

    protected String tempMajorName;

    protected String tempProjectName;

    protected String tempLoginName = null;

    protected String tempEmail = null;

//    @Rule
//    public ExpectedException thrown = ExpectedException.none();

    protected Department createDepartmentEntity(String deptName, String location) {
        Department departmentEntity = new Department();
        departmentEntity.setName(deptName);
        departmentEntity.setDescription(deptName + " description");
        departmentEntity.setLocation(location);
        return departmentEntity;
    }

    protected Employee createEmployeeEntity(String firstName, String lastName, String address) {
        Employee employeeEntity = new Employee();
        employeeEntity.setName(firstName + "_" + lastName);
        employeeEntity.setFirstName(firstName);
        employeeEntity.setLastName(lastName);
        employeeEntity.setEmail(firstName + "_" + lastName + "@gmail");
        employeeEntity.setAddress("123 Road, VA");
        employeeEntity.setHiredDate(LocalDate.now());
        return employeeEntity;
    }

    protected Account createAccountEntity(String accountType, BigDecimal balance) {
        Account accountEntity = new Account();
        accountEntity.setAccountType(accountType);
        accountEntity.setBalance(balance);
        return accountEntity;
    }

    protected DepartmentDetail createDepartmentDetailEntity(int size, int revenue, String description) {
        DepartmentDetail departmentDetailEntity = new DepartmentDetail();
        departmentDetailEntity.setSize(size);
        departmentDetailEntity.setRevenue(revenue);
        departmentDetailEntity.setDescription(description);
        return departmentDetailEntity;
    }


    protected void assertMajors(Major randomMajor, Major retrievedMajor) {
        assertEquals(randomMajor.getId(), retrievedMajor.getId());
        assertEquals(randomMajor.getName(), retrievedMajor.getName());
        assertEquals(randomMajor.getDescription(), retrievedMajor.getDescription());
//        assertEquals(randomMajor.getStudents().size(), retrievedMajor.getStudents().size());
    }

    protected void assertProjects(Project randomProject, Project retrievedProject) {
        assertEquals(randomProject.getId(), retrievedProject.getId());
        assertEquals(randomProject.getName(), retrievedProject.getName());
        assertEquals(randomProject.getDescription(), retrievedProject.getDescription());
        assertTrue(randomProject.getCreateDate().isEqual(retrievedProject.getCreateDate()));
//        assertEquals(randomProject.getStudents().size(), retrievedProject.getStudents().size());
    }

    protected void assertStudents(Student randomStudent, Student retrievedStudent) {
        assertEquals(randomStudent.getId(), retrievedStudent.getId(), "both student IDs should be equal");
        assertEquals(randomStudent.getMajor().getId(), retrievedStudent.getMajor().getId());
        assertEquals(randomStudent.getLoginName(), retrievedStudent.getLoginName());
        assertEquals(randomStudent.getPassword(), retrievedStudent.getPassword());
        assertEquals(randomStudent.getFirstName(), retrievedStudent.getFirstName());
        assertEquals(randomStudent.getLastName(), retrievedStudent.getLastName());
        assertEquals(randomStudent.getEmail(), retrievedStudent.getEmail());
        assertEquals(randomStudent.getAddress(), retrievedStudent.getAddress());
        assertTrue(randomStudent.getEnrolledDate().isEqual(retrievedStudent.getEnrolledDate()));
    }

    protected void assertEmployee(Employee savedEmployee, Employee tempEmployee) {
        assertEquals(savedEmployee.getId(), tempEmployee.getId());
        assertEquals(savedEmployee.getName(), tempEmployee.getName());
        assertEquals(savedEmployee.getAddress(), tempEmployee.getAddress());
        assertEquals(savedEmployee.getEmail(), tempEmployee.getEmail());
        assertEquals(savedEmployee.getFirstName(), tempEmployee.getFirstName());
        assertEquals(savedEmployee.getLastName(), tempEmployee.getLastName());
        assertTrue(savedEmployee.getHiredDate().isEqual(tempEmployee.getHiredDate()));
    }

    protected void assertDepartment(Department sourceDepartment, Department targetDepartment) {
        assertEquals(sourceDepartment.getId(), targetDepartment.getId());
        assertEquals(sourceDepartment.getName(), targetDepartment.getName());
        assertEquals(sourceDepartment.getDescription(), targetDepartment.getDescription());
        assertEquals(sourceDepartment.getLocation(), targetDepartment.getLocation());
    }

    protected void assertAccount(Account sourceAccount, Account targetAccount) {
        assertEquals(sourceAccount.getId(), targetAccount.getId());
        assertEquals(sourceAccount.getAccountType(), targetAccount.getAccountType());
        assertEquals(sourceAccount.getBalance(), targetAccount.getBalance());
    }

    protected Project createProjectByName(String projectName) {
        Project project = new Project();
        project.setName(projectName);
        project.setDescription(projectName + "--description");
        project.setCreateDate(LocalDate.now());
        return project;
    }

    protected Major createMajorByName(String name) {
        Major major = new Major();
        major.setName(name);
        major.setDescription(name + "--description");
        return major;
    }

    protected Student createStudentByLoginNameAndEmailWithRandomMajor(String loginName, String email) {
        Student student = new Student();
        student.setLoginName(loginName);
        student.setPassword("password123456");
        student.setFirstName("Frist name test");
        student.setLastName("Last name test");
        student.setEmail(email);
        student.setAddress("123 Dodge Road, Reston, VA 20220");
        student.setEnrolledDate(LocalDate.now());

        /*
         * Now using MajorDao to select a valid Major from DB
         */
        Major randomMajor = getRandomMajor();
        student.setMajor(randomMajor);

        return student;
    }

    protected Student createStudentByLoginNameAndEmailWithoutMajorAssigned(String loginName, String email) {
        Student student = new Student();
        student.setLoginName(loginName);
        student.setPassword("password123456");
        student.setFirstName("Frist name test");
        student.setLastName("Last name test");
        student.setEmail(email);
        student.setAddress("123 Dodge Road, Reston, VA 20220");
        student.setEnrolledDate(LocalDate.now());

        return student;
    }

    protected Department getRandomDepartment() {
        List<Department> departmentList = departmentDao.getDepartments();
        Department randomDepartment = null;
        if(departmentList != null && departmentList.size() > 0) {
            int randomIndex = getRandomInt(0, departmentList.size());
            randomDepartment = departmentList.get(randomIndex);
        }
        return randomDepartment;
    }

    protected Employee getRandomEmployee() {
        List<Employee> employeeList = employeeDao.getEmployees();
        Employee randomEmployee = null;
        if(employeeList != null && employeeList.size() > 0) {
            int randomIndex = getRandomInt(0, employeeList.size());
            randomEmployee = employeeList.get(randomIndex);
        }
        return randomEmployee;
    }

    protected Account getRandomAccount() {
        List<Account> accountList = accountDao.getAccounts();
        Account randomAccount = null;
        if(accountList != null && accountList.size() > 0) {
            int randomIndex = getRandomInt(0, accountList.size());
            randomAccount = accountList.get(randomIndex);
        }
        return randomAccount;
    }

    protected Project getRandomProject() {
        List<Project> projectList = projectDao.getProjects();
        Project randomProject = null;
        if(projectList != null && projectList.size() > 0) {
            int randomIndex = getRandomInt(0, projectList.size());
            randomProject = projectList.get(randomIndex);
        }
        return randomProject;
    }

    protected Major getRandomMajor() {
        List<Major> majorList = majorDao.getMajors();
        Major randomMajor = null;
        if(majorList != null && majorList.size() > 0) {
            int randomIndex = getRandomInt(0, majorList.size());
            randomMajor = majorList.get(randomIndex);
        }
        return randomMajor;
    }

    protected Student getRandomStudent() {
        List<Student> studentList = studentDao.getStudents();
        Student randomStudent = null;
        if(studentList != null && studentList.size() > 0) {
            int randomIndex = getRandomInt(0, studentList.size());
            randomStudent = studentList.get(randomIndex);
        }
        return randomStudent;
    }

    protected void displayStudentsWithoutAssociatedProjects(List<Student> studentList) {
        logger.info("The total number of Students is: {}", studentList.size());
        int index = 1;
        for(Student student : studentList) {
            logger.info("No.{} Student:", index);
            displayStudentWithoutAssociatedProjects(student);
            index++;
        }
    }

    protected void displayStudentWithoutAssociatedProjects(Student student) {
        logger.info(" Students is: {}", student);
    }

    protected void displayStudents(List<Student> studentList) {
        logger.info("The total number of Students is: {}", studentList.size());
        int index = 1;
        for(Student student : studentList) {
            logger.info("No.{} Student:", index);
            displayStudent(student);
            index++;
        }
    }

    protected void displayProjects(List<Project> projectList) {
        logger.info("The total number of Projects is: {}", projectList.size());
        int index = 1;
        for(Project eachProject : projectList) {
            logger.info("No.{} project:", index);
            displayProject(eachProject);
            index++;
        }
    }

    protected void displayProjectsWithoutAssociatedStudents(List<Project> projectList) {
        logger.info("The total number of Projects is: {}", projectList.size());
        int index = 1;
        for(Project eachProject : projectList) {
            logger.info("No.{} project:", index);
            displayProjectWithoutAssociatedStudents(eachProject);
            index++;
        }
    }

    protected void displayProject(Project project) {
        logger.info("Project detail={}", project);
        displayStudentsWithoutAssociatedProjects(project.getStudents());
    }

    protected void displayProjectWithoutAssociatedStudents(Project project) {
        logger.info("Project detail={}", project);
    }

    protected void displayStudentSetWithAssociatedProjects(Set<Student> studentSet) {
        logger.info("\t The total associated students={}", studentSet.size());
        int index = 1;
        for (Student student : studentSet) {
            logger.info("No.{} student = {}", index, student);
            displayProjectList(student.getProjects());
            logger.info("-----------------------------");
            index++;
        }
    }

//    protected void displayStudentsWithoutAssociatedProjects(List<Student> studentSet) {
//        logger.info("\t The total associated students={}", studentSet.size());
//        int index = 1;
//        for (Student student : studentSet) {
//            logger.info("No.{} student = {}", index, student);
//            logger.info("========= Student's major info as below: ");
//            displayMajorWithoutChildren(student.getMajor());
//            index++;
//        }
//    }

    protected void displayMajorsWithoutChildren(List<Major> majorList) {
        logger.info("The total number of Majors is: {}", majorList.size());
        int index = 1;
        for(Major major : majorList) {
            logger.info("No.{} Major:", index);
            logger.info("Major detail={}", major);
            logger.info("===============================================");
            index++;
        }
    }

    protected void displayMajorsWithChildren(List<Major> majorList) {
        logger.info("The total number of Majors is: {}", majorList.size());
        int index = 1;
        for(Major major : majorList) {
            logger.info("No.{} Major:", index);
            displayMajorWithAssociatedStudents(major);
            logger.info("===============================================");
            index++;
        }
    }

    protected void displayMajorWithChildren(Major major) {
        logger.info("Major={}", major);
        displayStudentSetWithAssociatedProjects(major.getStudents());
        logger.info("===============================================");
    }

    protected void displayMajorWithAssociatedStudents(Major major) {
        logger.info("Major detail={}", major);
        displayStudentSetWithAssociatedProjects(major.getStudents());
    }

    protected void displayMajorWithoutChildren(Major major) {
        logger.info("Major detail={}", major);
    }


//    protected void displayStudentList(List<StudentModel> studentList) {
//        logger.info("The total number of Students is: {}", studentList.size());
//        int index = 1;
//        for(StudentModel student : studentList) {
//            logger.info("No.{} Student:", index);
//            displayStudent(student);
//            index++;
//        }
//    }

    protected void displayStudent(Student student) {
        logger.info("Student detail={}", student);
        displayProjectList(student.getProjects());
    }

    protected void displayProjectList(List<Project> projectSet) {
        logger.info("\t The total associated projects={}", projectSet.size());
        int index = 1;
        for (Project project : projectSet) {
            logger.info("No.{} project = {}", index, project);
            index++;
        }
        logger.info("");
    }


    /**
     * This helper method return a random int value in a range between
     * min (inclusive) and max (exclusive)
     * @param min
     * @param max
     * @return
     */
    public int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

}
