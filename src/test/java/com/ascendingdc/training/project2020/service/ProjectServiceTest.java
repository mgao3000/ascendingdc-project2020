package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dao.hibernate.ProjectDao;
import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import com.ascendingdc.training.project2020.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTest {

    private Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

    @Mock(name="projectSpringDataJPADao")
    private ProjectDao mockProjectDao;

    @Mock
    private Project mockProject;

    @Mock
    private ProjectDto mockProjectDto;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private String tempMajorName;

    private String tempProjectName;

    private String tempLoginName = null;

    private String tempEmail = null;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tempLoginName = "Student-login-" + getRandomInt(1, 1000);
        tempEmail = "test" + getRandomInt(1, 1000) + "@devmountain.com";
        tempMajorName = "Random-Majoe" + getRandomInt(1, 1000);
        tempProjectName = "Project-" + getRandomInt(1, 1000);
    }

    @AfterEach
    public void teardown() {
        tempLoginName = null;
        tempEmail = null;
        tempMajorName = null;
        tempProjectName = null;

//        studentDao = null;
//        majorDao = null;
        mockProjectDao = null;
    }

    @Test
    public void testSaveProject() {
        when(mockProjectDto.convertProjectDtoToProject()).thenReturn(mockProject);
        when(mockProject.convertProjectToProjectDto()).thenReturn(mockProjectDto);
        when(mockProjectDao.save(mockProject)).thenReturn(mockProject);

        projectService.save(mockProjectDto);

        verify(mockProjectDao, times(1)).save(mockProject);
        verify(mockProject, times(1)).convertProjectToProjectDto();
        verify(mockProjectDto, times(1)).convertProjectDtoToProject();
    }

    @Test
    public void testUpdateProject() {
        when(mockProjectDto.convertProjectDtoToProject()).thenReturn(mockProject);
        when(mockProject.convertProjectToProjectDto()).thenReturn(mockProjectDto);
        when(mockProjectDao.update(mockProject)).thenReturn(mockProject);

        projectService.update(mockProjectDto);

        verify(mockProjectDao, times(1)).update(mockProject);
        verify(mockProject, times(1)).convertProjectToProjectDto();
        verify(mockProjectDto, times(1)).convertProjectDtoToProject();
    }

    @Test
    public void testDeleteProjectByName() {
        when(mockProjectDao.deleteByName((anyString()))).thenReturn(true);

        boolean deleteResult = projectService.deleteByName(anyString());

        assertTrue(true, "The deleteByName call should return true");

        verify(mockProjectDao, times(1)).deleteByName(anyString());
    }

    @Test
    public void testDeleteProjectById() {
        when(mockProjectDao.deleteById((anyLong()))).thenReturn(true);

        boolean deleteResult = projectService.deleteById(anyLong());

        assertTrue(true, "The deleteById call should return true");

        verify(mockProjectDao, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteProjectByProjectDto() {
        when(mockProjectDto.convertProjectDtoToProject()).thenReturn(mockProject);
        when(mockProjectDao.delete(mockProject)).thenReturn(true);

        boolean deleteResult = projectService.delete(mockProjectDto);

        assertTrue(true, "The delete(projectDto) call should return true");

        verify(mockProjectDto, times(1)).convertProjectDtoToProject();
        verify(mockProjectDao, times(1)).delete(mockProject);
    }

    @Test
    public void testFindAllProjects() {
        List<Project> spyProjectList = spy(ArrayList.class);
        spyProjectList.add(mockProject);
        spyProjectList.add(mockProject);
        spyProjectList.add(mockProject);

        when(mockProject.convertProjectToProjectDto()).thenReturn(mockProjectDto);
        when(mockProjectDao.getProjects()).thenReturn(spyProjectList);

        List<ProjectDto> projectDtoList = projectService.getProjects();

        assertEquals(3, projectDtoList.size(), "The projectDtoList.size() should return 3");

        verify(mockProject, times(3)).convertProjectToProjectDto();
        verify(mockProjectDao, times(1)).getProjects();
    }

    @Test
    public void testFindProjectByProjectId() {
        when(mockProjectDao.getProjectById(anyLong())).thenReturn(mockProject);
        when(mockProject.convertProjectToProjectDto()).thenReturn(mockProjectDto);

        ProjectDto projectDto = projectService.getProjectById(anyLong());

        verify(mockProject, times(1)).convertProjectToProjectDto();
        verify(mockProjectDao, times(1)).getProjectById(anyLong());
    }

    @Test
    public void testFindProjectByProjectName() {
        when(mockProjectDao.getProjectByName(anyString())).thenReturn(mockProject);
        when(mockProject.convertProjectToProjectDto()).thenReturn(mockProjectDto);

        ProjectDto projectDto = projectService.getProjectByName(anyString());

        verify(mockProject, times(1)).convertProjectToProjectDto();
        verify(mockProjectDao, times(1)).getProjectByName(anyString());
    }

    @Disabled
    public void testGetProjectsWithAssociatedStudents() {

    }

    @Disabled
    public void testGetProjectWithAssociatedStudentsById() {

    }

    @Disabled
    public void testGetProjectWithAssociatedStudentsByName() {

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
