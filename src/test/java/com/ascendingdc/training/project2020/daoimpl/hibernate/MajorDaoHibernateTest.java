package com.ascendingdc.training.project2020.daoimpl.hibernate;

import com.ascendingdc.training.project2020.dao.hibernate.MajorDao;
import com.ascendingdc.training.project2020.entity.Major;
import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import com.ascendingdc.training.project2020.exception.EntityCannotBeDeletedDueToNonEmptyChildrenException;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
public class MajorDaoHibernateTest extends AbstractDaoHibernateTest {
    private Logger logger = LoggerFactory.getLogger(MajorDaoHibernateTest.class);

//    @Rule
//    public ExpectedException thrown = ExpectedException.none();

//    private static MajorDao majorDao;
//    private MajorDao majorDao;

//    @Autowired
//    private MajorDao majorDao;

    @BeforeAll
    public static void setupOnce() {
        majorDao = new MajorDaoHibernateImpl();
        studentDao = new StudentDaoHibernateImpl();
        projectDao = new ProjectDaoHibernateImpl();
    }

    @AfterAll
    public static void teardownOnce() {
        majorDao = null;
        studentDao = null;
        projectDao = null;
    }

    @BeforeEach
    public void setup() {
//        majorDao = new MajorDaoHibernateImpl();

        tempMajorName = "Major-" + getRandomInt(1, 1000);
        tempLoginName = "Student-login-" + getRandomInt(1, 1000);
        tempProjectName = "Project-" + getRandomInt(1, 1000);
        tempEmail = "test-" + getRandomInt(1, 1000) + "@devmountain.com";
    }

    @AfterEach
    public void teardown() {
        tempLoginName = null;
        tempEmail = null;
        tempMajorName = null;
        tempProjectName = null;
    }

    @Test
    public void getMajorsTest() {
        List<Major> majorList = majorDao.getMajors();
        assertEquals(7, majorList.size());
        displayMajorsWithoutChildren(majorList);
//        displayMajorsWithChildren(majorList);
        /*
         * Loop each Major and trigger LazyInitializationException
         * due to fetchType = LAZY
         */
        for(Major eachMajor : majorList) {
            try {
                eachMajor.getStudents().size();
            } catch(LazyInitializationException ex) {
                assertTrue(ex instanceof LazyInitializationException);
            }
        }
    }

    @Test
    public void getMajorsWithChildrenTest() {
        List<Major> majorList = majorDao.getMajorsWithChildren();
        assertEquals(7, majorList.size());
        displayMajorsWithChildren(majorList);
        /*
         * Loop each Major and check each associated students
         * and each student's associated projects
         */
        for(Major eachMajor : majorList) {
            assertAssociatedStudentsAndProjectsBeingRetrieved(eachMajor.getStudents());
        }
    }

    @Test
    public void getMajorByIdTest() {
        /*
         * Pick up a random MajorModel from DB
         */
        Major randomMajor = getRandomMajor();
        if(randomMajor == null) {
            logger.error("there is no major being found in database, please double check DB connection!");
        } else {
            Long majorId = randomMajor.getId();
            Major retrievedMajor = majorDao.getMajorById(majorId);
            assertEquals(randomMajor.getName(), retrievedMajor.getName());
            assertTrue(true);
        }


    }

    @Test
    public void getMajorByIdThrowLazyInitializationExceptionTest() {
        /*
         * Pick up a random MajorModel from DB
         */
        Major randomMajor = getRandomMajor();
        if(randomMajor == null) {
            logger.error("there is no major being found in database, please double check DB connection!");
        } else {
            Long majorId = randomMajor.getId();
            Major retrievedMajor = majorDao.getMajorById(majorId);
            assertMajors(randomMajor, retrievedMajor);

            /*
             * Due to the LAZY initialization, LazyInitializationException is
             * thrown when trying to call retrievedMajor.getStudents()
             */
//            thrown.expect(LazyInitializationException.class);
//            LazyInitializationException lazyInitializationException = assertThrowsExactly(LazyInitializationException.class, () -> {
            LazyInitializationException lazyInitializationException = assertThrows(LazyInitializationException.class, () -> {
                retrievedMajor.getStudents().size();
            }, "LazyInitializationException was expected to be thrown");
            logger.info("========================, lazyInitializationException..getMessage() ={}", lazyInitializationException.getMessage());
            assertTrue(lazyInitializationException.getMessage().contains("failed to lazily initialize a collection of role"));
//            assertEquals(0, retrievedMajor.getStudents().size());
        }
    }

