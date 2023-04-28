package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.dto.DepartmentDetailDto;

import java.util.List;

public interface DepartmentDetailDao {
    List<DepartmentDetailDto> getDepartmentDetails();
    DepartmentDetailDto getDepartmentDetailById(Long id);
    DepartmentDetailDto save(DepartmentDetailDto departmentDetail);
    boolean delete(DepartmentDetailDto departmentDetail);
}
