package com.ascendingdc.training.project2020.daoimpl.springjdbc;

import com.ascendingdc.training.project2020.dto.StudentDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<StudentDto> {
    @Override
    public StudentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        StudentDto student = new StudentDto();
        student.setId(rs.getLong("id"));
        student.setLoginName(rs.getString("login_name"));
        student.setPassword(rs.getString("password"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        student.setAddress(rs.getString("address"));
        student.setEnrolledDate(rs.getTimestamp("enrolled_date").toLocalDateTime().toLocalDate());
        return student;
    }
}
