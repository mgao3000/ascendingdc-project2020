package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dao.hibernate.ProjectDao;
import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import com.ascendingdc.training.project2020.service.ProjectService;
import com.ascendingdc.training.project2020.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    @Qualifier("projectSpringDataJPADao")
    protected ProjectDao projectDao;

    @Override
    public ProjectDto save(ProjectDto projectDto) {
        Project project = projectDto.convertProjectDtoToProject();
        Project savedProject = projectDao.save(project);
        ProjectDto savedProjectDto = savedProject.convertProjectToProjectDto();
        return savedProjectDto;
    }

    @Override
    public ProjectDto update(ProjectDto projectDto) {
        Project project = projectDto.convertProjectDtoToProject();
        Project updatedProject = projectDao.update(project);
        ProjectDto updatedProjectDto = updatedProject.convertProjectToProjectDto();
        return updatedProjectDto;
    }

    @Override
    public boolean deleteByName(String projectName) {
        boolean deleteResult = projectDao.deleteByName(projectName);
        return deleteResult;
    }

    @Override
    public boolean deleteById(Long projectId) {
        boolean deleteResult = projectDao.deleteById(projectId);
        return deleteResult;
    }

    @Override
    public boolean delete(ProjectDto projectDto) {
        Project project = projectDto.convertProjectDtoToProject();
        boolean deleteResult = projectDao.delete(project);
        return deleteResult;
    }

    @Override
    public List<ProjectDto> getProjects() {
        List<Project> projectList = projectDao.getProjects();
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for(Project project : projectList) {
            ProjectDto projectDto = project.convertProjectToProjectDto();
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }

    @Override
    public ProjectDto getProjectById(Long id) {
        Project project = projectDao.getProjectById(id);
        ProjectDto projectDto = project.convertProjectToProjectDto();
        return projectDto;
    }

    @Override
    public ProjectDto getProjectByName(String projectName) {
        Project project = projectDao.getProjectByName(projectName);
        ProjectDto projectDto = project.convertProjectToProjectDto();
        return projectDto;
    }

    @Override
    public List<ProjectDto> getProjectsWithAssociatedStudents() {
        List<Project> projectList = projectDao.getProjectsWithAssociatedStudents();
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for(Project project : projectList) {
            ProjectDto projectDto = project.convertProjectToProjectDto();
            List<StudentDto> studentDtoList = getAssociatedStudentDtoList(project.getStudents());
            projectDto.setStudentDtoList(studentDtoList);
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }

    private List<StudentDto> getAssociatedStudentDtoList(List<Student> students) {
        List<StudentDto> studentDtoList = new ArrayList<>();
        for(Student student : students) {
            StudentDto studentDto = student.convertStudentToStudentDto();
            studentDtoList.add(studentDto);
        }
        return studentDtoList;
    }

    @Override
    public ProjectDto getProjectWithAssociatedStudentsById(Long projectId) {
        Project project = projectDao.getProjectWithAssociatedStudentsById(projectId);
        ProjectDto projectDto = project.convertProjectToProjectDto();
        List<StudentDto> studentDtoList = getAssociatedStudentDtoList(project.getStudents());
        projectDto.setStudentDtoList(studentDtoList);
        return projectDto;
    }

    @Override
    public ProjectDto getProjectWithAssociatedStudentsByName(String projectName) {
        Project project = projectDao.getProjectWithAssociatedStudentsByName(projectName);
        ProjectDto projectDto = project.convertProjectToProjectDto();
        List<StudentDto> studentDtoList = getAssociatedStudentDtoList(project.getStudents());
        projectDto.setStudentDtoList(studentDtoList);
        return projectDto;
    }
}