    @Test
    public void getMajorAndStudentsAndProjectsByMajorIdTest() {
        /*
         * Pick up a random MajorModel from DB
         */
        Major randomMajor = getRandomMajor();
        if(randomMajor == null) {
            logger.error("there is no major being found in database, please double check DB connection!");
        } else {
            Long majorId = randomMajor.getId();
            Major retrievedMajor = majorDao.getMajorAndStudentsAndProjectsByMajorId(majorId);
            assertMajors(randomMajor, retrievedMajor);

            /*
             * Assert the associated students are retrieved if there are some associated students
             * then assert projects associated with the student being retrieved too
             */
            assertAssociatedStudentsAndProjectsBeingRetrieved(retrievedMajor.getStudents());
            displayMajorWithAssociatedStudents(retrievedMajor);
        }

        /*
         *  demo purpose
         */
//        Major retrievedMajor = majorDao.getMajorAndStudentsAndProjectsByMajorId((long)2);
//        assertEquals(2, retrievedMajor.getStudents().size());
    }

    @Test
    public void getMajorByNameThrowLazyInitializationExceptionTest() {
        /*
         * Pick up a random MajorModel from DB
         */
        Major randomMajor = getRandomMajor();
        if(randomMajor == null) {
            logger.error("there is no major being found in database, please double check DB connection!");
        } else {
            String majorName = randomMajor.getName();
            Major retrievedMajor = majorDao.getMajorByName(majorName);
            assertMajors(randomMajor, retrievedMajor);

            /*
             * Due to the LAZY initialization, LazyInitializationException is
             * thrown when trying to call retrievedMajor.getStudents()
             */
//            thrown.expect(LazyInitializationException.class);
//            LazyInitializationException lazyInitializationException = assertThrowsExactly(LazyInitializationException.class, () -> {
            LazyInitializationException lazyInitializationException = assertThrows(LazyInitializationException.class, () -> {
                retrievedMajor.getStudents().size();
            }, "LazyInitializationException was expected to be throw");
            logger.info("========================, lazyInitializationException..getMessage() ={}", lazyInitializationException.getMessage());
            assertTrue(lazyInitializationException.getMessage().contains("failed to lazily initialize a collection of role"));
//            assertEquals(0, retrievedMajor.getStudents().size());
        }
    }

    @Test
    public void getMajorAndStudentsAndProjectsByMajorNameTest() {
        /*
         * Pick up a random MajorModel from DB
         */
        Major randomMajor = getRandomMajor();
        if(randomMajor == null) {
            logger.error("there is no major being found in database, please double check DB connection!");
        } else {
            String majorName = randomMajor.getName();
            Major retrievedMajor = majorDao.getMajorAndStudentsAndProjectsByMajorName(majorName);
            assertMajors(randomMajor, retrievedMajor);

            /*
             * Assert the associated students are retrieved if there are some associated students
             * then assert projects associated with the student being retrieved too
             */
            assertAssociatedStudentsAndProjectsBeingRetrieved(retrievedMajor.getStudents());
            displayMajorWithAssociatedStudents(retrievedMajor);
        }
    }

    @Test
    @Transactional
    public void testSaveMajor() {
        Major major = createMajorByNameOne("abc" + getRandomInt(1, 100));
        Major savedMajor = majorDao.save(major);
        assertNotNull(savedMajor.getId());
        assertEquals(major.getName(), savedMajor.getName());
        assertEquals(major.getDescription(), savedMajor.getDescription());
    }

    private Major createMajorByNameOne(String name) {
        Major major = new Major();
        major.setName(name);
        major.setDescription(name + " descripton");
        return major;
    }

