package com.ascendingdc.training.project2020.daoimpl.springjdbc;

import com.ascendingdc.training.project2020.dto.MajorDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MajorMapper implements RowMapper<MajorDto> {
    @Override
    public MajorDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        MajorDto major = new MajorDto();
        major.setId(rs.getLong("id"));
        major.setName(rs.getString("name"));
        major.setDescription(rs.getString("description"));
        return major;
    }
}
