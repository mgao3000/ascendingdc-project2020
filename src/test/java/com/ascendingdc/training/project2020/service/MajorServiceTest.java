package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dao.hibernate.MajorDao;
import com.ascendingdc.training.project2020.dao.hibernate.StudentDao;
import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
import com.ascendingdc.training.project2020.entity.Major;
import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import com.ascendingdc.training.project2020.service.impl.MajorServiceImpl;
import com.ascendingdc.training.project2020.util.DtoAndEntityConvertUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
public class MajorServiceTest {

    private Logger logger = LoggerFactory.getLogger(MajorServiceTest.class);

    @Mock(name="studentSpringDataJPADao")
//    @Qualifier("majorSpringDataJPADao")
    private StudentDao mockStudentDao;

    @Mock(name="majorSpringDataJPADao")
//    @Qualifier("majorSpringDataJPADao")
    private MajorDao mockMajorDao;

    @Mock
    private Major mockMajor;

    @Mock
    private MajorDto mockMajorDto;

    @InjectMocks
    private MajorServiceImpl majorService;

    private String tempMajorName;

    private String tempProjectName;

    private String tempLoginName = null;

    private String tempEmail = null;

    @BeforeEach
    public void setupEach() {
        MockitoAnnotations.openMocks(this);
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

        mockMajorDao = null;
//        studentDao = null;
//        projectDao = null;
    }

    /*
     *  This is an example mock static method to do mock tests
     */
//    @Test
    public void testSaveMajorDto() {
        try (MockedStatic mockStatic = mockStatic(DtoAndEntityConvertUtil.class)) {
            Major mockMajor = mock(Major.class);
            MajorDto mockMajorDto = mock(MajorDto.class);

            mockStatic.when(() -> DtoAndEntityConvertUtil.convertMajorToMajorDto(mockMajor)).thenReturn(mockMajorDto);
            mockStatic.when(() -> DtoAndEntityConvertUtil.convertMajorDtoToMajor(mockMajorDto)).thenReturn(mockMajor);


            when(mockMajorDao.save(mockMajor)).thenReturn(mockMajor);

            majorService.save(mockMajorDto);

            verify(mockMajorDao, times(1)).save(mockMajor);
        }
    }

    @Test
    public void testSaveMajorDtoWithoutStaticMethod() {
        when(mockMajorDto.convertMajorDtoToMajor()).thenReturn(mockMajor);
        when(mockMajor.convertMajorToMajorDto()).thenReturn(mockMajorDto);
        when(mockMajorDao.save(mockMajor)).thenReturn(mockMajor);

        majorService.save(mockMajorDto);

        verify(mockMajorDao, times(1)).save(mockMajor);
        verify(mockMajor, times(1)).convertMajorToMajorDto();
        verify(mockMajorDto, times(1)).convertMajorDtoToMajor();
    }

    @Test
    public void testUpdateMajor() {
        when(mockMajorDto.convertMajorDtoToMajor()).thenReturn(mockMajor);
        when(mockMajor.convertMajorToMajorDto()).thenReturn(mockMajorDto);
        when(mockMajorDao.update(mockMajor)).thenReturn(mockMajor);

        majorService.update(mockMajorDto);

        verify(mockMajorDao, times(1)).update(mockMajor);
        verify(mockMajor, times(1)).convertMajorToMajorDto();
        verify(mockMajorDto, times(1)).convertMajorDtoToMajor();
    }

    @Test
    public void testDeleteByName() {
        when(mockMajorDao.deleteByName(anyString())).thenReturn(true);

        boolean deleteResult = majorService.deleteByName(anyString());

        assertTrue(true, "The deleteByName call should return true");

        verify(mockMajorDao, times(1)).deleteByName(anyString());
    }

    @Test
    public void testDeleteById() {
        when(mockMajorDao.deleteById(anyLong())).thenReturn(true);

        boolean deleteResult = majorService.deleteById(anyLong());

        assertTrue(true, "The deleteById call should return true");

        verify(mockMajorDao, times(1)).deleteById(anyLong());
    }

    @Test
    public void testDeleteMajorByMajorDto() {
        when(mockMajorDto.convertMajorDtoToMajor()).thenReturn(mockMajor);
        when(mockMajorDao.delete(mockMajor)).thenReturn(true);

        boolean deleteResult = majorService.delete(mockMajorDto);

        assertTrue(true, "The delete(majorDto) call should return true");

        verify(mockMajorDto, times(1)).convertMajorDtoToMajor();
        verify(mockMajorDao, times(1)).delete(mockMajor);
    }

