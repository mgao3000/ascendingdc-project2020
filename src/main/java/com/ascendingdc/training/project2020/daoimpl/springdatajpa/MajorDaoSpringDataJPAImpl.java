package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.MajorDao;
import com.ascendingdc.training.project2020.daoimpl.repository.MajorRepository;
import com.ascendingdc.training.project2020.entity.Major;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository("majorSpringDataJPADao")
public class MajorDaoSpringDataJPAImpl implements MajorDao {

    private Logger logger = LoggerFactory.getLogger(MajorDaoSpringDataJPAImpl.class);

    @Autowired
    private MajorRepository majorRepository;

    @Override
    public Major save(Major major) {
        Major savedmajor = majorRepository.save(major);
        return savedmajor;
    }

    @Override
    public Major update(Major major) {
        Major updatedmajor = majorRepository.save(major);
        return updatedmajor;
    }

    @Override
    public boolean deleteByName(String majorName) {
        return majorRepository.deleteByName(majorName) > 0;
    }

    @Override
    public boolean deleteById(Long majorId) {
        boolean successFlag = false;
        try {
            majorRepository.deleteById(majorId);
            successFlag = true;
        } catch (IllegalArgumentException iae) {
            //do nothing
        } catch (OptimisticLockingFailureException olfe) {
            //do nothing
        }
        return successFlag;
    }

    @Override
    public boolean delete(Major major) {
        boolean successFlag = false;
        try {
            majorRepository.delete(major);
            successFlag = true;
        } catch (IllegalArgumentException iae) {
            //do nothing
        } catch (OptimisticLockingFailureException olfe) {
            //do nothing
        }
        return successFlag;
    }

    @Override
    public List<Major> getMajors() {
        return majorRepository.findAll();
    }

    @Override
    public Major getMajorById(Long id) {
        Major major = null;
        Optional<Major> majorOptional = majorRepository.findById(id);
        if(majorOptional.isPresent())
            major = majorOptional.get();
        return major;
    }

    @Override
    public Major getMajorByName(String majorName) {
        return majorRepository.findByName(majorName);
    }

    @Override
    public List<Major> getMajorsWithChildren() {
        return majorRepository.findAllMajorsWithStudentsAndProjects();
    }

    @Override
    public Major getMajorAndStudentsAndProjectsByMajorId(Long majorId) {
        return majorRepository.findMajorWithStudentsAndProjectsByMajorId(majorId);
    }

    @Override
    public Major getMajorAndStudentsAndProjectsByMajorName(String majorName) {
        return majorRepository.findMajorWithStudentsAndProjectsByMajorName(majorName);
    }
}
