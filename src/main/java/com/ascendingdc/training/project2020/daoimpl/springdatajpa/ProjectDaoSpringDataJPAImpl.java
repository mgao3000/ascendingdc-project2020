package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.ProjectDao;
import com.ascendingdc.training.project2020.daoimpl.repository.ProjectRepository;
import com.ascendingdc.training.project2020.entity.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository("projectSpringDataJPADao")
public class ProjectDaoSpringDataJPAImpl implements ProjectDao {

    private Logger logger = LoggerFactory.getLogger(ProjectDaoSpringDataJPAImpl.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project save(Project project) {
        return null;
    }

    @Override
    public Project update(Project project) {
        return null;
    }

    @Override
    public boolean deleteByName(String projectName) {
        return false;
    }

    @Override
    public boolean deleteById(Long projectId) {
        return false;
    }

    @Override
    public boolean delete(Project project) {
        return false;
    }

    @Override
    public List<Project> getProjects() {
        return null;
    }

    @Override
    public Project getProjectById(Long id) {
        return null;
    }

    @Override
    public Project getProjectByName(String projectName) {
        return null;
    }

    @Override
    public List<Project> getProjectsWithAssociatedStudents() {
        return null;
    }

    @Override
    public Project getProjectWithAssociatedStudentsById(Long projectId) {
        return null;
    }

    @Override
    public Project getProjectWithAssociatedStudentsByName(String projectName) {
        return null;
    }
}
