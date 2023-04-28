package com.ascendingdc.training.project2020.daoimpl.jdbc;

import com.ascendingdc.training.project2020.daoimpl.springjdbc.MajorDaoSpringJDBCImpl;
import com.ascendingdc.training.project2020.daoimpl.springjdbc.ProjectDaoSpringJDBCImpl;
import com.ascendingdc.training.project2020.daoimpl.springjdbc.StudentDaoSpringJDBCImpl;
import com.ascendingdc.training.project2020.daoimpl.springjdbc.StudentProjectDaoSpringJDBCImpl;
import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
//import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

//@Import(SpringJdbcTestConfig.class)
//@SpringBootTest
//@ComponentScan(basePackageClasses={com.ascendingdc.training.project2020.configuration.SpringJdbcConfig.class})
//@Transactional
public class MajorDaoJDBCTest extends AbstractDaoJDBCTest {
    private static Logger logger = LoggerFactory.getLogger(MajorDaoJDBCTest.class);

//    private static MajorDao majorDao;
//
//    private static StudentDao studentDao;
//
//    private static ProjectDao projectDao;
//
//    private static StudentProjectDao studentProjectDao;
//
//    private String tempMajorName = null;

    @Autowired
    private DataSource dataSource;

//    @PostConstruct
//    @Autowired
//    public void init() {
//        DataSourceUtil.setDataSource(dataSource);
//    }

    @BeforeAll
    public static void setupOnce() {
//        majorDao = new MajorDaoJDBCImpl();
//        studentDao = new StudentDaoJDBCImpl();
//        projectDao = new ProjectDaoJDBCImpl();
//        studentProjectDao = new StudentProjectDaoJDBCImpl();

//        majorDao = new MajorDaoSpringJDBCImpl(staticDataSource);

    }

    @AfterAll
    public static void teardownOnce() {
//        majorDao = null;
//        studentDao = null;
//        projectDao = null;
//        studentProjectDao = null;
    }

    @BeforeEach
    public void setup() {
//        majorDao = new MajorDaoJDBCImpl();
//        studentDao = new StudentDaoJDBCImpl();
//        projectDao = new ProjectDaoJDBCImpl();
//        studentProjectDao = new StudentProjectDaoJDBCImpl();

        majorDao = new MajorDaoSpringJDBCImpl(dataSource);
        studentDao = new StudentDaoSpringJDBCImpl(dataSource);
        projectDao = new ProjectDaoSpringJDBCImpl(dataSource);
        studentProjectDao = new StudentProjectDaoSpringJDBCImpl(dataSource);

        tempMajorName = "Major-" + getRandomInt(1, 1000);
        tempLoginName = "Student-login-" + getRandomInt(1, 1000);
        tempProjectName = "Project-" + getRandomInt(1, 1000);
        randomEmail = "test-" + getRandomInt(1, 1000) + "@devmountain.com";

        if(dataSource == null)
            logger.error("===== dataSource is NULL");
        else
            logger.error("===== dataSource is NOTNOTNOT NULL");
    }

    @AfterEach
    public void teardown() {

    }

    @Test
    public void getMajorsTest() {
        List<MajorDto> majorModelList = majorDao.getMajors();
        int i = 1;
        for(MajorDto major : majorModelList) {
            logger.info("No.{} Major = {}", i, major);
            i++;
        }
    }

    @Test
    public void getMajorByIdTest() {
        /*
         * Pick up a random MajorModel from DB
         */
        MajorDto randomMajor = getRandomMajorModel();
        if(randomMajor == null) {
            logger.error("there is no major being found in database, please double check DB connection!");
        } else {
            Long majorId = randomMajor.getId();
            MajorDto retrievedMajorModel = majorDao.getMajorById(majorId);
            assertMajorModels(randomMajor, retrievedMajorModel);
        }
    }

//    @Test
//    public void testGetMajorByInvalidId() {
//        Long majorId = 15678L;
//        Exception exception = Assertions.assertThrows(Exception.class, () -> {
//            majorDao.getMajorById(15678L);
//        }, "Exception was expected");
//
//        Assertions.assertEquals("SQLException is caught when trying to select a Major by majorId. The input majorId =" + majorId, exception.getMessage());
//    }