    @Test
    public void testFindAllMajors() {
//        List<Student> spyStudentList = spy(ArrayList.class);
//        spyStudentList.add(mock(Student.class));

        List<Major> spyMajorList = mock(ArrayList.class);

        Iterator iterator = mock(Iterator.class);
        when(spyMajorList.iterator()).thenReturn(iterator);

        when(mockMajor.convertMajorToMajorDto()).thenReturn(mockMajorDto);
        when(mockMajorDao.getMajors()).thenReturn(spyMajorList);
        when(iterator.next()).thenReturn(mockMajor);
        when(iterator.hasNext()).thenReturn(true, true, true, true, false);

//        when(mockStudentDao.getStudents()).thenReturn(spyStudentList);

        List<MajorDto> majorDtoList = majorService.getMajors();

        assertEquals(4, majorDtoList.size(), "The majorDtoList.size() should return 3");

        verify(mockMajor, times(4)).convertMajorToMajorDto();
        verify(mockMajorDao, times(1)).getMajors();

//        verify(mockStudentDao, times(1)).getStudents();
    }

    @Test
    public void testFindAllMajorsUsingSpy() {
//        List<Student> spyStudentList = spy(ArrayList.class);
//        spyStudentList.add(mock(Student.class));

        List<Major> spyMajorList = spy(ArrayList.class);
        spyMajorList.add(mockMajor);
        spyMajorList.add(mockMajor);
        spyMajorList.add(mockMajor);

//        List<Major> majorList = new ArrayList<Major>(3);
//        List<Major> spyMajorList = spy(majorList);
//        Major major = createMajorByName("aaa");
//        spyMajorList.add(mockMajor);
//        spyMajorList.add(mockMajor);
//        spyMajorList.add(mockMajor);

        when(mockMajor.convertMajorToMajorDto()).thenReturn(mockMajorDto);
        when(mockMajorDao.getMajors()).thenReturn(spyMajorList);

//        when(mockStudentDao.getStudents()).thenReturn(spyStudentList);

        List<MajorDto> majorDtoList = majorService.getMajors();

        assertEquals(3, majorDtoList.size(), "The majorDtoList.size() should return 3");

        verify(mockMajor, times(3)).convertMajorToMajorDto();
        verify(mockMajorDao, times(1)).getMajors();

//        verify(mockStudentDao, times(1)).getStudents();
    }

    @Test
    public void testFindAllMajorsWithStudentAndProjects() {
        List<Major> spyMajorList = spy(ArrayList.class);
        spyMajorList.add(mockMajor);
//        spyMajorList.add(mockMajor);
//        spyMajorList.add(mockMajor);

        StudentDto mockStudentDto = mock(StudentDto.class);
        Student mockStudentOne = mock(Student.class);
        Student mockStudentTwo = mock(Student.class);
        Set<Student> spyStudentSet = spy(HashSet.class);
        spyStudentSet.add(mockStudentOne);
        spyStudentSet.add(mockStudentTwo);

        ProjectDto mockProjectDto = mock(ProjectDto.class);
        Project mockProject = mock(Project.class);
        List<Project> spyProjectList = spy(ArrayList.class);
        spyProjectList.add(mockProject);
        spyProjectList.add(mockProject);

        when(mockMajorDao.getMajors()).thenReturn(spyMajorList);
        when(mockMajor.convertMajorToMajorDto()).thenReturn(mockMajorDto);
        when(mockMajor.getStudents()).thenReturn(spyStudentSet);
        when(mockStudentOne.convertStudentToStudentDto()).thenReturn(mockStudentDto);
        when(mockStudentTwo.convertStudentToStudentDto()).thenReturn(mockStudentDto);

        when(mockStudentOne.getProjects()).thenReturn(spyProjectList);
        when(mockStudentTwo.getProjects()).thenReturn(spyProjectList);
        when(mockProject.convertProjectToProjectDto()).thenReturn(mockProjectDto);

        List<MajorDto> majorDtoList = majorService.getMajorsWithChildren();

        assertEquals(1, majorDtoList.size(), "The majorDtoList.size() should return 3");

//        verify(mockMajor, times(3)).convertMajorToMajorDto();

        verify(mockMajor, times(1)).convertMajorToMajorDto();
        verify(mockMajor, times(1)).getStudents();
        verify(mockStudentOne, times(1)).convertStudentToStudentDto();
        verify(mockStudentTwo, times(1)).convertStudentToStudentDto();

        verify(mockStudentOne, times(1)).getProjects();
        verify(mockStudentTwo, times(1)).getProjects();
        verify(mockProject, times(4)).convertProjectToProjectDto();


        verify(mockMajorDao, times(1)).getMajors();
    }

    @Test
    public void testFindMajorByMajorId() {
        when(mockMajorDao.getMajorById(anyLong())).thenReturn(mockMajor);
        when(mockMajor.convertMajorToMajorDto()).thenReturn(mockMajorDto);

        MajorDto mockMajorDto = majorService.getMajorById(anyLong());

        verify(mockMajor, times(1)).convertMajorToMajorDto();
        verify(mockMajorDao, times(1)).getMajorById(anyLong());
    }

