package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.dto.ProjectDto;

import java.util.List;

public interface ProjectDao {
    ProjectDto save(ProjectDto project);
    ProjectDto update(ProjectDto project);
    boolean deleteByName(String projectName);
    boolean deleteById(Long projectId);
    boolean delete(ProjectDto project);
    List<ProjectDto> getProjects();
    ProjectDto getProjectById(Long id);
    ProjectDto getProjectByName(String projectName);
    List<ProjectDto> getProjectsWithAssociatedStudents();
    ProjectDto getProjectWithAssociatedStudentsById(Long projectId);
    ProjectDto getProjectWithAssociatedStudentsByName(String projectName);
}
