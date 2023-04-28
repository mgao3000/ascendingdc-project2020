package com.ascendingdc.training.project2020.daoimpl.springjdbc;

import com.ascendingdc.training.project2020.dao.jdbc.StudentProjectDao;
import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
import com.ascendingdc.training.project2020.util.SQLStatementUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class StudentProjectDaoSpringJDBCImpl implements StudentProjectDao {
    private Logger logger = LoggerFactory.getLogger(StudentProjectDaoSpringJDBCImpl.class);

    private JdbcTemplate jdbcTemplate;

    public StudentProjectDaoSpringJDBCImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean deleteStudentProjectRelationshipByStudentId(long studentId) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_ALL_STUDENT_AND_PROJECT_IDS_BY_STUDENT_ID,
                studentId) > 0;
    }

    @Override
    public boolean deleteStudentProjectRelationshipByProjectId(long projectId) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_ALL_STUDENT_AND_PROJECT_IDS_BY_PROJECT_ID,
                projectId) > 0;
    }

    @Override
    public boolean deleteStudentProjectRelationship(StudentDto student, ProjectDto project) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_STUDENT_PROJECT_RELATIONSHIP_BY_BOTH_IDS,
                student.getId(), project.getId()) > 0;
    }

    @Override
    public boolean deleteStudentProjectRelationship(long studentId, long projectId) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_STUDENT_PROJECT_RELATIONSHIP_BY_BOTH_IDS,
                studentId, projectId) > 0;
    }

    @Override
    public boolean addStudentProjectRelationship(StudentDto student, ProjectDto project) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_INSERT_STUDENT_PROJECT_RELATIONSHIP_BY_BOTH_IDS,
                student.getId(), project.getId()) > 0;
   }

    @Override
    public boolean addStudentProjectRelationship(long studentId, long projectId) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_INSERT_STUDENT_PROJECT_RELATIONSHIP_BY_BOTH_IDS,
                studentId, projectId) > 0;
    }

    @Override
    public List<Long> findStudentIdListByProjectId(long projectId) {
        return null;
    }

    @Override
    public List<Long> findProjectIdListByStudentId(long studentId) {
        return null;
    }
}
