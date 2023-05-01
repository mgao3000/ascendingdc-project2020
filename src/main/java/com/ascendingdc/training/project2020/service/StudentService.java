package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto save(StudentDto studentDto);
    StudentDto update(StudentDto studentDto);
    boolean deleteByLoginName(String loginName);
    boolean deleteById(Long studentId);
    boolean delete(StudentDto studentDto);
    List<StudentDto> getStudents();
    StudentDto getStudentById(Long id);
    StudentDto getStudentByLoginName(String loginName);

    List<StudentDto> getStudentsWithAssociatedProjects();
    StudentDto getStudentWithAssociatedProjectsByStudentId(Long studentId);
    StudentDto getStudentWithAssociatedProjectsByLoginName(String loginName);

}
