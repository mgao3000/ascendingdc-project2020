package com.ascendingdc.training.project2020.daoimpl.springjdbc;

import com.ascendingdc.training.project2020.dao.jdbc.ProjectDao;
import com.ascendingdc.training.project2020.model.Project;
import com.ascendingdc.training.project2020.model.Student;
import com.ascendingdc.training.project2020.util.SQLStatementUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

public class ProjectDaoSpringJDBCImpl implements ProjectDao {
    private Logger logger = LoggerFactory.getLogger(ProjectDaoSpringJDBCImpl.class);

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    public ProjectDaoSpringJDBCImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("PROJECT").usingGeneratedKeyColumns("id");
    }

    @Override
    public Project save(Project project) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", project.getName())
                .addValue("description", project.getDescription())
                .addValue("create_date", Timestamp.valueOf(project.getCreateDate().atStartOfDay()));
//        Number projectId = simpleJdbcInsert.withTableName("PROJECT").usingGeneratedKeyColumns("id").executeAndReturnKey(mapSqlParameterSource);
        Number projectId = simpleJdbcInsert.executeAndReturnKey(mapSqlParameterSource);
        if(projectId != null)
            project.setId((Long)projectId);
        return project;
    }

    @Override
    public Project update(Project project) {
        int updateResult = jdbcTemplate.update(SQLStatementUtils.SQL_UPDATE_PROJECT,
                project.getName(), project.getDescription(),
                project.getCreateDate(), project.getId());
        if(updateResult > 0)
            return project;
        else
            return null;
    }

    @Override
    public boolean deleteByName(String projectName) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_PROJECT_BY_NAME,
                projectName) > 0;
    }

    @Override
    public boolean deleteById(Long projectId) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_PROJECT_BY_ID,
                projectId) > 0;
    }

    @Override
    public boolean delete(Project project) {
        return deleteById(project.getId());
    }

    @Override
    public List<Project> getProjects() {
        return jdbcTemplate.query(SQLStatementUtils.SELECT_ALL_PROJECT, new ProjectMapper());
    }

    @Override
    public Project getProjectById(Long id) {
        Project project = null;
        try {
            project = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_PROJECT_BY_ID,
                new Object[] {id}, new ProjectMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing
        }
        return project;
   }

    @Override
    public Project getProjectByName(String projectName) {
        Project project = null;
        try {
            project = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_PROJECT_BY_NAME,
                    new Object[] {projectName}, new ProjectMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing
        }
        return project;
    }

    @Override
    public List<Project> getProjectsWithAssociatedStudents() {
       List<Project> allProjectList = jdbcTemplate.query(SQLStatementUtils.SELECT_ALL_PROJECT, new ProjectMapper());
       for(Project project : allProjectList) {
           List<Student> studentList = jdbcTemplate.query(SQLStatementUtils.SELECT_STUDENTS_BY_PROJECT_ID,
                   new StudentMapper(), new Object[] {project.getId()});
            project.setStudentList(studentList);
       }
       return allProjectList;
    }

    @Override
    public Project getProjectWithAssociatedStudentsById(Long projectId) {
        Project project = null;
        try {
            project = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_PROJECT_BY_ID,
                    new Object[] {projectId}, new ProjectMapper());
            List<Student> studentList = jdbcTemplate.query(SQLStatementUtils.SELECT_STUDENTS_BY_PROJECT_ID,
                    new StudentMapper(), new Object[] {project.getId()});
            project.setStudentList(studentList);
        } catch (EmptyResultDataAccessException e) {
            // do nothing
        }
        return project;
    }

    @Override
    public Project getProjectWithAssociatedStudentsByName(String projectName) {
        Project project = null;
        try {
            project = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_PROJECT_BY_NAME,
                    new Object[] {projectName}, new ProjectMapper());
            List<Student> studentList = jdbcTemplate.query(SQLStatementUtils.SELECT_STUDENTS_BY_PROJECT_ID,
                    new StudentMapper(), new Object[] {project.getId()});
            project.setStudentList(studentList);
        } catch (EmptyResultDataAccessException e) {
            // do nothing
        }
        return project;
    }
}
