package com.ascendingdc.training.project2020.daoimpl.jdbc;

import com.ascendingdc.training.project2020.daoimpl.springjdbc.MajorDaoSpringJDBCImpl;
import com.ascendingdc.training.project2020.daoimpl.springjdbc.StudentDaoSpringJDBCImpl;
import com.ascendingdc.training.project2020.model.Major;
import com.ascendingdc.training.project2020.model.Student;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.List;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentDaoJDBCTest extends AbstractDaoJDBCTest {
    private Logger logger = LoggerFactory.getLogger(StudentDaoJDBCTest.class);

//    private static StudentDao studentDao;
//    private static MajorDao majorDao;

//    private String tempLoginName = null;
    @Autowired
    private DataSource dataSource;

    @BeforeAll
    public static void setupOnce() {
//        studentDao = new StudentDaoJDBCImpl();
//        majorDao = new MajorDaoJDBCImpl();
    }

    @AfterAll
    public static void teardownOnce() {
//        studentDao = null;
//        majorDao = null;
    }

    @BeforeEach
    public void setup() {
//        studentDao = new StudentDaoJDBCImpl();
//        majorDao = new MajorDaoJDBCImpl();
        studentDao = new StudentDaoSpringJDBCImpl(dataSource);
        majorDao = new MajorDaoSpringJDBCImpl(dataSource);

        tempLoginName = "Student-login-" + getRandomInt(1, 1000);
        randomEmail = "test" + getRandomInt(1, 1000) + "@devmountain.com";
    }

    @AfterEach
    public void teardown() {
        studentDao = null;
        majorDao = null;
    }

    @Test
    public void getStudentsTest() {
        List<Student> studentList = studentDao.getStudents();
        int i = 1;
        for(Student student : studentList) {
            logger.info("No.{} student = {}", i, student);
            i++;
        }
    }

    @Test
    public void getStudentsByMajorIdTest() {
        /*
         * Now need to use majorDao to randomly select a valid Major
         */
        Major randomMajor = getRandomMajorModel();

        List<Student> studentList = studentDao.getStudentsByMajorId(randomMajor.getId());
        int i = 1;
        for(Student student : studentList) {
            logger.info("No.{} student = {}", i, student);
            i++;
        }
    }

    @Test
    public void getStudentByIdTest() {
        /*
         * Pick up a random StudentModel from DB
         */
        Student randomStudent = getRandomStudentModel();
        if(randomStudent == null) {
            logger.error("there is no student being found in database, please double check DB connection!");
        } else {
            Long studentId = randomStudent.getId();
            Student retrievedStudentModel = studentDao.getStudentById(studentId);
            assertStudentModels(randomStudent, retrievedStudentModel);
        }
    }

    @Test
    public void getStudentByLoginNameTest() {
        /*
         * Pick up a random StudentModel from DB
         */
        Student randomStudent = getRandomStudentModel();
        if(randomStudent == null) {
            logger.error("there is no Student being found in database, please double check DB connection!");
        } else {
            String loginName = randomStudent.getLoginName();
            Student retrievedStudentModel = studentDao.getStudentByLoginName(loginName);
            assertStudentModels(randomStudent, retrievedStudentModel);
        }
    }

    @Test
    public void saveStudentTest() {
        Student student = createStudentByLoginNameAndEmail(tempLoginName, randomEmail);
        /*
         * Now need to use majorDao to randomly select a valid Major
         */
        Major randomMajor = getRandomMajorModel();

        Student savedStudent = studentDao.save(student, randomMajor.getId());
        student.setMajorId(randomMajor.getId());
        assertStudentModels(student, savedStudent);
        /*
         * Now clean up the saved Student from DB Major table
         */
        boolean deleteSuccessfulFlag = studentDao.delete(savedStudent);
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void deleteStudentTest() {
        /*
         * create a temp Student to test deletion
         */
        Student student = createStudentByLoginNameAndEmail(tempLoginName, randomEmail);
        /*
         * Now need to use majorDao to randomly select a valid Major
         */
        Major randomMajor = getRandomMajorModel();

        Student savedStudent = studentDao.save(student, randomMajor.getId());
        /*
         * Now delete the saved Project from DB Major table
         */
        boolean deleteSuccessfulFlag = studentDao.delete(savedStudent);
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void deleteStudentByIdTest() {
        /*
         * create a temp Student to test deletion
         */
        Student student = createStudentByLoginNameAndEmail(tempLoginName, randomEmail);
        /*
         * Now need to use majorDao to randomly select a valid Major
         */
        Major randomMajor = getRandomMajorModel();

        Student savedStudent = studentDao.save(student, randomMajor.getId());
        /*
         * Now delete the saved Student using student ID from DB Major table
         */
        boolean deleteSuccessfulFlag = studentDao.deleteById(savedStudent.getId());
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void deleteStudentByLoginNameTest() {
        /*
         * create a temp Student to test deletion
         */
        Student student = createStudentByLoginNameAndEmail(tempLoginName, randomEmail);
        /*
         * Now need to use majorDao to randomly select a valid Major
         */
        Major randomMajor = getRandomMajorModel();

        Student savedStudent = studentDao.save(student, randomMajor.getId());
        /*
         * Now delete the saved Student using student ID from DB Major table
         */
        boolean deleteSuccessfulFlag = studentDao.deleteByLoginName(savedStudent.getLoginName());
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void updateStudentTest() {
        Student originalStudentModel = getRandomStudentModel();

        String originalStudentAddress = originalStudentModel.getAddress();
        String modifiedStudentAddress = originalStudentAddress + "---Modified Address";
        originalStudentModel.setAddress(modifiedStudentAddress);
        /*
         * Now start doing update operation
         */
        Student updatedStudentModel = studentDao.update(originalStudentModel);
        assertStudentModels(originalStudentModel, updatedStudentModel);

        /*
         * now reset StudentModel address back to the original value
         */
        originalStudentModel.setAddress(originalStudentAddress);
        updatedStudentModel = studentDao.update(originalStudentModel);
        assertStudentModels(originalStudentModel, updatedStudentModel);
    }

    @Test
    public void getStudentsWithAssociatedProjectsTest() {
        List<Student> studentList = studentDao.getStudentsWithAssociatedProjects();
        displayStudents(studentList);
    }

    @Test
    public void getStudentsWithAssociatedProjectsByMajorIdTest() {
        /*
         * Now need to use majorDao to randomly select a valid Major
         */
        Major randomMajorModel = getRandomMajorModel();

        List<Student> studentList = studentDao.getStudentsWithAssociatedProjectsByMajorId(randomMajorModel.getId());
        displayStudents(studentList);
    }

    @Test
    public void getStudentWithAssociatedProjectsByStudentIdTest() {
        /*
         * Pick up a random StudentModel from DB
         */
        Student randomStudent = getRandomStudentModel();
        if(randomStudent == null) {
            logger.error("there is no student being found in database, please double check DB connection!");
        } else {
            Long studentId = randomStudent.getId();
            Student retrievedStudentModel = studentDao.getStudentWithAssociatedProjectsByStudentId(studentId);
            assertStudentModels(randomStudent, retrievedStudentModel);
            displayStudent(retrievedStudentModel);
        }
    }

    @Test
    public void getStudentWithAssociatedProjectsByLoginNameTest() {
        /*
         * Pick up a random StudentModel from DB
         */
        Student randomStudent = getRandomStudentModel();
        if(randomStudent == null) {
            logger.error("there is no Student being found in database, please double check DB connection!");
        } else {
            String loginName = randomStudent.getLoginName();
            Student retrievedStudentModel = studentDao.getStudentWithAssociatedProjectsByLoginName(loginName);
            assertStudentModels(randomStudent, retrievedStudentModel);
            displayStudent(retrievedStudentModel);
        }
    }

//    private void displayStudents(List<StudentModel> studentList) {
//        logger.info("The total number of Students is: {}", studentList.size());
//        int index = 1;
//        for(StudentModel student : studentList) {
//            logger.info("No.{} Student:", index);
//            displayStudent(student);
//            index++;
//        }
//    }

//    private void displayStudent(StudentModel student) {
//        logger.info("Student detail={}", student);
//        displayProjectList(student.getProjectList());
//    }
//
//    private void displayProjectList(List<ProjectModel> projectList) {
//        logger.info("\t The total associated projects={}", projectList.size());
//        int index = 1;
//        for (ProjectModel project : projectList) {
//            logger.info("No.{} project = {}", index, project);
//            index++;
//        }
//    }

//    private void assertStudentModels(StudentModel randomStudentModel, StudentModel retrievedStudentModel) {
//        assertEquals(randomStudentModel.getId(), retrievedStudentModel.getId());
//        assertEquals(randomStudentModel.getMajorId(), retrievedStudentModel.getMajorId());
//        assertEquals(randomStudentModel.getLoginName(), retrievedStudentModel.getLoginName());
//        assertEquals(randomStudentModel.getPassword(), retrievedStudentModel.getPassword());
//        assertEquals(randomStudentModel.getFirstName(), retrievedStudentModel.getFirstName());
//        assertEquals(randomStudentModel.getLastName(), retrievedStudentModel.getLastName());
//        assertEquals(randomStudentModel.getEmail(), retrievedStudentModel.getEmail());
//        assertEquals(randomStudentModel.getAddress(), retrievedStudentModel.getAddress());
//        assertTrue(randomStudentModel.getEnrolledDate().isEqual(retrievedStudentModel.getEnrolledDate()));
//    }
//
//    private StudentModel createStudentByLoginName(String loginName) {
//        StudentModel student = new StudentModel();
//        student.setLoginName(loginName);
//        student.setPassword("password123456");
//        student.setFirstName("Frist name test");
//        student.setLastName("Last name test");
//        student.setEmail("test@google.com");
//        student.setAddress("123 Dodge Road, Reston, VA 20220");
//        student.setEnrolledDate(LocalDate.now());
//
//        /*
//         * Now using MajorDao to select a valid Major from DB
//         */
//        MajorModel randomMajor = getRandomMajorModel();
//        student.setMajorId(randomMajor.getId());
//
//        return student;
//    }

//    private MajorModel getRandomMajorModel() {
//        List<MajorModel> majorModelList = majorDao.getMajors();
//        MajorModel randomMajor = null;
//        if(majorModelList != null && majorModelList.size() > 0) {
//            int randomIndex = getRandomInt(0, majorModelList.size());
//            randomMajor = majorModelList.get(randomIndex);
//        }
//        return randomMajor;
//    }

//    private StudentModel getRandomStudentModel() {
//        List<StudentModel> studentModelList = studentDao.getStudents();
//        StudentModel randomStudent = null;
//        if(studentModelList != null && studentModelList.size() > 0) {
//            int randomIndex = getRandomInt(0, studentModelList.size());
//            randomStudent = studentModelList.get(randomIndex);
//        }
//        return randomStudent;
//    }

    /**
     * This helper method return a random int value in a range between
     * min (inclusive) and max (exclusive)
     * @param min
     * @param max
     * @return
     */
//    private int getRandomInt(int min, int max) {
//        Random random = new Random();
//        return random.nextInt(max - min) + min;
//    }

}
