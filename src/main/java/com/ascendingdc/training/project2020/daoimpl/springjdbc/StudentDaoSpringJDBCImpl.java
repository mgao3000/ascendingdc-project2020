package com.ascendingdc.training.project2020.daoimpl.springjdbc;

import com.ascendingdc.training.project2020.dao.jdbc.StudentDao;
import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
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

public class StudentDaoSpringJDBCImpl implements StudentDao {
    private Logger logger = LoggerFactory.getLogger(StudentProjectDaoSpringJDBCImpl.class);

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    public StudentDaoSpringJDBCImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName("STUDENT").usingGeneratedKeyColumns("id");
    }


    @Override
    public StudentDto save(StudentDto student, Long majorId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("login_name", student.getLoginName())
                .addValue("password", student.getPassword())
                .addValue("first_name", student.getFirstName())
                .addValue("last_name", student.getLastName())
                .addValue("email", student.getEmail())
                .addValue("address", student.getAddress())
                .addValue("enrolled_date", Timestamp.valueOf(student.getEnrolledDate().atStartOfDay()))
                .addValue("major_id", majorId);
        Number studentId = simpleJdbcInsert.executeAndReturnKey(mapSqlParameterSource);
        if(studentId != null)
            student.setId((Long)studentId);
        return student;
    }

    @Override
    public StudentDto update(StudentDto student) {
        int updateResult = jdbcTemplate.update(SQLStatementUtils.SQL_UPDATE_STUDENT,
                student.getLoginName(), student.getPassword(),
                student.getEmail(), student.getAddress(), student.getId());
        if(updateResult > 0)
            return student;
        else
            return null;
    }

    @Override
    public boolean deleteByLoginName(String loginName) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_STUDENT_BY_LOGIN_NAME,
                loginName) > 0;
    }

    @Override
    public boolean deleteById(Long studentId) {
        return jdbcTemplate.update(SQLStatementUtils.SQL_DELETE_STUDENT_BY_ID,
                studentId) > 0;
    }

    @Override
    public boolean delete(StudentDto student) {
        return deleteById(student.getId());
    }

    @Override
    public List<StudentDto> getStudents() {
        return jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_ALL_STUDENTS, new StudentMapper());
    }

    @Override
    public StudentDto getStudentById(Long id) {
        StudentDto student = null;
        try {
            student = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_STUDENT_BY_ID,
                    new Object[] {id}, new StudentMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing
        }
        return student;
    }

    @Override
    public StudentDto getStudentByLoginName(String loginName) {
        StudentDto student = null;
        try {
            student = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_STUDENT_BY_LOGIN_NAME,
                    new Object[] {loginName}, new StudentMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing
        }
        return student;
    }

    @Override
    public List<StudentDto> getStudentsByMajorId(Long majorId) {
        return jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_STUDENTS_BY_MAJOR_ID,
                new Object[] {majorId}, new StudentMapper());
    }

    @Override
    public List<ProjectDto> getAssociatedProjectsByStudentId(Long studentId) {
        return jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                new Object[] {studentId}, new ProjectMapper());
    }

    @Override
    public List<ProjectDto> getAssociatedProjectsByStudentLoginName(String loginName) {
        return jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_LOGIN_NAME,
                new Object[] {loginName}, new ProjectMapper());
    }

    @Override
    public List<StudentDto> getStudentsWithAssociatedProjects() {
        List<StudentDto> studentList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_ALL_STUDENTS, new StudentMapper());
        for(StudentDto student : studentList) {
            List<ProjectDto> projects = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                    new Object[] {student.getId()}, new ProjectMapper());
            student.setProjectDtoList(projects);
        }
        return studentList;
    }

    @Override
    public StudentDto getStudentWithAssociatedProjectsByStudentId(Long studentId) {
        StudentDto student = null;
        try {
            student = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_STUDENT_BY_ID,
                    new Object[] {studentId}, new StudentMapper());
            List<ProjectDto> projects = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                    new Object[] {student.getId()}, new ProjectMapper());
            student.setProjectDtoList(projects);
        } catch (EmptyResultDataAccessException e) {
            // do nothing
        }
        return student;
    }

    @Override
    public StudentDto getStudentWithAssociatedProjectsByLoginName(String loginName) {
        StudentDto student = null;
        try {
            student = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_STUDENT_BY_LOGIN_NAME,
                    new Object[] {loginName}, new StudentMapper());
            List<ProjectDto> projects = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                    new Object[] {student.getId()}, new ProjectMapper());
            student.setProjectDtoList(projects);
        } catch (EmptyResultDataAccessException e) {
            // do nothing
        }
        return student;
    }

    @Override
    public List<StudentDto> getStudentsWithAssociatedProjectsByMajorId(Long majorId) {
        List<StudentDto> studentList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_STUDENTS_BY_MAJOR_ID,
                new Object[] {majorId}, new StudentMapper());
        for(StudentDto student : studentList) {
            List<ProjectDto> projects = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                    new Object[] {student.getId()}, new ProjectMapper());
            student.setProjectDtoList(projects);
        }
        return studentList;
    }
}
