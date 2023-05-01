package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    ProjectDto save(ProjectDto projectDto);
    ProjectDto update(ProjectDto projectDto);
    boolean deleteByName(String projectName);
    boolean deleteById(Long projectId);
    boolean delete(ProjectDto projectDto);
    List<ProjectDto> getProjects();
    ProjectDto getProjectById(Long id);
    ProjectDto getProjectByName(String projectName);
    List<ProjectDto> getProjectsWithAssociatedStudents();
    ProjectDto getProjectWithAssociatedStudentsById(Long projectId);
    ProjectDto getProjectWithAssociatedStudentsByName(String projectName);

}
