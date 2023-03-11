package com.ascendingdc.training.project2020.daoimpl.springjdbc;

import com.ascendingdc.training.project2020.dao.jdbc.MajorDao;
import com.ascendingdc.training.project2020.model.Major;
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
import java.util.List;

public class MajorDaoSpringJDBCImpl implements MajorDao {
    private Logger logger = LoggerFactory.getLogger(MajorDaoSpringJDBCImpl.class);

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    public MajorDaoSpringJDBCImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("MAJOR").usingGeneratedKeyColumns("id");
    }

    @Override
    public Major save(Major major) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", major.getName())
                        .addValue("description", major.getDescription());
        Number majorId = simpleJdbcInsert.executeAndReturnKey(mapSqlParameterSource);
//        jdbcTemplate.update(SQLStatementUtils.SQL_INSERT_MAJOR, major.getName(), major.getDescription());
        if(majorId != null)
            major.setId((Long)majorId);
        return major;
    }

    @Override
    public Major update(Major major) {
        int updateResult = jdbcTemplate.update(SQLStatementUtils.SQL_UPDATE_MAJOR,
                major.getName(), major.getDescription(), major.getId());
        if(updateResult > 0)
            return major;
        else
            return null;
    }

    @Override
    public boolean deleteByName(String majorName) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_MAJOR_BY_NAME,
                majorName) > 0;
    }

    @Override
    public boolean deleteById(Long majorId) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_MAJOR_BY_ID,
                majorId) > 0;
    }

    @Override
    public boolean delete(Major major) {
        return deleteById(major.getId());
    }

    @Override
    public List<Major> getMajors() {
        return jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_ALL_MAJORS, new MajorMapper());
    }

    @Override
    public Major getMajorById(Long id) {
        Major retrievedMajor = null;
        try {
            retrievedMajor = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_MAJOR_BY_ID,
                    new Object[] {id}, new MajorMapper());

        } catch (EmptyResultDataAccessException e) {
            //do nothing
        }
        return retrievedMajor;
    }

    @Override
    public Major getMajorByName(String majorName) {
        Major retrievedMajor = null;
        try {
            retrievedMajor = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_MAJOR_BY_MAME,
                    new Object[] {majorName}, new MajorMapper());

        } catch (EmptyResultDataAccessException e) {
            //do nothing
        }
        return retrievedMajor;
    }

    @Override
    public List<Major> getMajorsWithChildren() {
        List<Major> allMajors = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_ALL_MAJORS, new MajorMapper());
        for(Major major : allMajors) {
            List<Student> studentList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_STUDENTS_BY_MAJOR_ID,
                    new StudentMapper(), new Object[] {major.getId()});
            for(Student student : studentList) {
                List<Project> projectList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                        new ProjectMapper(), new Object[] {student.getId()});
                student.setProjectList(projectList);
            }
            major.setStudentList(studentList);
        }
        return allMajors;
    }

    @Override
    public Major getMajorAndStudentsAndProjectsByMajorId(Long majorId) {
        Major retrievedMajor = null;
        try {
            retrievedMajor = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_MAJOR_BY_ID,
                    new Object[] {majorId}, new MajorMapper());
            List<Student> studentList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_STUDENTS_BY_MAJOR_ID,
                    new StudentMapper(), new Object[] {retrievedMajor.getId()});
            for(Student student : studentList) {
                List<Project> projectList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                        new ProjectMapper(), new Object[] {student.getId()});
                student.setProjectList(projectList);
            }
            retrievedMajor.setStudentList(studentList);
        } catch (EmptyResultDataAccessException e) {
            //do nothing
        }
        return retrievedMajor;
    }

    @Override
    public Major getMajorAndStudentsAndProjectsByMajorName(String majorName) {
        Major retrievedMajor = null;
        try {
            retrievedMajor = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_MAJOR_BY_MAME,
                    new Object[] {majorName}, new MajorMapper());
            List<Student> studentList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_STUDENTS_BY_MAJOR_ID,
                    new StudentMapper(), new Object[] {retrievedMajor.getId()});
            for(Student student : studentList) {
                List<Project> projectList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                        new ProjectMapper(), new Object[] {student.getId()});
                student.setProjectList(projectList);
            }
            retrievedMajor.setStudentList(studentList);

        } catch (EmptyResultDataAccessException e) {
            //do nothing
        }
        return retrievedMajor;
    }
}
