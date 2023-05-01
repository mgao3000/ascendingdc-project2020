package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dao.hibernate.MajorDao;
import com.ascendingdc.training.project2020.dto.MajorDto;
import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;
import com.ascendingdc.training.project2020.entity.Major;
import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import com.ascendingdc.training.project2020.service.MajorService;
import com.ascendingdc.training.project2020.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MajorServiceImpl implements MajorService {

    private final static Logger logger = LoggerFactory.getLogger(MajorServiceImpl.class);

    @Autowired
    @Qualifier("majorSpringDataJPADao")
    protected MajorDao majorDao;

    @Override
    public MajorDto save(MajorDto majorDto) {
//        Major major = DtoAndEntityConvertUtil.convertMajorDtoToMajor(majorDto);
        Major major = majorDto.convertMajorDtoToMajor();
        Major savedMajor = majorDao.save(major);
//        MajorDto savedMajorDto = DtoAndEntityConvertUtil.convertMajorToMajorDto(savedMajor);
        MajorDto savedMajorDto = savedMajor.convertMajorToMajorDto();
        return savedMajorDto;
    }

    @Override
    public MajorDto update(MajorDto majorDto) {
//        Major major = DtoAndEntityConvertUtil.convertMajorDtoToMajor(majorDto);
        Major major = majorDto.convertMajorDtoToMajor();
        Major updatedMajor = majorDao.update(major);
//        MajorDto updatedMajorDto = DtoAndEntityConvertUtil.convertMajorToMajorDto(updatedMajor);
        MajorDto updatedMajorDto = updatedMajor.convertMajorToMajorDto();
        return updatedMajorDto;
    }

    @Override
    public boolean deleteByName(String majorName) {
        boolean deleteResult = majorDao.deleteByName(majorName);
        return deleteResult;
    }

    @Override
    public boolean deleteById(Long majorId) {
        boolean deleteResult = majorDao.deleteById(majorId);
        return deleteResult;
    }

    @Override
    public boolean delete(MajorDto majorDto) {
//        Major major = DtoAndEntityConvertUtil.convertMajorDtoToMajor(majorDto);
        Major major = majorDto.convertMajorDtoToMajor();
        boolean deleteResult = majorDao.delete(major);
        return deleteResult;
    }

    @Override
    public List<MajorDto> getMajors() {
        List<Major> majorList = majorDao.getMajors();
        List<MajorDto> majorDtoList = new ArrayList<>();
        for(Major major : majorList) {
//            MajorDto majorDto = DtoAndEntityConvertUtil.convertMajorToMajorDto(major);
            MajorDto majorDto = major.convertMajorToMajorDto();
            majorDtoList.add(majorDto);
        }
        return majorDtoList;
    }

    @Override
    public MajorDto getMajorById(Long id) {
        Major major = majorDao.getMajorById(id);
//        MajorDto majorDto = DtoAndEntityConvertUtil.convertMajorToMajorDto(major);
        MajorDto majorDto = major.convertMajorToMajorDto();
        return majorDto;
    }

    @Override
    public MajorDto getMajorByName(String majorName) {
        Major major = majorDao.getMajorByName(majorName);
//        MajorDto majorDto = DtoAndEntityConvertUtil.convertMajorToMajorDto(major);
        MajorDto majorDto = major.convertMajorToMajorDto();
        return majorDto;
    }

    @Override
    public List<MajorDto> getMajorsWithChildren() {
        List<Major> majorList = majorDao.getMajors();
        List<MajorDto> majorDtoList = new ArrayList<>();
        for(Major major : majorList) {
//            MajorDto majorDto = DtoAndEntityConvertUtil.convertMajorToMajorDto(major);
            MajorDto majorDto = major.convertMajorToMajorDto();
            List<StudentDto> studentDtoList = getAssociatedStudentDtoList(major.getStudents());
            majorDto.setStudentList(studentDtoList);
            majorDtoList.add(majorDto);
        }
        return majorDtoList;
    }

    private List<StudentDto> getAssociatedStudentDtoList(Set<Student> students) {
        List<StudentDto> studentDtoList = new ArrayList<>();
        for(Student student : students) {
            StudentDto studentDto = student.convertStudentToStudentDto();
            List<ProjectDto> projectDtoList = getAssociatedProjectDtoList(student.getProjects());
            studentDto.setProjectDtoList(projectDtoList);
            studentDtoList.add(studentDto);
        }
        return studentDtoList;
    }

    private List<ProjectDto> getAssociatedProjectDtoList(List<Project> projects) {
        List<ProjectDto> projectDtoList = new ArrayList<>();
        for(Project project : projects) {
            ProjectDto projectDto = project.convertProjectToProjectDto();
            projectDtoList.add(projectDto);
        }
        return projectDtoList;
    }

    @Override
    public MajorDto getMajorAndStudentsAndProjectsByMajorId(Long majorId) {
        Major major = majorDao.getMajorAndStudentsAndProjectsByMajorId(majorId);
        MajorDto majorDto = major.convertMajorToMajorDto();
        List<StudentDto> studentDtoList = getAssociatedStudentDtoList(major.getStudents());
        majorDto.setStudentList(studentDtoList);

        return majorDto;
    }

    @Override
    public MajorDto getMajorAndStudentsAndProjectsByMajorName(String majorName) {
        Major major = majorDao.getMajorAndStudentsAndProjectsByMajorName(majorName);
        MajorDto majorDto = major.convertMajorToMajorDto();
        List<StudentDto> studentDtoList = getAssociatedStudentDtoList(major.getStudents());
        majorDto.setStudentList(studentDtoList);

        return majorDto;
    }
}