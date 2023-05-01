package com.ascendingdc.training.project2020.daoimpl.springjdbc;

import com.ascendingdc.training.project2020.dao.jdbc.MajorDao;
import com.ascendingdc.training.project2020.dto.MajorDto;
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
    public MajorDto save(MajorDto major) {
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
    public MajorDto update(MajorDto major) {
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
    public boolean delete(MajorDto major) {
        return deleteById(major.getId());
    }

    @Override
    public List<MajorDto> getMajors() {
        return jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_ALL_MAJORS, new MajorMapper());
    }

    @Override
    public MajorDto getMajorById(Long id) {
        MajorDto retrievedMajor = null;
        try {
            retrievedMajor = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_MAJOR_BY_ID,
                    new Object[] {id}, new MajorMapper());

        } catch (EmptyResultDataAccessException e) {
            //do nothing
        }
        return retrievedMajor;
    }

    @Override
    public MajorDto getMajorByName(String majorName) {
        MajorDto retrievedMajor = null;
        try {
            retrievedMajor = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_MAJOR_BY_MAME,
                    new Object[] {majorName}, new MajorMapper());

        } catch (EmptyResultDataAccessException e) {
            //do nothing
        }
        return retrievedMajor;
    }

    @Override
    public List<MajorDto> getMajorsWithChildren() {
        List<MajorDto> allMajors = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_ALL_MAJORS, new MajorMapper());
        for(MajorDto major : allMajors) {
            List<StudentDto> studentList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_STUDENTS_BY_MAJOR_ID,
                    new StudentMapper(), new Object[] {major.getId()});
            for(StudentDto student : studentList) {
                List<ProjectDto> projectList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                        new ProjectMapper(), new Object[] {student.getId()});
                student.setProjectDtoList(projectList);
            }
            major.setStudentList(studentList);
        }
        return allMajors;
    }

    @Override
    public MajorDto getMajorAndStudentsAndProjectsByMajorId(Long majorId) {
        MajorDto retrievedMajor = null;
        try {
            retrievedMajor = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_MAJOR_BY_ID,
                    new Object[] {majorId}, new MajorMapper());
            List<StudentDto> studentList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_STUDENTS_BY_MAJOR_ID,
                    new StudentMapper(), new Object[] {retrievedMajor.getId()});
            for(StudentDto student : studentList) {
                List<ProjectDto> projectList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                        new ProjectMapper(), new Object[] {student.getId()});
                student.setProjectDtoList(projectList);
            }
            retrievedMajor.setStudentList(studentList);
        } catch (EmptyResultDataAccessException e) {
            //do nothing
        }
        return retrievedMajor;
    }

    @Override
    public MajorDto getMajorAndStudentsAndProjectsByMajorName(String majorName) {
        MajorDto retrievedMajor = null;
        try {
            retrievedMajor = jdbcTemplate.queryForObject(SQLStatementUtils.SQL_SELECT_MAJOR_BY_MAME,
                    new Object[] {majorName}, new MajorMapper());
            List<StudentDto> studentList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_STUDENTS_BY_MAJOR_ID,
                    new StudentMapper(), new Object[] {retrievedMajor.getId()});
            for(StudentDto student : studentList) {
                List<ProjectDto> projectList = jdbcTemplate.query(SQLStatementUtils.SQL_SELECT_PROJECTS_BY_STUDENT_ID,
                        new ProjectMapper(), new Object[] {student.getId()});
                student.setProjectDtoList(projectList);
            }
            retrievedMajor.setStudentList(studentList);

        } catch (EmptyResultDataAccessException e) {
            //do nothing
        }
        return retrievedMajor;
    }
}
