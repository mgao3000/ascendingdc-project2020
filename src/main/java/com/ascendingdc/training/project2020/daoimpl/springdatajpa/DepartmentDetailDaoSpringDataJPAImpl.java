package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDetailDao;
import com.ascendingdc.training.project2020.daoimpl.repository.DepartmentDetailRepository;
import com.ascendingdc.training.project2020.entity.DepartmentDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("departmentDetailSpringDataJPADao")
public class DepartmentDetailDaoSpringDataJPAImpl implements DepartmentDetailDao {

    private Logger logger = LoggerFactory.getLogger(DepartmentDetailDaoSpringDataJPAImpl.class);

    @Autowired
    private DepartmentDetailRepository departmentDetailRepository;

    @Override
    public List<DepartmentDetail> getDepartmentDetails() {
        return departmentDetailRepository.findAll();
    }

    @Override
    public DepartmentDetail getDepartmentDetailById(Long id) {
        DepartmentDetail departmentDetail = null;
        Optional<DepartmentDetail> departmentDetailOptional = departmentDetailRepository.findById(id);
        if(departmentDetailOptional.isPresent()) {
            departmentDetail = departmentDetailOptional.get();
        }
        return departmentDetail;
    }

    @Override
    public DepartmentDetail save(DepartmentDetail departmentDetail) {
        return departmentDetailRepository.save(departmentDetail);
    }

    @Override
    @Transactional
    public boolean delete(DepartmentDetail departmentDetail) {
        boolean successFlag = false;
        try {
            departmentDetailRepository.delete(departmentDetail);
            successFlag = true;
        } catch (IllegalArgumentException iae) {
            //do nothing
        } catch (OptimisticLockingFailureException olfe) {
            //do nothing
        }
        return successFlag;
//        return departmentDetailRepository.deleteDepartmentDetailUsingId(departmentDetail.getId()) > 0;
    }
}
