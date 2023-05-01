package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDao;
import com.ascendingdc.training.project2020.dto.DepartmentDto;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.service.DepartmentService;
import com.ascendingdc.training.project2020.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final static Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    @Qualifier("departmentSpringDataJPADao")
    private DepartmentDao departmentDao;

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        Department department = DtoAndEntityConvertUtil.convertDepartmentDtoToDepartment(departmentDto);
        Department savedDepartment = departmentDao.save(department);
        DepartmentDto savedDepartmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(savedDepartment);
        return savedDepartmentDto;
    }

    @Override
    public DepartmentDto update(DepartmentDto departmentDto) {
        Department department = DtoAndEntityConvertUtil.convertDepartmentDtoToDepartment(departmentDto);
        Department updatedDepartment = departmentDao.update(department);
        DepartmentDto updatedDepartmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(updatedDepartment);
        return updatedDepartmentDto;
    }

    @Override
    public boolean deleteByName(String deptName) {
        boolean deleteResult = departmentDao.deleteByName(deptName);
        return deleteResult;
    }

    @Override
    public boolean delete(DepartmentDto departmentDto) {
        Department department = DtoAndEntityConvertUtil.convertDepartmentDtoToDepartment(departmentDto);
        boolean deleteResult = departmentDao.delete(department);
        return deleteResult;
    }

    @Override
    public List<DepartmentDto> getDepartments() {
        List<Department> departmentList = departmentDao.getDepartments();
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        for(Department department : departmentList) {
            departmentDtoList.add(DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department));
        }
        return departmentDtoList;
    }

    @Override
    public List<DepartmentDto> getDepartmentsWithoutJoinFetch() {
        List<Department> departmentList = departmentDao.getDepartmentsWithoutJoinFetch();
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        for(Department department : departmentList) {
            departmentDtoList.add(DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department));
        }
        return departmentDtoList;
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentDao.getDepartmentById(id);
        DepartmentDto departmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department);
        return departmentDto;
    }

    @Override
    public List<DepartmentDto> getDepartmentsWithChildren() {
        List<Department> departmentList = departmentDao.getDepartmentsWithChildren();
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        for(Department department : departmentList) {
            departmentDtoList.add(DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department));
        }
        return departmentDtoList;
    }

    @Override
    public DepartmentDto getDepartmentLazyByName(String deptName) {
        Department department = departmentDao.getDepartmentLazyByName(deptName);
        DepartmentDto departmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department);
        return departmentDto;
    }

    @Override
    public DepartmentDto getDepartmentLazyByDeptId(Long id) {
        Department department = departmentDao.getDepartmentLazyByDeptId(id);
        DepartmentDto departmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department);
        return departmentDto;
    }

    @Override
    public DepartmentDto getDepartmentEagerByName(String deptName) {
        Department department = departmentDao.getDepartmentEagerByName(deptName);
        DepartmentDto departmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department);
        return departmentDto;
    }

    @Override
    public DepartmentDto getDepartmentEagerByDeptId(Long id) {
        Department department = departmentDao.getDepartmentEagerByDeptId(id);
        DepartmentDto departmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department);
        return departmentDto;
    }

    @Override
    public DepartmentDto getDepartmentAndEmployeesByDeptName(String deptName) {
        Department department = departmentDao.getDepartmentAndEmployeesByDeptName(deptName);
        DepartmentDto departmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department);
        return departmentDto;
    }

    @Override
    public DepartmentDto getDepartmentAndEmployeesAndAccounts(String deptName) {
        Department department = departmentDao.getDepartmentAndEmployeesAndAccounts(deptName);
        DepartmentDto departmentDto = DtoAndEntityConvertUtil.convertDepartmentToDepartmentDto(department);
        return departmentDto;
    }
}
