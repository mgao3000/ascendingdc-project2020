package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.MajorDao;
import com.ascendingdc.training.project2020.daoimpl.repository.MajorRepository;
import com.ascendingdc.training.project2020.entity.Major;
import com.ascendingdc.training.project2020.exception.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
    public boolean deleteByName(String majorName) {
//        return majorRepository.deleteByName(majorName) > 0;
        boolean successFlag = false;
        try {
            int deleteResult = majorRepository.deleteByName(majorName);
            if(deleteResult > 0)
                successFlag = true;
        } catch (IllegalArgumentException iae) {
            logger.error("caught IllegalArgumentException when trying deleteByName with majorName={}, error={}", majorName, iae.getMessage());
        } catch (OptimisticLockingFailureException olfe) {
            logger.error("caught OptimisticLockingFailureException when trying deleteByName with majorName={}, error={}", majorName, olfe.getMessage());
        }
        return successFlag;

    }

    @Override
    public boolean deleteById(Long majorId) {
        boolean successFlag = false;
        try {
            Optional<Major> optionalMajor = majorRepository.findById(majorId);
            if(optionalMajor.isPresent()) {
                majorRepository.deleteById(majorId);
                successFlag = true;
            }
        } catch (IllegalArgumentException iae) {
            logger.error("caught IllegalArgumentException when trying deleteById with majorId={}, error={}", majorId, iae.getMessage());
        } catch (OptimisticLockingFailureException olfe) {
            logger.error("caught OptimisticLockingFailureException when trying deleteById with majorId={}, error={}", majorId, olfe.getMessage());
        }
        return successFlag;
    }

    @Override
    public boolean delete(Major major) {
        boolean successFlag = false;
        if(major != null) {
            successFlag = deletingMajor(major);
        }
        return successFlag;
    }

    private boolean deletingMajor(Major major) {
        boolean deleteFlag = false;
        try {
                Optional<Major> optionalMajor = majorRepository.findById(major.getId());
                if(optionalMajor.isPresent()) {
                    majorRepository.delete(major);
                    deleteFlag = true;
                }
        } catch (IllegalArgumentException iae) {
            logger.error("caught IllegalArgumentException when trying delete major, error={}", iae.getMessage());
        } catch (OptimisticLockingFailureException olfe) {
            logger.error("caught OptimisticLockingFailureException when trying delete major, error={}", olfe.getMessage());
        }
        return deleteFlag;
    }

    @Override
    public List<Major> getMajors() {
        List<Major> majorList = majorRepository.findAll();
        if(majorList == null)
            majorList = new ArrayList<Major>();
        return majorList;

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
        List<Major> majorList = majorRepository.findAllMajorsWithStudentsAndProjects();
        if(majorList == null)
            majorList = new ArrayList<Major>();
        return majorList;
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
