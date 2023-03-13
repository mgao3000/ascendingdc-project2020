package com.ascendingdc.training.project2020.daoimpl.repository;

import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Long deleteByLoginName(String loginName);

    Student findByLoginName(String loginName);

    @Query("SELECT distinct stu from Student as stu left join fetch stu.major as m where m.id = :id")
    List<Student> findStudentsByMajorId(@Param(value = "id") Long majorId);

    @Query("SELECT distinct proj from Project as proj left join fetch proj.students as stu where stu.id = :id")
    List<Project> findProjectsByStudentId(@Param(value = "id") Long studentId);

    @Query("SELECT distinct proj from Project as proj left join fetch proj.students as stu where stu.loginName = :loginName")
    List<Project> findProjectsByStudentLoginName(@Param(value = "loginName") String loginName);

    @Query("SELECT distinct stu from Student as stu left join fetch stu.projects")
    List<Student> findAllStudentsWithAssociatedProjects();

    @Query("SELECT distinct stu from Student as stu left join fetch stu.projects where stu.id = :id")
    Student findStudentWithAssociatedProjectsByStudentId(@Param(value = "id") Long studentId);

    @Query("SELECT distinct stu from Student as stu left join fetch stu.projects where stu.loginName = :loginName")
    Student findStudentWithAssociatedProjectsByStudentLoginName(@Param(value = "loginName") String loginName);

    @Query("SELECT distinct stu from Student as stu join fetch stu.major as m left join fetch stu.projects where m.id = :id")
    List<Student> findStudentsWithAssociatedProjectsAndMajorByMajorId(@Param(value = "id") Long majorId);


}
