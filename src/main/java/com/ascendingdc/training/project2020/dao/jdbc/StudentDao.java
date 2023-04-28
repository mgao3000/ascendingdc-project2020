package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;

import java.util.List;

public interface StudentDao {
//    Student save(Student student);
    StudentDto save(StudentDto student, Long majorId);
    StudentDto update(StudentDto student);
    boolean deleteByLoginName(String loginName);
    boolean deleteById(Long studentId);
    boolean delete(StudentDto student);
    List<StudentDto> getStudents();
    StudentDto getStudentById(Long id);
    StudentDto getStudentByLoginName(String loginName);

    List<StudentDto> getStudentsByMajorId (Long majorId);
    List<ProjectDto> getAssociatedProjectsByStudentId(Long studentId);
    List<ProjectDto> getAssociatedProjectsByStudentLoginName(String loginName);

    List<StudentDto> getStudentsWithAssociatedProjects();
    StudentDto getStudentWithAssociatedProjectsByStudentId(Long studentId);
    StudentDto getStudentWithAssociatedProjectsByLoginName(String loginName);

    List<StudentDto> getStudentsWithAssociatedProjectsByMajorId (Long majorId);
}
