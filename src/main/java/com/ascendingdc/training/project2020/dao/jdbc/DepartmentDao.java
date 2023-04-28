package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDao {
    DepartmentDto save(DepartmentDto department);
    DepartmentDto update(DepartmentDto department);
    boolean deleteByName(String deptName);
    boolean delete(DepartmentDto dept);
    List<DepartmentDto> getDepartments();
    List<DepartmentDto> getDepartmentsWithoutJoinFetch();
    DepartmentDto getDepartmentById(Long id);
    List<DepartmentDto> getDepartmentsWithChildren();
    DepartmentDto getDepartmentByName(String deptName);
    DepartmentDto getDepartmentEagerByDeptId(Long id);
    List<Object[]> getDepartmentAndEmployeesByDeptName(String deptName);
    List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName);
    List<Object[]> getDepartmentAndEmployees(String deptName);

}
