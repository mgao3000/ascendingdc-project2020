package com.ascendingdc.training.project2020.daoimpl.springjdbc;

import com.ascendingdc.training.project2020.dto.ProjectDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper implements RowMapper<ProjectDto> {
    @Override
    public ProjectDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProjectDto project = new ProjectDto();
        project.setId(rs.getLong("id"));
        project.setName(rs.getString("name"));
        project.setDescription(rs.getString("description"));
        project.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime().toLocalDate());
        return project;
    }
}
