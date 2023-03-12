package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.MajorDao;
import com.ascendingdc.training.project2020.daoimpl.repository.MajorRepository;
import com.ascendingdc.training.project2020.entity.Major;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository("majorSpringDataJPADao")
public class MajorDaoSpringDataJPAImpl implements MajorDao {

    private Logger logger = LoggerFactory.getLogger(MajorDaoSpringDataJPAImpl.class);

    @Autowired
    private MajorRepository majorRepository;

    @Override
    public Major save(Major major) {
        return null;
    }

    @Override
    public Major update(Major major) {
        return null;
    }

    @Override
    public boolean deleteByName(String majorName) {
        return false;
    }

    @Override
    public boolean deleteById(Long majorId) {
        return false;
    }

    @Override
    public boolean delete(Major major) {
        return false;
    }

    @Override
    public List<Major> getMajors() {
        return null;
    }

    @Override
    public Major getMajorById(Long id) {
        return null;
    }

    @Override
    public Major getMajorByName(String majorName) {
        return null;
    }

    @Override
    public List<Major> getMajorsWithChildren() {
        return null;
    }

    @Override
    public Major getMajorAndStudentsAndProjectsByMajorId(Long majorId) {
        return null;
    }

    @Override
    public Major getMajorAndStudentsAndProjectsByMajorName(String majorName) {
        return null;
    }
}
