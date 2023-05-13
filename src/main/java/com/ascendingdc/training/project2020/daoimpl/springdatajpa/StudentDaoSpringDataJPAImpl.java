package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.StudentDao;
import com.ascendingdc.training.project2020.daoimpl.repository.StudentRepository;
import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("studentSpringDataJPADao")
public class StudentDaoSpringDataJPAImpl implements StudentDao {

    private Logger logger = LoggerFactory.getLogger(StudentDaoSpringDataJPAImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student save(Student student) {
        Student savedStudent = studentRepository.save(student);
        return savedStudent;
    }

    @Override
    public Student update(Student student) {
        Student updatedStudent = studentRepository.save(student);
        return updatedStudent;
    }

    @Override
    public boolean deleteByLoginName(String loginName) {
        return studentRepository.deleteByLoginName(loginName) > 0;
    }

    @Override
    public boolean deleteById(Long studentId) {
        boolean successFlag = false;
        try {
            studentRepository.deleteById(studentId);
            successFlag = true;
        } catch (IllegalArgumentException iae) {
            //do nothing
        } catch (OptimisticLockingFailureException olfe) {
            //do nothing
        }
        return successFlag;
    }

    @Override
    public boolean delete(Student student) {
        boolean successFlag = false;
        try {
            studentRepository.delete(student);
            successFlag = true;
        } catch (IllegalArgumentException iae) {
            //do nothing
        } catch (OptimisticLockingFailureException olfe) {
            //do nothing
        }
        return successFlag;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        Student student = null;
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isPresent())
            student = studentOptional.get();
        return student;
    }

    @Override
    public Student getStudentByLoginName(String loginName) {
        return studentRepository.findByLoginName(loginName);
    }

//    @Override
//    public List<Student> getStudentsByMajorId(Long majorId) {
//        return studentRepository.findStudentsByMajorId(majorId);
//    }
//
//    @Override
//    public List<Project> getAssociatedProjectsByStudentId(Long studentId) {
//        return studentRepository.findProjectsByStudentId(studentId);
//    }

    @Override
    public List<Project> getAssociatedProjectsByStudentLoginName(String loginName) {
 //       return studentRepository.findProjectsByStudentLoginName(loginName);
        List<Project> projectList = studentRepository.findProjectsByStudentLoginName(loginName);
        if(projectList == null) {
            projectList = new ArrayList<>();
        }
        return projectList;
    }

    @Override
    public List<Student> getStudentsWithAssociatedProjects() {
        return studentRepository.findAllStudentsWithAssociatedProjects();
    }

    @Override
    public Student getStudentWithAssociatedProjectsByStudentId(Long studentId) {
        return studentRepository.findStudentWithAssociatedProjectsByStudentId(studentId);
    }

    @Override
    public Student getStudentWithAssociatedProjectsByLoginName(String loginName) {
        return studentRepository.findStudentWithAssociatedProjectsByStudentLoginName(loginName);
    }

//    @Override
//    public List<Student> getStudentsWithAssociatedProjectsByMajorId(Long majorId) {
//        return studentRepository.findStudentsWithAssociatedProjectsAndMajorByMajorId(majorId);
//    }
}