    @Test
    public void getMajorByNameTest() {
        /*
         * Pick up a random MajorModel from DB
         */
        MajorDto randomMajor = getRandomMajorModel();
        if(randomMajor == null) {
            logger.error("there is no major being found in database, please double check DB connection!");
        } else {
            String majorName = randomMajor.getName();
            MajorDto retrievedMajorModel = majorDao.getMajorByName(majorName);
            assertMajorModels(randomMajor, retrievedMajorModel);
        }
    }

    @Test
    public void getMajorsWithChildrenTest() {
        List<MajorDto> majorModelList = majorDao.getMajorsWithChildren();
        displayMajors(majorModelList);
    }

    @Test
    public void getMajorAndStudentsAndProjectsByMajorIdTest() {
        /*
         * Pick up a random MajorModel from DB
         */
        MajorDto randomMajor = getRandomMajorModel();
        if(randomMajor == null) {
            logger.error("there is no major being found in database, please double check DB connection!");
        } else {
            Long majorId = randomMajor.getId();
            MajorDto retrievedMajorModel = majorDao.getMajorAndStudentsAndProjectsByMajorId(majorId);
            assertMajorModels(randomMajor, retrievedMajorModel);
            displayMajor(retrievedMajorModel);
        }
    }

    @Test
    public void getMajorAndStudentsAndProjectsByMajorNameTest() {
        /*
         * Pick up a random MajorModel from DB
         */
        MajorDto randomMajor = getRandomMajorModel();
        if(randomMajor == null) {
            logger.error("there is no major being found in database, please double check DB connection!");
        } else {
            String majorName = randomMajor.getName();
            MajorDto retrievedMajorModel = majorDao.getMajorAndStudentsAndProjectsByMajorName(majorName);
            assertMajorModels(randomMajor, retrievedMajorModel);
            displayMajor(retrievedMajorModel);
        }
    }


    @Test
    @Transactional
    public void saveMajorTest() {
        MajorDto major = createMajorByName(tempMajorName);
        MajorDto savedMajor = majorDao.save(major);
        assertNotNull(savedMajor.getId());
        assertEquals(major.getName(), savedMajor.getName());
        assertEquals(major.getDescription(), savedMajor.getDescription());
        /*
         * Now clean up the saved Major from DB Major table
         */
//        boolean deleteSuccessfulFlag = majorDao.delete(savedMajor);
//        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void deleteMajorTest() {
        MajorDto major = createMajorByName(tempMajorName);
        MajorDto savedMajor = majorDao.save(major);
        assertNotNull(savedMajor.getId(), " successfully saved Major should have a valid ID value");
        /*
         * Now delete the saved Major from DB Major table
         */
        boolean deleteSuccessfulFlag = majorDao.delete(savedMajor);
        assertEquals(true, deleteSuccessfulFlag);
        assertTrue(deleteSuccessfulFlag, "the returned boolean flag should be true if the deletion is successful");

        /*
         * Now assert the deleted Major no longer exists in DB.
         */
        MajorDto retrievedMajor = majorDao.getMajorById(savedMajor.getId());
        assertNull(retrievedMajor, "Select a deleted Major should return null");
    }

    @Test
    public void deleteMajorByIdTest() {
        MajorDto major = createMajorByName(tempMajorName);
        MajorDto savedMajor = majorDao.save(major);
        assertNotNull(savedMajor.getId(), " successfully saved Major should have a valid ID value");
        /*
         * Now delete the saved Major from DB Major table
         */
        boolean deleteSuccessfulFlag = majorDao.deleteById(savedMajor.getId());
        assertTrue(deleteSuccessfulFlag);

        /*
         * Now assert the deleted Major no longer exists in DB.
         */
        MajorDto retrievedMajor = majorDao.getMajorById(savedMajor.getId());
        assertNull(retrievedMajor, "Select a deleted Major should return null");

    }

