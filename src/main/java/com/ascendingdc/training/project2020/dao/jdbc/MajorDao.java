package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.dto.MajorDto;

import java.util.List;

public interface MajorDao {
    MajorDto save(MajorDto major);
    MajorDto update(MajorDto major);
    boolean deleteByName(String majorName);
    boolean deleteById(Long majorId);
    boolean delete(MajorDto major);
    List<MajorDto> getMajors();
    MajorDto getMajorById(Long id);
    MajorDto getMajorByName(String majorName);

    List<MajorDto> getMajorsWithChildren();
    MajorDto getMajorAndStudentsAndProjectsByMajorId(Long majorId);
    MajorDto getMajorAndStudentsAndProjectsByMajorName(String majorName);

}
