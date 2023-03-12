package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.StudentDao;
import com.ascendingdc.training.project2020.daoimpl.repository.StudentRepository;
import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository("studentSpringDataJPADao")
public class StudentDaoSpringDataJPAImpl implements StudentDao {

    private Logger logger = LoggerFactory.getLogger(StudentDaoSpringDataJPAImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student save(Student student) {
        return null;
    }

    @Override
    public Student update(Student student) {
        return null;
    }

    @Override
    public boolean deleteByLoginName(String loginName) {
        return false;
    }

    @Override
    public boolean deleteById(Long studentId) {
        return false;
    }

    @Override
    public boolean delete(Student student) {
        return false;
    }

    @Override
    public List<Student> getStudents() {
        return null;
    }

    @Override
    public Student getStudentById(Long id) {
        return null;
    }

    @Override
    public Student getStudentByLoginName(String loginName) {
        return null;
    }

    @Override
    public List<Student> getStudentsByMajorId(Long majorId) {
        return null;
    }

    @Override
    public List<Project> getAssociatedProjectsByStudentId(Long studentId) {
        return null;
    }

    @Override
    public List<Project> getAssociatedProjectsByStudentLoginName(String loginName) {
        return null;
    }

    @Override
    public List<Student> getStudentsWithAssociatedProjects() {
        return null;
    }

    @Override
    public Student getStudentWithAssociatedProjectsByStudentId(Long studentId) {
        return null;
    }

    @Override
    public Student getStudentWithAssociatedProjectsByLoginName(String loginName) {
        return null;
    }

    @Override
    public List<Student> getStudentsWithAssociatedProjectsByMajorId(Long majorId) {
        return null;
    }
}
