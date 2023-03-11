package com.ascendingdc.training.project2020.dao.hibernate;

import com.ascendingdc.training.project2020.entity.Department;

import java.util.List;

public interface DepartmentDao {
    Department save(Department department);
    Department update(Department department);
    boolean deleteByName(String deptName);
    boolean delete(Department dept);
    List<Department> getDepartments();
    List<Department> getDepartmentsWithoutJoinFetch();
    Department getDepartmentById(Long id);
    List<Department> getDepartmentsWithChildren();
    Department getDepartmentByName(String deptName);
    Department getDepartmentEagerByDeptId(Long id);
    List<Object[]> getDepartmentAndEmployeesByDeptName(String deptName);
    List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName);
    List<Object[]> getDepartmentAndEmployees(String deptName);

}
