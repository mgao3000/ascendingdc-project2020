package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDetailDao;
import com.ascendingdc.training.project2020.daoimpl.repository.DepartmentDetailRepository;
import com.ascendingdc.training.project2020.entity.DepartmentDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository("departmentDetailSpringDataJPADao")
public class DepartmentDetailDaoSpringDataJPAImpl implements DepartmentDetailDao {

    private Logger logger = LoggerFactory.getLogger(DepartmentDetailDaoSpringDataJPAImpl.class);

    @Autowired
    private DepartmentDetailRepository departmentDetailRepository;

    @Override
    public List<DepartmentDetail> getDepartmentDetails() {
        return null;
    }

    @Override
    public DepartmentDetail getDepartmentDetailById(Long id) {
        return null;
    }

    @Override
    public DepartmentDetail save(DepartmentDetail departmentDetail) {
        return null;
    }

    @Override
    public boolean delete(DepartmentDetail departmentDetail) {
        return false;
    }
}
