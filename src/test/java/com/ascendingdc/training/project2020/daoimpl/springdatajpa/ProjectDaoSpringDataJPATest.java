package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

//import com.devmountain.training.dao.ProjectDao;

import com.ascendingdc.training.project2020.daoimpl.hibernate.AbstractDaoHibernateTest;
import com.ascendingdc.training.project2020.daoimpl.hibernate.MajorDaoHibernateImpl;
import com.ascendingdc.training.project2020.daoimpl.hibernate.ProjectDaoHibernateImpl;
import com.ascendingdc.training.project2020.daoimpl.hibernate.StudentDaoHibernateImpl;
import com.ascendingdc.training.project2020.entity.Major;
import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectDaoSpringDataJPATest extends AbstractDaoSpringDataJPATest {
    private Logger logger = LoggerFactory.getLogger(ProjectDaoSpringDataJPATest.class);

//    public ExpectedException thrown = ExpectedException.none();

    @BeforeAll
    public static void setupOnce() {
//        majorDao = new MajorDaoHibernateImpl();
//        studentDao = new StudentDaoHibernateImpl();
//        projectDao = new ProjectDaoHibernateImpl();
    }

    @AfterAll
    public static void teardownOnce() {
//        majorDao = null;
//        studentDao = null;
//        projectDao = null;
    }

    @BeforeEach
    public void setup() {
        tempMajorName = "Random-Majoe" + getRandomInt(1, 1000);
        tempLoginName = "Random-LoginName" + getRandomInt(1, 1000);
        tempEmail = "Test" + getRandomInt(1, 1000) + "@google.com";
        tempProjectName = "Project-" + getRandomInt(1, 1000);
    }

    @AfterEach
    public void teardown() {
        tempLoginName = null;
        tempEmail = null;
        tempMajorName = null;
        tempProjectName = null;

        majorDao = null;
        studentDao = null;
        projectDao = null;
    }

    @Test
    public void getProjectsTest() {
        List<Project> projectList = projectDao.getProjects();
        assertEquals(10, projectList.size());
//        displayProjects(projectList);
        /*
         * Loop each Project and trigger LazyInitializationException
         * due to fetchType = LAZY
         */
        for(Project eachProject : projectList) {
            try {
                eachProject.getStudents().size();
            } catch(LazyInitializationException ex) {
                assertTrue(ex instanceof LazyInitializationException);
            }
        }
    }

    @Test
    public void getProjectsWithAssociatedStudentsTest() {
        List<Project> projectList = projectDao.getProjectsWithAssociatedStudents();
        assertEquals(10, projectList.size());
        displayProjects(projectList);
        /*
         * Loop each Project and check each associated students
         * and each student's associated Major
         */
        for(Project eachProject : projectList) {
            assertAssociatedStudentsAndMajorStudentBelongBeingRetrieved(eachProject);
        }
    }

    private void assertAssociatedStudentsAndMajorStudentBelongBeingRetrieved(Project project) {
        //Assert the associated students are retrieved if there are some associated students
        assertTrue(project.getStudents().size() >= 0);

        //Make sure Student's major is retrieved too
        for(Student eachStudent : project.getStudents()) {
            assertNotNull(eachStudent.getMajor());
        }
    }

    @Test
    public void getProjectByIdTest() {
        /*
         * Pick up a random Project from DB
         */
        Project randomProject = getRandomProject();
        if(randomProject == null) {
            logger.error("there is no project being found in database, please double check DB connection!");
        } else {
            Long projectId = randomProject.getId();
            Project retrievedProject = projectDao.getProjectById(projectId);
            assertProjects(randomProject, retrievedProject);
            displayProjectWithoutAssociatedStudents(retrievedProject);

            /*
             * Due to the LAZY initialization, LazyInitializationException is
             * thrown when trying to call retrievedProject.getStudents()
             */
//            thrown.expect(LazyInitializationException.class);
//            LazyInitializationException lazyInitializationException = assertThrowsExactly(LazyInitializationException.class, () -> {
            LazyInitializationException lazyInitializationException = assertThrows(LazyInitializationException.class, () -> {
                retrievedProject.getStudents().size();
            }, "LazyInitializationException was expected to be thrown");
            logger.info("========================, lazyInitializationException..getMessage() ={}", lazyInitializationException.getMessage());
            assertTrue(lazyInitializationException.getMessage().contains("failed to lazily initialize a collection of role"));

//            assertEquals(0, retrievedProject.getStudents().size());
//            try {
//                retrievedProject.getStudents().size();
//            } catch(LazyInitializationException ex) {
//                assertTrue(ex instanceof LazyInitializationException);
//            }

        }
    }

    @Test
    public void getProjectWithAssociatedStudentsByIdTest() {
        /*
         * Pick up a random Project from DB
         */
        Project randomProject = getRandomProject();
        if(randomProject == null) {
            logger.error("there is no project being found in database, please double check DB connection!");
        } else {
            Long projectId = randomProject.getId();
            Project retrievedProject = projectDao.getProjectWithAssociatedStudentsById(projectId);
            assertProjects(randomProject, retrievedProject);
            /*
             * Assert the associated students are retrieved if there are some associated students
             */
            assertTrue(retrievedProject.getStudents().size() >= 0);
//            displayProject(retrievedProject);
        }
    }

    @Test
    public void getProjectByNameTest() {
        /*
         * Pick up a random Project from DB
         */
        Project randomProject = getRandomProject();
        if(randomProject == null) {
            logger.error("there is no Project being found in database, please double check DB connection!");
        } else {
            String projectName = randomProject.getName();
            Project retrievedProject = projectDao.getProjectByName(projectName);
            assertProjects(randomProject, retrievedProject);
            displayProjectWithoutAssociatedStudents(retrievedProject);
            /*
             * Due to the LAZY initialization, LazyInitializationException is
             * thrown when trying to call retrievedProject.getStudents()
             */
//            thrown.expect(LazyInitializationException.class);
//            LazyInitializationException lazyInitializationException = assertThrowsExactly(LazyInitializationException.class, () -> {
            LazyInitializationException lazyInitializationException = assertThrows(LazyInitializationException.class, () -> {
                retrievedProject.getStudents().size();
            }, "LazyInitializationException was expected to be throw");
            logger.info("========================, lazyInitializationException..getMessage() ={}", lazyInitializationException.getMessage());
            assertTrue(lazyInitializationException.getMessage().contains("failed to lazily initialize a collection of role"));

//            assertEquals(0, retrievedProject.getStudents().size());
        }
    }

    @Test
    public void getProjectWithAssociatedStudentsByNameTest() {
        /*
         * Pick up a random Project from DB
         */
        Project randomProject = getRandomProject();
        if(randomProject == null) {
            logger.error("there is no Project being found in database, please double check DB connection!");
        } else {
            String projectName = randomProject.getName();
            Project retrievedProject = projectDao.getProjectWithAssociatedStudentsByName(projectName);
            assertProjects(randomProject, retrievedProject);
            /*
             * Assert the associated students are retrieved if there are some associated students
             */
            assertTrue(retrievedProject.getStudents().size() >= 0);
            displayProject(retrievedProject);
        }

    }

    @Test
    public void saveProjectOnlyTest() {
        Project project = createProjectByName(tempProjectName);
        Project savedProject = projectDao.save(project);
        assertNotNull(savedProject);
        assertEquals(project.getName(), savedProject.getName());
        assertEquals(project.getDescription(), savedProject.getDescription());
        /*
         * Now clean up the saved Project from DB Major table
         */
        boolean deleteSuccessfulFlag = projectDao.delete(savedProject);
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void deleteProjectWithoutAnyAssociatedStudentsTest() {
        Project project = createProjectByName(tempProjectName);
        Project savedProject = projectDao.save(project);
        /*
         * Now delete the saved Project from DB Major table
         */
        boolean deleteSuccessfulFlag = projectDao.delete(savedProject);
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    @Transactional
    public void deleteProjectByNameTest() {
        Project project = createProjectByName(tempProjectName);
        Project savedProject = projectDao.save(project);
        /*
         * Now delete the saved Project from DB Major table
         */
        boolean deleteSuccessfulFlag = projectDao.deleteByName(savedProject.getName());
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void deleteProjectByIdWithoutAnyAssociatedStudentsTest() {
        Project project = createProjectByName(tempProjectName);
        Project savedProject = projectDao.save(project);
        /*
         * Now delete the saved Project from DB Major table
         */
        boolean deleteSuccessfulFlag = projectDao.deleteById(savedProject.getId());
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    @Transactional
    public void deleteProjectByNameWithoutAnyAssociatedStudentsTest() {
        Project project = createProjectByName(tempProjectName);
        Project savedProject = projectDao.save(project);
        /*
         * Now delete the saved Project from DB Major table
         */
        boolean deleteSuccessfulFlag = projectDao.deleteByName(savedProject.getName());
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void updateProjectTest() {
        Project originalProject = getRandomProject();
        String originalProjectDesc = originalProject.getDescription();
        String modifiedProjectDesc = originalProjectDesc + "---newUpdate";
        originalProject.setDescription(modifiedProjectDesc);
        /*
         * Now start doing update operation
         */
        Project updatedProject = projectDao.update(originalProject);
        assertProjects(originalProject, updatedProject);

        /*
         * now reset ProjectModel description back to the original value
         */
        originalProject.setDescription(originalProjectDesc);
        updatedProject = projectDao.update(originalProject);
        assertProjects(originalProject, updatedProject);
    }

    @Test
    public void addStudentsToProjectTest() {
        //Step 0: create a temp Major
        Major major = createMajorByName(tempMajorName);
        Major majorSaved = majorDao.save(major);
        logger.info("=============, addStudentsToProjectTest(), majorSaved={}", majorSaved);

        //Step 3.1  create a temp project No.1
        Project project1 = createProjectByName(tempProjectName);
        Project projectSaved1 = projectDao.save(project1);

        //Step 3.2: create a temp project No.2
        Project project2 = createProjectByName(tempProjectName+"-2");
        Project projectSaved2 = projectDao.save(project2);

        //Step 1.1: create a temp Student No.1
        Student student1 = createStudentByLoginNameAndEmailWithoutMajorAssigned(tempLoginName, tempEmail);
        student1.setMajor(majorSaved);
        student1.addProject(projectSaved1);
        student1.addProject(projectSaved2);
        Student studentSaved1 = studentDao.save(student1);
        logger.info("=============, addStudentsToProjectTest(), studentSaved1={}", studentSaved1);

        //Step 1.2: create a temp Student No.2
        Student student2 = createStudentByLoginNameAndEmailWithoutMajorAssigned(tempLoginName+"-2", "a2-" + tempEmail);
        student2.setMajor(majorSaved);
        student2.addProject(projectSaved2);
        Student studentSaved2 = studentDao.save(student2);
        logger.info("=============, addStudentsToProjectTest(), studentSaved2={}", studentSaved2);

        //Conduct assertions
        assertEquals(1, projectSaved1.getStudents().size());
        assertEquals(2, projectSaved2.getStudents().size());

        assertEquals(2, studentSaved1.getProjects().size());
        assertEquals(1, studentSaved2.getProjects().size());

        //2 update the major
        majorSaved.addStudent(studentSaved1);
        majorSaved.addStudent(studentSaved2);
        majorDao.update(majorSaved);

        assertEquals(2, majorSaved.getStudents().size());

        // first remove student from project and then update project, finally delete project
//        logger.info("====#########$$$$$$$$$, before do removeStudent, projectSaved1.getStudents().size()={}", projectSaved1.getStudents().size());
//        projectSaved1.removeStudent(studentSaved1);
//        studentDao.update(studentSaved1);
//        boolean deleteSuccessfulFlag = projectDao.delete(projectSaved1);
//        assertEquals(true, deleteSuccessfulFlag);
//
//        projectSaved2.removeStudent(studentSaved1);
//        projectSaved2.removeStudent(studentSaved2);
//        studentDao.update(studentSaved1);
//        studentDao.update(studentSaved2);
////        projectDao.update(projectSaved2);
//        deleteSuccessfulFlag = projectDao.delete(projectSaved2);
//        assertEquals(true, deleteSuccessfulFlag);
//
//        // delete student
//        deleteSuccessfulFlag = studentDao.delete(studentSaved1);
//        assertEquals(true, deleteSuccessfulFlag);
//
//        deleteSuccessfulFlag = studentDao.delete(studentSaved2);
//        assertEquals(true, deleteSuccessfulFlag);
        studentSaved1.removeProject(projectSaved1);
        studentSaved1.removeProject(projectSaved2);
        studentDao.update(studentSaved1);
        majorSaved.removeStudent(studentSaved1);
        boolean deleteSuccessfulFlag = studentDao.delete(studentSaved1);
        assertEquals(true, deleteSuccessfulFlag);

        studentSaved2.removeProject(projectSaved2);
        studentDao.update(studentSaved2);
        majorSaved.removeStudent(studentSaved2);
        deleteSuccessfulFlag = studentDao.delete(studentSaved2);
        assertEquals(true, deleteSuccessfulFlag);

        projectDao.delete(projectSaved1);
        projectDao.delete(projectSaved2);

//        assertEquals(2, majorSaved.getStudents().size());

        // remove projects from student and then update student, finally delete student
        deleteSuccessfulFlag = majorDao.delete(majorSaved);
        assertEquals(true, deleteSuccessfulFlag);

    }


}
