package com.ascendingdc.training.project2020.daoimpl.jdbc;

import com.ascendingdc.training.project2020.daoimpl.springjdbc.ProjectDaoSpringJDBCImpl;
import com.ascendingdc.training.project2020.model.Project;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.List;

//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectDaoJDBCTest extends AbstractDaoJDBCTest {
    private Logger logger = LoggerFactory.getLogger(ProjectDaoJDBCTest.class);

//    private static ProjectDao projectDao;

//    private String tempProjectName = null;
    @Autowired
    private DataSource dataSource;

    @BeforeAll
    public static void setupOnce() {
//        projectDao = new ProjectDaoJDBCImpl();
    }

    @AfterAll
    public static void teardownOnce() {
//        projectDao = null;
    }

    @BeforeEach
    public void setup() {
//        projectDao = new ProjectDaoJDBCImpl();
        projectDao = new ProjectDaoSpringJDBCImpl(dataSource);

        tempProjectName = "Project-" + getRandomInt(1, 1000);
    }

    @AfterEach
    public void teardown() {
        projectDao = null;
    }

    @Test
    public void getProjectsTest() {
        List<Project> projectModelList = projectDao.getProjects();
        displayProjects(projectModelList);
//        int i = 1;
//        for(ProjectModel project : projectModelList) {
//            logger.info("No.{} Project = {}", i, project);
//            i++;
//        }
    }

    @Test
    public void getProjectByIdTest() {
        /*
         * Pick up a random ProjectModel from DB
         */
        Project randomProject = getRandomProjectModel();
        if(randomProject == null) {
            logger.error("there is no project being found in database, please double check DB connection!");
        } else {
            Long projectId = randomProject.getId();
            Project retrievedProjectModel = projectDao.getProjectById(projectId);
            assertProjectModels(randomProject, retrievedProjectModel);
        }
    }

    @Test
    public void getProjectByNameTest() {
        /*
         * Pick up a random ProjectModel from DB
         */
        Project randomProject = getRandomProjectModel();
        if(randomProject == null) {
            logger.error("there is no Project being found in database, please double check DB connection!");
        } else {
            String projectName = randomProject.getName();
            Project retrievedProjectModel = projectDao.getProjectByName(projectName);
            assertProjectModels(randomProject, retrievedProjectModel);
        }
    }

    @Test
    public void saveProjectTest() {
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
    public void deleteProjectTest() {
        Project project = createProjectByName(tempProjectName);
        Project savedProject = projectDao.save(project);
        /*
         * Now delete the saved Project from DB Major table
         */
        boolean deleteSuccessfulFlag = projectDao.delete(savedProject);
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
    public void deleteProjectByIdTest() {
        Project project = createProjectByName(tempProjectName);
        Project savedProject = projectDao.save(project);
        /*
         * Now delete the saved Project from DB Major table
         */
        boolean deleteSuccessfulFlag = projectDao.deleteById(savedProject.getId());
        assertEquals(true, deleteSuccessfulFlag);
    }

    @Test
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
    public void updateProjectTest() {
        Project originalProjectModel = getRandomProjectModel();
        String originalProjectDesc = originalProjectModel.getDescription();
        String modifiedProjectDesc = originalProjectDesc + "---newUpdate";
        originalProjectModel.setDescription(modifiedProjectDesc);
        /*
         * Now start doing update operation
         */
        Project updatedProjectModel = projectDao.update(originalProjectModel);
        assertProjectModels(originalProjectModel, updatedProjectModel);

        /*
         * now reset ProjectModel description back to the original value
         */
        originalProjectModel.setDescription(originalProjectDesc);
        updatedProjectModel = projectDao.update(originalProjectModel);
        assertProjectModels(originalProjectModel, updatedProjectModel);
    }

    @Test
    public void getProjectsWithAssociatedStudentsTest() {
        List<Project> projectModelList = projectDao.getProjectsWithAssociatedStudents();
        displayProjects(projectModelList);
    }

    @Test
    public void getProjectWithAssociatedStudentsByIdTest() {
        /*
         * Pick up a random ProjectModel from DB
         */
        Project randomProject = getRandomProjectModel();
        if(randomProject == null) {
            logger.error("there is no project being found in database, please double check DB connection!");
        } else {
            Long projectId = randomProject.getId();
            Project retrievedProjectModel = projectDao.getProjectWithAssociatedStudentsById(projectId);
            assertProjectModels(randomProject, retrievedProjectModel);
            displayProject(retrievedProjectModel);
        }

    }

    @Test
    public void getProjectWithAssociatedStudentsByNameTest() {
        /*
         * Pick up a random ProjectModel from DB
         */
        Project randomProject = getRandomProjectModel();
        if(randomProject == null) {
            logger.error("there is no Project being found in database, please double check DB connection!");
        } else {
            String projectName = randomProject.getName();
            Project retrievedProjectModel = projectDao.getProjectWithAssociatedStudentsByName(projectName);
            assertProjectModels(randomProject, retrievedProjectModel);
            displayProject(retrievedProjectModel);
        }

    }

//    private void assertProjectModels(ProjectModel randomProjectModel, ProjectModel retrievedProjectModel) {
//        assertEquals(randomProjectModel.getId(), retrievedProjectModel.getId());
//        assertEquals(randomProjectModel.getName(), retrievedProjectModel.getName());
//        assertEquals(randomProjectModel.getDescription(), retrievedProjectModel.getDescription());
//        assertTrue(randomProjectModel.getCreateDate().isEqual(retrievedProjectModel.getCreateDate()));
//    }
//
//    private ProjectModel createProjectByName(String projectName) {
//        ProjectModel project = new ProjectModel();
//        project.setName(projectName);
//        project.setDescription(projectName + "--description");
//        project.setCreateDate(LocalDate.now());
//        return project;
//    }
//
//    private ProjectModel getRandomProjectModel() {
//        List<ProjectModel> projectModelList = projectDao.getProjects();
//        ProjectModel randomProject = null;
//        if(projectModelList != null && projectModelList.size() > 0) {
//            int randomIndex = getRandomInt(0, projectModelList.size());
//            randomProject = projectModelList.get(randomIndex);
//        }
//        return randomProject;
//    }
//
//    private void displayProjects(List<ProjectModel> projectList) {
//        logger.info("The total number of Projects is: {}", projectList.size());
//        int index = 1;
//        for(ProjectModel eachProject : projectList) {
//            logger.info("No.{} project:", index);
//            displayProject(eachProject);
//            index++;
//        }
//    }
//
//    private void displayProject(ProjectModel project) {
//        logger.info("Project detail={}", project);
//        displayStudentList(project.getStudentList());
//    }
//
//    private void displayStudentList(List<StudentModel> studentList) {
//        logger.info("\t The total associated students={}", studentList.size());
//        int index = 1;
//        for(StudentModel student : studentList) {
//            logger.info("No.{} student = {}", index, student);
//            index++;
//        }
//
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