    @Test
    public void deleteMajorWithChildrenTest() {
        //Step 0: create a temp Major
        MajorDto major = createMajorByName(tempMajorName);
        MajorDto majorSaved = majorDao.save(major);

        //Step 1.1: create a temp Student No.1
        StudentDto student1 = createStudentByLoginNameAndEmail(tempLoginName, randomEmail);
//        student1.setMajorId(majorSaved.getId());
        StudentDto studentSaved1 = studentDao.save(student1, majorSaved.getId());

        //Step 1.2  create a temp project No.1
        ProjectDto project1 = createProjectByName(tempProjectName);
        ProjectDto projectSaved1 = projectDao.save(project1);

        //Step 1.3: create a temp project No.2
        ProjectDto project2 = createProjectByName(tempProjectName+"-2");
        ProjectDto projectSaved2 = projectDao.save(project2);

        //Step 1.4: set relationship for student No.1 with project No.1 and No.2
        studentProjectDao.addStudentProjectRelationship(studentSaved1.getId(), projectSaved1.getId());
        studentProjectDao.addStudentProjectRelationship(studentSaved1.getId(), projectSaved2.getId());

        //Step 2.1: create a temp Student No.2
        StudentDto student2 = createStudentByLoginNameAndEmail(tempLoginName+"-2", 2 + randomEmail);
//        student2.setMajorId(majorSaved.getId());
        StudentDto studentSaved2 = studentDao.save(student2, majorSaved.getId());

        //Step 2.2  create a temp project No.3
        ProjectDto project3 = createProjectByName(tempProjectName+"-3");
        ProjectDto projectSaved3 = projectDao.save(project3);

        //Step 2.3: create a temp project No.4
        ProjectDto project4 = createProjectByName(tempProjectName+"-4");
        ProjectDto projectSaved4 = projectDao.save(project4);

        //Step 2.4: set relationship for student No.2 with project No.3 and No.4
        studentProjectDao.addStudentProjectRelationship(studentSaved2.getId(), projectSaved3.getId());
        studentProjectDao.addStudentProjectRelationship(studentSaved2.getId(), projectSaved4.getId());

        /*
         * Now delete the saved Major from DB Major table
         */
        //Step 1, delete student_project join table
        boolean deleteSuccessfulFlag = studentProjectDao.deleteStudentProjectRelationshipByStudentId(studentSaved1.getId());
        assertEquals(true, deleteSuccessfulFlag);
        deleteSuccessfulFlag = studentProjectDao.deleteStudentProjectRelationshipByStudentId(studentSaved2.getId());
        assertEquals(true, deleteSuccessfulFlag);

        //Step 2: delete temp students using studentId
        deleteSuccessfulFlag = studentDao.deleteById(studentSaved1.getId());
        assertEquals(true, deleteSuccessfulFlag);
        deleteSuccessfulFlag = studentDao.deleteById(studentSaved2.getId());
        assertEquals(true, deleteSuccessfulFlag);

        //Step 3: delete temp projects using projectId
        deleteSuccessfulFlag = projectDao.deleteById(projectSaved1.getId());
        assertEquals(true, deleteSuccessfulFlag);
        deleteSuccessfulFlag = projectDao.deleteById(projectSaved2.getId());
        assertEquals(true, deleteSuccessfulFlag);
        deleteSuccessfulFlag = projectDao.deleteById(projectSaved3.getId());
        assertEquals(true, deleteSuccessfulFlag);
        deleteSuccessfulFlag = projectDao.deleteById(projectSaved4.getId());
        assertEquals(true, deleteSuccessfulFlag);

        //Step 4: delete temp major using majorId
        deleteSuccessfulFlag = majorDao.delete(majorSaved);
        assertEquals(true, deleteSuccessfulFlag);

//        //Now delete those temporarily created projects using project ID
//        deleteSuccessfulFlag = projectDao.delete(projectSaved1);
//        assertEquals(true, deleteSuccessfulFlag);
//
//        deleteSuccessfulFlag = projectDao.delete(projectSaved2);
//        assertEquals(true, deleteSuccessfulFlag);
//
//        deleteSuccessfulFlag = projectDao.delete(projectSaved3);
//        assertEquals(true, deleteSuccessfulFlag);
//
//        deleteSuccessfulFlag = projectDao.delete(projectSaved4);
//        assertEquals(true, deleteSuccessfulFlag);

        //Now start to do multiple selection to make sure new created major, students are gone!
        MajorDto retrievedMajor = majorDao.getMajorById(majorSaved.getId());
        assertNull(retrievedMajor);

        StudentDto retrievedStudent = studentDao.getStudentById(studentSaved1.getId());
        assertNull(retrievedStudent);
        retrievedStudent = studentDao.getStudentById(studentSaved2.getId());
        assertNull(retrievedStudent);

        ProjectDto retrievedProject = projectDao.getProjectById(projectSaved1.getId());
        assertNull(retrievedProject);
        retrievedProject = projectDao.getProjectById(projectSaved2.getId());
        assertNull(retrievedProject);
        retrievedProject = projectDao.getProjectById(projectSaved3.getId());
        assertNull(retrievedProject);
        retrievedProject = projectDao.getProjectById(projectSaved4.getId());
        assertNull(retrievedProject);
    }

