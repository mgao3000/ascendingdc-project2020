package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.ProjectDao;
import com.ascendingdc.training.project2020.daoimpl.repository.ProjectRepository;
import com.ascendingdc.training.project2020.entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository("projectSpringDataJPADao")
public class ProjectDaoSpringDataJPAImpl implements ProjectDao {

    private Logger logger = LoggerFactory.getLogger(ProjectDaoSpringDataJPAImpl.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project save(Project project) {
        Project savedProject = projectRepository.save(project);
        return savedProject;
    }

    @Override
    public Project update(Project project) {
        Project updatedProject = projectRepository.save(project);
        return updatedProject;
    }

    @Override
    public boolean deleteByName(String projectName) {
        return projectRepository.deleteByName(projectName) > 0;
    }

    @Override
    public boolean deleteById(Long projectId) {
        boolean successFlag = false;
        try {
            projectRepository.deleteById(projectId);
            successFlag = true;
        } catch (IllegalArgumentException iae) {
            //do nothing
        } catch (OptimisticLockingFailureException olfe) {
            //do nothing
        }
        return successFlag;
    }

    @Override
    public boolean delete(Project project) {
        boolean successFlag = false;
        try {
            projectRepository.delete(project);
            successFlag = true;
        } catch (IllegalArgumentException iae) {
            //do nothing
        } catch (OptimisticLockingFailureException olfe) {
            //do nothing
        }
        return successFlag;
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long id) {
        Project project = null;
        Optional<Project> projectOptional = projectRepository.findById(id);
        if(projectOptional.isPresent())
            project = projectOptional.get();
        return project;
    }

    @Override
    public Project getProjectByName(String projectName) {
        return projectRepository.findByName(projectName);
    }

    @Override
    public List<Project> getProjectsWithAssociatedStudents() {
        return projectRepository.findAllProjectsWithAssociatedStudents();
    }

    @Override
    public Project getProjectWithAssociatedStudentsById(Long projectId) {
        return projectRepository.findProjectWithAssociatedStudentsByProjectId(projectId);
    }

    @Override
    public Project getProjectWithAssociatedStudentsByName(String projectName) {
        return projectRepository.findProjectWithAssociatedStudentsByProjectName(projectName);
    }
}
