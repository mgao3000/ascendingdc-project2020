package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dao.hibernate.MajorDao;
import com.ascendingdc.training.project2020.dao.hibernate.StudentDao;
import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.entity.Major;
import com.ascendingdc.training.project2020.service.MajorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
public class MajorServiceOne implements MajorService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    @Qualifier("majorSpringDataJPADao")
    private MajorDao majorDao;

    @Autowired
    @Qualifier("studentSpringDataJPADao")
    private StudentDao studentDao;

    @Override
    public MajorDto save(MajorDto majorDto) {
        Major major = majorDto.convertMajorDtoToMajor();
        Major savedMajor = majorDao.save(major);
        MajorDto savedMajorDto = savedMajor.convertMajorToMajorDto();
        return savedMajorDto;
    }

    @Override
    public MajorDto update(MajorDto majorDto) {
        Major major = majorDto.convertMajorDtoToMajor();
        Major updatedMajor = majorDao.update(major);
        MajorDto updatedMajorDto = updatedMajor.convertMajorToMajorDto();
        return updatedMajorDto;
    }

    @Override
    public boolean deleteByName(String majorName) {
        return majorDao.deleteByName(majorName);
    }

    @Override
    public boolean deleteById(Long majorId) {
        boolean deleteResult = majorDao.deleteById(majorId);
        logger.debug(" === within method MajorserviceOne.deleteById(...), majorId={}, deleteResult={}", majorId, deleteResult);
        return deleteResult;
    }

    @Override
    public boolean delete(MajorDto majorDto) {
        Major major = majorDto.convertMajorDtoToMajor();
        boolean deleteResult = majorDao.delete(major);
        return deleteResult;
    }

    @Override
    public List<MajorDto> getMajors() {
        List<Major> majorList = majorDao.getMajors();
        List<MajorDto> majorDtoList = getMajorDtoListFromMajorList(majorList);
        return majorDtoList;
    }

    private List<MajorDto> getMajorDtoListFromMajorList(List<Major> majorList) {
        List<MajorDto> majorDtoList = new ArrayList<>();
        for(Major major : majorList) {
            MajorDto majorDto = major.convertMajorToMajorDto();
            majorDtoList.add(majorDto);
        }
        return majorDtoList;
    }

    @Override
    public MajorDto getMajorById(Long id) {
        Major retrievedMajor = majorDao.getMajorById(id);
        MajorDto retrievedMajorDto = retrievedMajor.convertMajorToMajorDto();
        return retrievedMajorDto;
    }

    @Override
    public MajorDto getMajorByName(String majorName) {
        Major retrievedMajor = majorDao.getMajorByName(majorName);
        MajorDto retrievedMajorDto = retrievedMajor.convertMajorToMajorDto();
        return retrievedMajorDto;
    }

    @Override
    public List<MajorDto> getMajorsWithChildren() {
        List<Major> majorList = majorDao.getMajorsWithChildren();
        List<MajorDto> majorDtoList = getMajorDtoListFromMajorList(majorList);
        return majorDtoList;
    }

    @Override
    public MajorDto getMajorAndStudentsAndProjectsByMajorId(Long majorId) {
        Major retrievedMajor = majorDao.getMajorAndStudentsAndProjectsByMajorId(majorId);
        MajorDto retrievedMajorDto = retrievedMajor.convertMajorToMajorDto();
        return retrievedMajorDto;
    }

    @Override
    public MajorDto getMajorAndStudentsAndProjectsByMajorName(String majorName) {
        Major retrievedMajor = majorDao.getMajorAndStudentsAndProjectsByMajorName(majorName);
        MajorDto retrievedMajorDto = retrievedMajor.convertMajorToMajorDto();
        return retrievedMajorDto;
    }
}
