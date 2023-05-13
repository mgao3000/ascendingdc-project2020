package com.ascendingdc.training.project2020.daoimpl.jdbc;

import com.ascendingdc.training.project2020.dao.jdbc.MajorDao;
import com.ascendingdc.training.project2020.dao.jdbc.ProjectDao;
import com.ascendingdc.training.project2020.dao.jdbc.StudentDao;
import com.ascendingdc.training.project2020.dao.jdbc.StudentProjectDao;
import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractDaoJDBCTest {
    private Logger logger = LoggerFactory.getLogger(AbstractDaoJDBCTest.class);

    protected MajorDao majorDao;

    protected StudentDao studentDao;

    protected ProjectDao projectDao;

    protected StudentProjectDao studentProjectDao;

    protected String tempMajorName;

    protected String tempProjectName;

    protected String tempLoginName = null;

    protected String randomEmail = null;

    protected void assertMajorModels(MajorDto randomMajor, MajorDto retrievedMajorModel) {
        assertEquals(randomMajor.getId(), retrievedMajorModel.getId());
        assertEquals(randomMajor.getName(), retrievedMajorModel.getName());
        assertEquals(randomMajor.getDescription(), retrievedMajorModel.getDescription());
    }

    protected void assertProjectModels(ProjectDto randomProjectModel, ProjectDto retrievedProjectModel) {
        assertEquals(randomProjectModel.getId(), retrievedProjectModel.getId());
        assertEquals(randomProjectModel.getName(), retrievedProjectModel.getName());
        assertEquals(randomProjectModel.getDescription(), retrievedProjectModel.getDescription());
        assertTrue(randomProjectModel.getCreateDate().isEqual(retrievedProjectModel.getCreateDate()));
    }

    protected void assertStudentModels(StudentDto randomStudentModel, StudentDto retrievedStudentModel) {
        assertEquals(randomStudentModel.getId(), retrievedStudentModel.getId());
        assertEquals(randomStudentModel.getMajorId(), retrievedStudentModel.getMajorId());
        assertEquals(randomStudentModel.getLoginName(), retrievedStudentModel.getLoginName());
        assertEquals(randomStudentModel.getPassword(), retrievedStudentModel.getPassword());
        assertEquals(randomStudentModel.getFirstName(), retrievedStudentModel.getFirstName());
        assertEquals(randomStudentModel.getLastName(), retrievedStudentModel.getLastName());
        assertEquals(randomStudentModel.getEmail(), retrievedStudentModel.getEmail());
        assertEquals(randomStudentModel.getAddress(), retrievedStudentModel.getAddress());
        assertTrue(randomStudentModel.getEnrolledDate().isEqual(retrievedStudentModel.getEnrolledDate()));
    }

    protected ProjectDto createProjectByName(String projectName) {
        ProjectDto project = new ProjectDto();
        project.setName(projectName);
        project.setDescription(projectName + "--description");
        project.setCreateDate(LocalDate.now());
        return project;
    }

    protected MajorDto createMajorByName(String name) {
        MajorDto major = new MajorDto();
        major.setName(name);
        major.setDescription(name + "--description");
        return major;
    }

    protected StudentDto createStudentByLoginNameAndEmail(String loginName, String email) {
        StudentDto student = new StudentDto();
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
        MajorDto randomMajor = getRandomMajorModel();
        student.setMajorId(randomMajor.getId());

        return student;
    }

    protected ProjectDto getRandomProjectModel() {
        List<ProjectDto> projectModelList = projectDao.getProjects();
        ProjectDto randomProject = null;
        if(projectModelList != null && projectModelList.size() > 0) {
            int randomIndex = getRandomInt(0, projectModelList.size());
            randomProject = projectModelList.get(randomIndex);
        }
        return randomProject;
    }

    protected MajorDto getRandomMajorModel() {
        List<MajorDto> majorModelList = majorDao.getMajors();
        MajorDto randomMajor = null;
        if(majorModelList != null && majorModelList.size() > 0) {
            int randomIndex = getRandomInt(0, majorModelList.size());
            randomMajor = majorModelList.get(randomIndex);
        }
        return randomMajor;
    }

    protected StudentDto getRandomStudentModel() {
        List<StudentDto> studentModelList = studentDao.getStudents();
        StudentDto randomStudent = null;
        if(studentModelList != null && studentModelList.size() > 0) {
            int randomIndex = getRandomInt(0, studentModelList.size());
            randomStudent = studentModelList.get(randomIndex);
        }
        return randomStudent;
    }

    protected void displayStudents(List<StudentDto> studentList) {
        logger.info("The total number of Students is: {}", studentList.size());
        int index = 1;
        for(StudentDto student : studentList) {
            logger.info("No.{} Student:", index);
            displayStudent(student);
            index++;
        }
    }

    protected void displayProjects(List<ProjectDto> projectList) {
        logger.info("The total number of Projects is: {}", projectList.size());
        int index = 1;
        for(ProjectDto eachProject : projectList) {
            logger.info("No.{} project:", index);
            displayProject(eachProject);
            index++;
        }
    }

    protected void displayProject(ProjectDto project) {
        logger.info("Project detail={}", project);
        displayStudentList(project.getStudentDtoList());
    }

    protected void displayStudentList(List<StudentDto> studentList) {
        logger.info("\t The total associated students={}", studentList.size());
        int index = 1;
        for (StudentDto student : studentList) {
            logger.info("No.{} student = {}", index, student);
            if(student.getProjectDtoList() != null) {
                logger.info("\t The total associated projects with studentId={}", student.getId());
                displayProjectList(student.getProjectDtoList());
            }
            index++;
        }
    }

    protected void displayMajors(List<MajorDto> majorList) {
        logger.info("The total number of Majors is: {}", majorList.size());
        int index = 1;
        for(MajorDto major : majorList) {
            logger.info("No.{} Major:", index);
            displayMajor(major);
            index++;
        }
    }

    protected void displayMajor(MajorDto major) {
        logger.info("Major detail={}", major);
        displayStudentList(major.getStudentDtoList());
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

    protected void displayStudent(StudentDto student) {
        logger.info("Student detail={}", student);
        displayProjectList(student.getProjectDtoList());
    }

    protected void displayProjectList(List<ProjectDto> projectList) {
        logger.info("\t The total associated projects={}", projectList.size());
        int index = 1;
        for (ProjectDto project : projectList) {
            logger.info("No.{} project = {}", index, project);
            index++;
        }
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