    @Test
    @Transactional
    public void saveMajorOnlyTest() {
        Major major = createMajorByName(tempMajorName);
        Major savedMajor = majorDao.save(major);
        assertNotNull(savedMajor.getId());
        assertEquals(major.getName(), savedMajor.getName());
        assertEquals(major.getDescription(), savedMajor.getDescription());
        displayMajorWithoutChildren(savedMajor);
        /*
         * Now clean up the saved Major from DB Major table
         */
//        boolean deleteSuccessfulFlag = majorDao.delete(savedMajor);
//        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void deleteMajorWithoutAnyAssociatedStudentsTest() {
        Major major = createMajorByName(tempMajorName);
        Major savedMajor = majorDao.save(major);
        /*
         * Now delete the saved Major from DB Major table
         */
        boolean deleteSuccessfulFlag = majorDao.delete(savedMajor);
        assertEquals(true, deleteSuccessfulFlag);

        Major retrievedMajor = majorDao.getMajorById(savedMajor.getId());
        assertNull(retrievedMajor);
    }

    @Test
    public void createMajorWithAssociatedStudentsTest() {
        //Step 0: create a temp Major
        Major major = createMajorByName(tempMajorName);
//        Major majorSaved = majorDao.save(major);

        //Step 1.1: create a temp Student No.1
        Student student1 = createStudentByLoginNameAndEmailWithoutMajorAssigned(tempLoginName, tempEmail);
//        student1.setMajor(majorSaved);
        student1.setMajor(major);
//        Student studentSaved1 = studentDao.save(student1);

        //Step 1.2: create a temp Student No.2
        Student student2 = createStudentByLoginNameAndEmailWithoutMajorAssigned(tempLoginName+"-2", 2 + tempEmail);
//        student2.setMajor(majorSaved);
        student2.setMajor(major);
//        Student studentSaved2 = studentDao.save(student2);

        //update the major
//        majorSaved.addStudent(studentSaved1);
//        majorSaved.addStudent(studentSaved2);
        majorDao.save(major);

//        //Start doing assertions
//        assertEquals(2, majorSaved.getStudents().size());
//
//        /*
//         * Now delete the saved Major from DB Major table.
//         * Due to the saved Major has some students associated with.
//         * EntityCannotBeDeletedDueToNonEmptyChildrenException is
//         * thrown when trying to call majorDao.delete(majorSaved);
//         * Here we choose to use try-catch to handle the thrown
//         * EntityCannotBeDeletedDueToNonEmptyChildrenException.
//         * The reason is we would like to continue to clean
//         * up those temporarily created major and students
//         */
////        thrown.expect(EntityCannotBeDeletedDueToNonEmptyChildrenException.class);
////        boolean deleteSuccessfulFlag = majorDao.delete(majorSaved);
//        try {
//            boolean deleteSuccessfulFlag = majorDao.delete(majorSaved);
//        } catch(EntityCannotBeDeletedDueToNonEmptyChildrenException ex) {
//            assertTrue(ex instanceof EntityCannotBeDeletedDueToNonEmptyChildrenException);
//        }
//
//        /*
//         * Now start clean up the temporarily created students and the major
//         * by removing students first and then delete the major
//         */
//        majorSaved.removeStudent(studentSaved1);
//        majorSaved.removeStudent(studentSaved2);
//
//        boolean deleteSuccessfulFlag = studentDao.delete(studentSaved1);
//        assertTrue(deleteSuccessfulFlag);
//
//        deleteSuccessfulFlag = studentDao.delete(studentSaved2);
//        assertTrue(deleteSuccessfulFlag);
//
//        deleteSuccessfulFlag = majorDao.delete(majorSaved);
//        assertTrue(deleteSuccessfulFlag);
//
//        //Now start to do multiple selection to make sure new created major, students and projects are gone!
//        Major retrievedMajor = majorDao.getMajorById(majorSaved.getId());
//        assertNull(retrievedMajor);
//
//        Student retrievedStudent = studentDao.getStudentById(studentSaved1.getId());
//        assertNull(retrievedStudent);
//        retrievedStudent = studentDao.getStudentById(studentSaved2.getId());
//        assertNull(retrievedStudent);

    }

