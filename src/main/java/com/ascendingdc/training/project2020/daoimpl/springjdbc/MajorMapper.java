package com.ascendingdc.training.project2020.daoimpl.springjdbc;

import com.ascendingdc.training.project2020.model.Major;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MajorMapper implements RowMapper<Major> {
    @Override
    public Major mapRow(ResultSet rs, int rowNum) throws SQLException {
        Major major = new Major();
        major.setId(rs.getLong("id"));
        major.setName(rs.getString("name"));
        major.setDescription(rs.getString("description"));
        return major;
    }
}
