package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dao.hibernate.StudentDao;
import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import com.ascendingdc.training.project2020.service.StudentService;
import com.ascendingdc.training.project2020.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    @Qualifier("studentSpringDataJPADao")
    protected StudentDao studentDao;

    @Override
    public StudentDto save(StudentDto studentDto) {
        Student student = studentDto.convertStudentDtoToStudent();
        Student savedStudent = studentDao.save(student);
        StudentDto savedStudentDto = savedStudent.convertStudentToStudentDto();
        return savedStudentDto;
    }

    @Override
    public StudentDto update(StudentDto studentDto) {
        Student student = studentDto.convertStudentDtoToStudent();
        Student updatedStudent = studentDao.update(student);
        StudentDto updatedStudentDto = updatedStudent.convertStudentToStudentDto();
        return updatedStudentDto;
    }

    @Override
    public boolean deleteByLoginName(String loginName) {
        boolean deleteResult = studentDao.deleteByLoginName(loginName);
        return deleteResult;
    }

    @Override
    public boolean deleteById(Long studentId) {
        boolean deleteResult = studentDao.deleteById(studentId);
        return deleteResult;
    }

    @Override
    public boolean delete(StudentDto studentDto) {
        Student student = studentDto.convertStudentDtoToStudent();
        boolean deleteResult = studentDao.delete(student);
        return deleteResult;
    }

    @Override
    public List<StudentDto> getStudents() {
        List<Student> studentList = studentDao.getStudents();
        List<StudentDto> studentDtoList = new ArrayList<>();
        for(Student student : studentList) {
            StudentDto studentDto = student.convertStudentToStudentDto();
            studentDtoList.add(studentDto);
        }
        return studentDtoList;
    }

    public List<ProjectDto> getAssociatedProjectsByStudentLoginName(String loginName)  {
        List<Project> projectList = studentDao.getAssociatedProjectsByStudentLoginName(loginName);
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for(Project project : projectList) {
            ProjectDto projectDto = project.convertProjectToProjectDto();
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentDao.getStudentById(id);
        StudentDto studentDto = student.convertStudentToStudentDto();
        return studentDto;
    }

    @Override
    public StudentDto getStudentByLoginName(String loginName) {
        Student student = studentDao.getStudentByLoginName(loginName);
        StudentDto studentDto = student.convertStudentToStudentDto();
        return studentDto;
    }

    @Override
    public List<StudentDto> getStudentsWithAssociatedProjects() {
        List<Student> studentList = studentDao.getStudentsWithAssociatedProjects();
        List<StudentDto> studentDtoList = new ArrayList<>();
        for(Student student : studentList) {
            StudentDto studentDto = student.convertStudentToStudentDto();
            List<ProjectDto> projectDtoList = getAssociatedProjectDtoList(student.getProjects());
            studentDto.setProjectDtoList(projectDtoList);
            studentDtoList.add(studentDto);
        }
        return studentDtoList;
    }

    private List<ProjectDto> getAssociatedProjectDtoList(List<Project> projects) {
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for(Project project : projects) {
            ProjectDto projectDto = project.convertProjectToProjectDto();
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }

    @Override
    public StudentDto getStudentWithAssociatedProjectsByStudentId(Long studentId) {
        Student student = studentDao.getStudentWithAssociatedProjectsByStudentId(studentId);
        StudentDto studentDto = student.convertStudentToStudentDto();
        List<ProjectDto> projectDtoList = getAssociatedProjectDtoList(student.getProjects());
        studentDto.setProjectDtoList(projectDtoList);
        return studentDto;
    }

    @Override
    public StudentDto getStudentWithAssociatedProjectsByLoginName(String loginName) {
        Student student = studentDao.getStudentWithAssociatedProjectsByLoginName(loginName);
        StudentDto studentDto = student.convertStudentToStudentDto();
        List<ProjectDto> projectDtoList = getAssociatedProjectDtoList(student.getProjects());
        studentDto.setProjectDtoList(projectDtoList);
        return studentDto;
    }
}
