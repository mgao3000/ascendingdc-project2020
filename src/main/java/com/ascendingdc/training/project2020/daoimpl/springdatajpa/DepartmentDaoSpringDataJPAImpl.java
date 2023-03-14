package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDao;
import com.ascendingdc.training.project2020.daoimpl.repository.DepartmentRepository;
import com.ascendingdc.training.project2020.entity.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("departmentSpringDataJPADao")
public class DepartmentDaoSpringDataJPAImpl implements DepartmentDao {

    private Logger logger = LoggerFactory.getLogger(DepartmentDaoSpringDataJPAImpl.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department save(Department department) {
        Department savedDepartment = departmentRepository.save(department);
        return savedDepartment;
    }

    @Override
    public Department update(Department department) {
        Department savedDepartment = departmentRepository.save(department);
        return savedDepartment;
    }

    @Override
    @Transactional
    public boolean deleteByName(String deptName) {
        return departmentRepository.deleteByName(deptName) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Department dept) {
//        return departmentRepository.deleteDepartmentUsingId(dept.getId()) > 0;
        return departmentRepository.deleteByName(dept.getName()) > 0;
    }

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAllDepartmentsWithChildren();
    }

    @Override
    public List<Department> getDepartmentsWithoutJoinFetch() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long id) {
        Department retrievedDepartment = null;
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if(departmentOptional.isPresent())
            retrievedDepartment = departmentOptional.get();
        return retrievedDepartment;
    }

    @Override
    public List<Department> getDepartmentsWithChildren() {
        return departmentRepository.findAllDepartmentsWithChildren();
    }

    @Override
    public Department getDepartmentLazyByName(String deptName) {
        return departmentRepository.findByName(deptName);
    }

    @Override
    public Department getDepartmentLazyByDeptId(Long id) {
        Department retrievedDepartment = null;
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if(departmentOptional.isPresent())
            retrievedDepartment = departmentOptional.get();
        return retrievedDepartment;
    }

    @Override
    public Department getDepartmentEagerByName(String deptName) {
        return departmentRepository.findDepartmentWithChildrenByName(deptName);
    }

    @Override
    public Department getDepartmentEagerByDeptId(Long id) {
        return departmentRepository.findDepartmentWithChildrenById(id);
    }

    @Override
    public Department getDepartmentAndEmployeesByDeptName(String deptName) {
        return departmentRepository.findDepartmentWithAssociatedEmployeesByDepartmentName(deptName);
    }

    @Override
    public Department getDepartmentAndEmployeesAndAccounts(String deptName) {
        return departmentRepository.findDepartmentWithAssociatedEmployeesAndAccountsByDepartmentName(deptName);
    }

}