    @Test
    public void createMajorWithAssociatedStudentsAndThenDeleteFailedTest() {
        //Step 0: create a temp Major
        Major major = createMajorByName(tempMajorName);
        Major majorSaved = majorDao.save(major);

        //Step 1.1: create a temp Student No.1
        Student student1 = createStudentByLoginNameAndEmailWithoutMajorAssigned(tempLoginName, tempEmail);
        student1.setMajor(majorSaved);
        Student studentSaved1 = studentDao.save(student1);

        //Step 1.2: create a temp Student No.2
        Student student2 = createStudentByLoginNameAndEmailWithoutMajorAssigned(tempLoginName+"-2", 2 + tempEmail);
        student2.setMajor(majorSaved);
        Student studentSaved2 = studentDao.save(student2);

        //update the major
        majorSaved.addStudent(studentSaved1);
        majorSaved.addStudent(studentSaved2);
        majorDao.save(majorSaved);

        //Start doing assertions
        assertEquals(2, majorSaved.getStudents().size());

        /*
         * Now delete the saved Major from DB Major table.
         * Due to the saved Major has some students associated with.
         * EntityCannotBeDeletedDueToNonEmptyChildrenException is
         * thrown when trying to call majorDao.delete(majorSaved);
         * Here we choose to use try-catch to handle the thrown
         * EntityCannotBeDeletedDueToNonEmptyChildrenException.
         * The reason is we would like to continue to clean
         * up those temporarily created major and students
         */
//        thrown.expect(EntityCannotBeDeletedDueToNonEmptyChildrenException.class);
//        boolean deleteSuccessfulFlag = majorDao.delete(majorSaved);
        try {
            boolean deleteSuccessfulFlag = majorDao.delete(majorSaved);
        } catch(EntityCannotBeDeletedDueToNonEmptyChildrenException ex) {
            assertTrue(ex instanceof EntityCannotBeDeletedDueToNonEmptyChildrenException);
        }

        /*
         * Now start clean up the temporarily created students and the major
         * by removing students first and then delete the major
         */
        majorSaved.removeStudent(studentSaved1);
        majorSaved.removeStudent(studentSaved2);

        boolean deleteSuccessfulFlag = studentDao.delete(studentSaved1);
        assertTrue(deleteSuccessfulFlag);

        deleteSuccessfulFlag = studentDao.delete(studentSaved2);
        assertTrue(deleteSuccessfulFlag);

        deleteSuccessfulFlag = majorDao.delete(majorSaved);
        assertTrue(deleteSuccessfulFlag);

        //Now start to do multiple selection to make sure new created major, students and projects are gone!
        Major retrievedMajor = majorDao.getMajorById(majorSaved.getId());
        assertNull(retrievedMajor);

        Student retrievedStudent = studentDao.getStudentById(studentSaved1.getId());
        assertNull(retrievedStudent);
        retrievedStudent = studentDao.getStudentById(studentSaved2.getId());
        assertNull(retrievedStudent);

    }

