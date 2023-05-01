package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dto.MajorDto;

import java.util.List;

public interface MajorService {
    MajorDto save(MajorDto majorDto);
    MajorDto update(MajorDto majorDto);
    boolean deleteByName(String majorName);
    boolean deleteById(Long majorId);
    boolean delete(MajorDto majorDto);
    List<MajorDto> getMajors();
    MajorDto getMajorById(Long id);
    MajorDto getMajorByName(String majorName);

    List<MajorDto> getMajorsWithChildren();
    MajorDto getMajorAndStudentsAndProjectsByMajorId(Long majorId);
    MajorDto getMajorAndStudentsAndProjectsByMajorName(String majorName);

}
