package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.model.Project;
import com.ascendingdc.training.project2020.model.Student;

import java.util.List;

public interface StudentDao {
//    Student save(Student student);
    Student save(Student student, Long majorId);
    Student update(Student student);
    boolean deleteByLoginName(String loginName);
    boolean deleteById(Long studentId);
    boolean delete(Student student);
    List<Student> getStudents();
    Student getStudentById(Long id);
    Student getStudentByLoginName(String loginName);

    List<Student> getStudentsByMajorId (Long majorId);
    List<Project> getAssociatedProjectsByStudentId(Long studentId);
    List<Project> getAssociatedProjectsByStudentLoginName(String loginName);

    List<Student> getStudentsWithAssociatedProjects();
    Student getStudentWithAssociatedProjectsByStudentId(Long studentId);
    Student getStudentWithAssociatedProjectsByLoginName(String loginName);

    List<Student> getStudentsWithAssociatedProjectsByMajorId (Long majorId);
}