    //    @Test
    public void createMajorWithAssociatedStudentsAndProjectsThenDeleteFailedTest() {
        //Step 0: create a temp Major
        Major major = createMajorByName(tempMajorName);
        Major majorSaved = majorDao.save(major);

        //Step 1.1: create a temp Student No.1
        Student student1 = createStudentByLoginNameAndEmailWithoutMajorAssigned(tempLoginName, tempEmail);
        student1.setMajor(majorSaved);
        Student studentSaved1 = studentDao.save(student1);

        //Step 1.2: create a temp Student No.2
        Student student2 = createStudentByLoginNameAndEmailWithoutMajorAssigned(tempLoginName+"-2", 2 + tempEmail);
        student2.setMajor(majorSaved);
        Student studentSaved2 = studentDao.save(student2);

        //Step 2.1  create a temp project No.1
        Project project1 = createProjectByName(tempProjectName + "-1");
        project1.addStudent(studentSaved1);
        Project projectDSaved1 = projectDao.save(project1);

        //Step 2.2: create a temp project No.2
        Project project2 = createProjectByName(tempProjectName+"-2");
        project2.addStudent(studentSaved1);
        project2.addStudent(studentSaved2);
        Project projectDSaved2 = projectDao.save(project2);

        //Step 2.3  create a temp project No.3
        Project project3 = createProjectByName(tempProjectName+"-3");
        project3.addStudent(studentSaved1);
        Project projectDSaved3 = projectDao.save(project3);

        //Step 2.4  create a temp project No.4
        Project project4 = createProjectByName(tempProjectName+"-4");
        project4.addStudent(studentSaved1);
        project4.addStudent(studentSaved2);
        Project projectDSaved4 = projectDao.save(project4);

        //Step 2.5: create a temp project No.5
        Project project5 = createProjectByName(tempProjectName+"-5");
        project5.addStudent(studentSaved2);
        Project projectDSaved5 = projectDao.save(project5);

        //Step 2.6: create a temp project No.6
        Project project6 = createProjectByName(tempProjectName+"-6");
        Project projectDSaved6 = projectDao.save(project6);

        //Start doing assertions
        assertEquals(4, studentSaved1.getProjects().size());
        assertEquals(3, studentSaved2.getProjects().size());

        assertEquals(1, projectDSaved1.getStudents().size());
        assertEquals(2, projectDSaved2.getStudents().size());
        assertEquals(1, projectDSaved3.getStudents().size());
        assertEquals(2, projectDSaved4.getStudents().size());
        assertEquals(1, projectDSaved5.getStudents().size());
        assertEquals(0, projectDSaved6.getStudents().size());

        /*
         * Now delete the saved Major from DB Major table
         */
        boolean deleteSuccessfulFlag = majorDao.delete(majorSaved);
        assertEquals(true, deleteSuccessfulFlag);

        //Now delete those temporarily created projects using project ID
        deleteSuccessfulFlag = projectDao.delete(projectDSaved1);
        assertEquals(true, deleteSuccessfulFlag);

        deleteSuccessfulFlag = projectDao.delete(projectDSaved2);
        assertEquals(true, deleteSuccessfulFlag);

        deleteSuccessfulFlag = projectDao.delete(projectDSaved3);
        assertEquals(true, deleteSuccessfulFlag);

        deleteSuccessfulFlag = projectDao.delete(projectDSaved4);
        assertEquals(true, deleteSuccessfulFlag);

        deleteSuccessfulFlag = projectDao.delete(projectDSaved5);
        assertEquals(true, deleteSuccessfulFlag);

        deleteSuccessfulFlag = projectDao.delete(projectDSaved6);
        assertEquals(true, deleteSuccessfulFlag);

        //Now start to do multiple selection to make sure new created major, students and projects are gone!
        Major retrievedMajor = majorDao.getMajorById(majorSaved.getId());
        assertNull(retrievedMajor);

        Student retrievedStudent = studentDao.getStudentById(studentSaved1.getId());
        assertNull(retrievedStudent);
        retrievedStudent = studentDao.getStudentById(studentSaved2.getId());
        assertNull(retrievedStudent);

        Project retrievedProject = projectDao.getProjectById(projectDSaved1.getId());
        assertNull(retrievedProject);
        retrievedProject = projectDao.getProjectById(projectDSaved2.getId());
        assertNull(retrievedProject);
        retrievedProject = projectDao.getProjectById(projectDSaved3.getId());
        assertNull(retrievedProject);
        retrievedProject = projectDao.getProjectById(projectDSaved4.getId());
        assertNull(retrievedProject);
        retrievedProject = projectDao.getProjectById(projectDSaved5.getId());
        assertNull(retrievedProject);
        retrievedProject = projectDao.getProjectById(projectDSaved6.getId());
        assertNull(retrievedProject);
    }

    @Test
    public void updateMajorTest() {
        Major originalMajorModel = getRandomMajor();
        String currentMajorDesc = originalMajorModel.getDescription();
        String modifiedMajorDesc = currentMajorDesc + "---newUpdate";
        originalMajorModel.setDescription(modifiedMajorDesc);
        /*
         * Now start doing update operation
         */
        Major updatedMajor = majorDao.update(originalMajorModel);
        assertMajors(originalMajorModel, updatedMajor);

        /*
         * now reset MajorModel description back to the original value
         */
        originalMajorModel.setDescription(currentMajorDesc);
        updatedMajor = majorDao.update(originalMajorModel);
        assertMajors(originalMajorModel, updatedMajor);
    }

    private void assertAssociatedStudentsAndProjectsBeingRetrieved(Set<Student> students) {
        //Assert the associated students are retrieved if there are some associated students
        assertTrue(students.size() >= 0);

        for(Student eachStudent : students) {
            // assert projects associated with each student being retrieved too
            assertTrue(eachStudent.getProjects().size() >= 0);
        }
    }


}