    @Test
    public void updateMajorTest() {
//        Major originalMajorModel = getRandomMajorModel();
        MajorDto originalMajorModel = majorDao.getMajorById(5L);
        String currentMajorDesc = originalMajorModel.getDescription();
        String modifiedMajorDesc = currentMajorDesc + "---newUpdate";
        originalMajorModel.setDescription(modifiedMajorDesc);
        /*
         * Now start doing update operation
         */
        MajorDto updatedMajorModel = majorDao.update(originalMajorModel);
        assertMajorModels(originalMajorModel, updatedMajorModel);

        /*
         * now reset MajorModel description back to the original value
         */
        originalMajorModel.setDescription(currentMajorDesc);
        updatedMajorModel = majorDao.update(originalMajorModel);
        assertMajorModels(originalMajorModel, updatedMajorModel);
    }

//    private void assertMajorModels(MajorModel randomMajor, MajorModel retrievedMajorModel) {
//        assertEquals(randomMajor.getId(), retrievedMajorModel.getId());
//        assertEquals(randomMajor.getName(), retrievedMajorModel.getName());
//        assertEquals(randomMajor.getDescription(), retrievedMajorModel.getDescription());
//    }
//
//    private MajorModel createMajorByName(String name) {
//        MajorModel major = new MajorModel();
//        major.setName(name);
//        major.setDescription(name + "--description");
//        return major;
//    }
//
//    private MajorModel getRandomMajorModel() {
//        List<MajorModel> majorModelList = majorDao.getMajors();
//        MajorModel randomMajor = null;
//        if(majorModelList != null && majorModelList.size() > 0) {
//            int randomIndex = getRandomInt(0, majorModelList.size());
//            randomMajor = majorModelList.get(randomIndex);
//        }
//        return randomMajor;
//    }
//
//    private void displayMajors(List<MajorModel> majorList) {
//        logger.info("The total number of Majors is: {}", majorList.size());
//        int index = 1;
//        for(MajorModel major : majorList) {
//            logger.info("No.{} Major:", index);
//            displayMajor(major);
//            index++;
//        }
//    }
//
//    private void displayMajor(MajorModel major) {
//        logger.info("Major detail={}", major);
//        displayStudentList(major.getStudentList());
//    }
//
//    private void displayStudentList(List<StudentModel> studentList) {
//        logger.info("The total number of Students is: {}", studentList.size());
//        int index = 1;
//        for(StudentModel student : studentList) {
//            logger.info("No.{} Student:", index);
//            displayStudent(student);
//            index++;
//        }
//    }
//
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
//
//
//    /**
//     * This helper method return a random int value in a range between
//     * min (inclusive) and max (exclusive)
//     * @param min
//     * @param max
//     * @return
//     */
//    private int getRandomInt(int min, int max) {
//        Random random = new Random();
//        return random.nextInt(max - min) + min;
//    }

}
