package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dao.hibernate.StudentDao;
import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
import com.ascendingdc.training.project2020.entity.Major;
import com.ascendingdc.training.project2020.entity.Student;
import com.ascendingdc.training.project2020.service.impl.StudentServiceImpl;
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
public class StudentServiceTest {

    private Logger logger = LoggerFactory.getLogger(StudentServiceTest.class);

    @Mock(name="studentSpringDataJPADao")
    private StudentDao mockStudentDao;

    @Mock
    private Student mockStudent;

    @Mock
    private StudentDto mockStudentDto;

    @InjectMocks
    private StudentServiceImpl studentService;

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

        mockStudentDao = null;
//        majorDao = null;
//        projectDao = null;
    }

    @Test
    public void testSaveStudent() {
        when(mockStudentDto.convertStudentDtoToStudent()).thenReturn(mockStudent);
        when(mockStudent.convertStudentToStudentDto()).thenReturn(mockStudentDto);
        when(mockStudentDao.save(mockStudent)).thenReturn(mockStudent);

        studentService.save(mockStudentDto);

        verify(mockStudentDao, times(1)).save(mockStudent);
        verify(mockStudent, times(1)).convertStudentToStudentDto();
        verify(mockStudentDto, times(1)).convertStudentDtoToStudent();
    }

    @Test
    public void testUpdateStudent() {
        when(mockStudentDto.convertStudentDtoToStudent()).thenReturn(mockStudent);
        when(mockStudent.convertStudentToStudentDto()).thenReturn(mockStudentDto);
        when(mockStudentDao.update(mockStudent)).thenReturn(mockStudent);

        studentService.update(mockStudentDto);

        verify(mockStudentDao, times(1)).update(mockStudent);
        verify(mockStudent, times(1)).convertStudentToStudentDto();
        verify(mockStudentDto, times(1)).convertStudentDtoToStudent();
    }

    @Test
    public void testDeleteStudentByLoginName() {
        when(mockStudentDao.deleteByLoginName((anyString()))).thenReturn(true);

        boolean deleteResult = studentService.deleteByLoginName(anyString());

        assertTrue(true, "The deleteByLoginName call should return true");

        verify(mockStudentDao, times(1)).deleteByLoginName(anyString());
    }

    @Test
    public void testDeleteStudentById() {
        when(mockStudentDao.deleteById((anyLong()))).thenReturn(true);

        boolean deleteResult = studentService.deleteById(anyLong());

        assertTrue(true, "The deleteById call should return true");

        verify(mockStudentDao, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteStudentByStudentDto() {
        when(mockStudentDto.convertStudentDtoToStudent()).thenReturn(mockStudent);
        when(mockStudentDao.delete(mockStudent)).thenReturn(true);

        boolean deleteResult = studentService.delete(mockStudentDto);

        assertTrue(true, "The delete(studentDto) call should return true");

        verify(mockStudentDto, times(1)).convertStudentDtoToStudent();
        verify(mockStudentDao, times(1)).delete(mockStudent);
    }

    @Test
    public void testFindAllStudents() {
        List<Student> spyStudentList = spy(ArrayList.class);
        spyStudentList.add(mockStudent);
        spyStudentList.add(mockStudent);
        spyStudentList.add(mockStudent);

        when(mockStudent.convertStudentToStudentDto()).thenReturn(mockStudentDto);
        when(mockStudentDao.getStudents()).thenReturn(spyStudentList);

        List<StudentDto> studentDtoList = studentService.getStudents();

        assertEquals(3, studentDtoList.size(), "The studentDtoList.size() should return 3");

        verify(mockStudent, times(3)).convertStudentToStudentDto();
        verify(mockStudentDao, times(1)).getStudents();
    }

    @Test
    public void testFindStudentByStudentId() {
        when(mockStudentDao.getStudentById(anyLong())).thenReturn(mockStudent);
        when(mockStudent.convertStudentToStudentDto()).thenReturn(mockStudentDto);

        StudentDto studentDto = studentService.getStudentById(anyLong());

        verify(mockStudent, times(1)).convertStudentToStudentDto();
        verify(mockStudentDao, times(1)).getStudentById(anyLong());
    }

    @Test
    public void testFindStudentByStudentLoginName() {
        when(mockStudentDao.getStudentByLoginName(anyString())).thenReturn(mockStudent);
        when(mockStudent.convertStudentToStudentDto()).thenReturn(mockStudentDto);

        StudentDto studentDto = studentService.getStudentByLoginName(anyString());

        verify(mockStudent, times(1)).convertStudentToStudentDto();
        verify(mockStudentDao, times(1)).getStudentByLoginName(anyString());
    }

    @Disabled
    public void testGetStudentsWithAssociatedProjects() {

    }

    @Disabled
    public void testGetStudentWithAssociatedProjectsByStudentId() {

    }

    @Disabled
    public void testGetStudentWithAssociatedProjectsByLoginName() {

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