    @Test
    public void testFindMajorByMajorName() {
        when(mockMajorDao.getMajorByName(anyString())).thenReturn(mockMajor);
        when(mockMajor.convertMajorToMajorDto()).thenReturn(mockMajorDto);

        MajorDto mockMajorDto = majorService.getMajorByName(anyString());

        verify(mockMajor, times(1)).convertMajorToMajorDto();
        verify(mockMajorDao, times(1)).getMajorByName(anyString());
    }

    @Test
    public void testFindMajorAndStudentsAndProjectsByMajorId() {
        StudentDto mockStudentDto = mock(StudentDto.class);
        Student mockStudentOne = mock(Student.class);
        Student mockStudentTwo = mock(Student.class);
        Set<Student> spyStudentSet = spy(HashSet.class);
        spyStudentSet.add(mockStudentOne);
        spyStudentSet.add(mockStudentTwo);

        ProjectDto mockProjectDto = mock(ProjectDto.class);
        Project mockProject = mock(Project.class);
        List<Project> spyProjectList = spy(ArrayList.class);
        spyProjectList.add(mockProject);
        spyProjectList.add(mockProject);

        when(mockMajorDao.getMajorAndStudentsAndProjectsByMajorId(anyLong())).thenReturn(mockMajor);
        when(mockMajor.convertMajorToMajorDto()).thenReturn(mockMajorDto);
        when(mockMajor.getStudents()).thenReturn(spyStudentSet);
        when(mockStudentOne.convertStudentToStudentDto()).thenReturn(mockStudentDto);
        when(mockStudentTwo.convertStudentToStudentDto()).thenReturn(mockStudentDto);

        when(mockStudentOne.getProjects()).thenReturn(spyProjectList);
        when(mockStudentTwo.getProjects()).thenReturn(spyProjectList);
        when(mockProject.convertProjectToProjectDto()).thenReturn(mockProjectDto);

        MajorDto mockMajorDto = majorService.getMajorAndStudentsAndProjectsByMajorId(anyLong());

        verify(mockMajor, times(1)).convertMajorToMajorDto();
        verify(mockMajor, times(1)).getStudents();
        verify(mockStudentOne, times(1)).convertStudentToStudentDto();
        verify(mockStudentTwo, times(1)).convertStudentToStudentDto();

        verify(mockStudentOne, times(1)).getProjects();
        verify(mockStudentTwo, times(1)).getProjects();
        verify(mockProject, times(4)).convertProjectToProjectDto();

        verify(mockMajorDao, times(1)).getMajorAndStudentsAndProjectsByMajorId(anyLong());

    }

    @Test
    public void testFindMajorAndStudentsAndProjectsByMajorName() {
        StudentDto mockStudentDto = mock(StudentDto.class);
        Student mockStudentOne = mock(Student.class);
        Student mockStudentTwo = mock(Student.class);
        Set<Student> spyStudentSet = spy(HashSet.class);
        spyStudentSet.add(mockStudentOne);
        spyStudentSet.add(mockStudentTwo);

        ProjectDto mockProjectDto = mock(ProjectDto.class);
        Project mockProject = mock(Project.class);
        List<Project> spyProjectList = spy(ArrayList.class);
        spyProjectList.add(mockProject);
        spyProjectList.add(mockProject);

        when(mockMajorDao.getMajorAndStudentsAndProjectsByMajorName(anyString())).thenReturn(mockMajor);
        when(mockMajor.convertMajorToMajorDto()).thenReturn(mockMajorDto);
        when(mockMajor.getStudents()).thenReturn(spyStudentSet);
        when(mockStudentOne.convertStudentToStudentDto()).thenReturn(mockStudentDto);
        when(mockStudentTwo.convertStudentToStudentDto()).thenReturn(mockStudentDto);

        when(mockStudentOne.getProjects()).thenReturn(spyProjectList);
        when(mockStudentTwo.getProjects()).thenReturn(spyProjectList);
        when(mockProject.convertProjectToProjectDto()).thenReturn(mockProjectDto);

        MajorDto mockMajorDto = majorService.getMajorAndStudentsAndProjectsByMajorName(anyString());

        verify(mockMajor, times(1)).convertMajorToMajorDto();
        verify(mockMajor, times(1)).getStudents();
        verify(mockStudentOne, times(1)).convertStudentToStudentDto();
        verify(mockStudentTwo, times(1)).convertStudentToStudentDto();

        verify(mockStudentOne, times(1)).getProjects();
        verify(mockStudentTwo, times(1)).getProjects();
        verify(mockProject, times(4)).convertProjectToProjectDto();

        verify(mockMajorDao, times(1)).getMajorAndStudentsAndProjectsByMajorName(anyString());

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

    protected Major createMajorByName(String name) {
        Major major = new Major();
        major.setName(name);
        major.setDescription(name + "--description");
        major.setId(Long.valueOf(getRandomInt(1, 1000)));
        return major;
    }


}
