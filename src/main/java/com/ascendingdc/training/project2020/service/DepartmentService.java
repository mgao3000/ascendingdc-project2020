package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto save(DepartmentDto departmentDto);
    DepartmentDto update(DepartmentDto departmentDto);
    boolean deleteByName(String deptName);
    boolean delete(DepartmentDto departmentDto);
    List<DepartmentDto> getDepartments();
    List<DepartmentDto> getDepartmentsWithoutJoinFetch();
    DepartmentDto getDepartmentById(Long id);
    List<DepartmentDto> getDepartmentsWithChildren();
    DepartmentDto getDepartmentLazyByName(String deptName);
    DepartmentDto getDepartmentLazyByDeptId(Long id);
    DepartmentDto getDepartmentEagerByName(String deptName);
    DepartmentDto getDepartmentEagerByDeptId(Long id);
    DepartmentDto getDepartmentAndEmployeesByDeptName(String deptName);
    DepartmentDto getDepartmentAndEmployeesAndAccounts(String deptName);

}
