package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.model.DepartmentDetail;

import java.util.List;

public interface DepartmentDetailDao {
    List<DepartmentDetail> getDepartmentDetails();
    DepartmentDetail getDepartmentDetailById(Long id);
    DepartmentDetail save(DepartmentDetail departmentDetail);
    boolean delete(DepartmentDetail departmentDetail);
}
