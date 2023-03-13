package com.ascendingdc.training.project2020.daoimpl.repository;

import com.ascendingdc.training.project2020.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Long deleteByName(String name);

    Project findByName(String name);

    @Query("SELECT distinct proj from Project as proj left join fetch proj.students")
    List<Project> findAllProjectsWithAssociatedStudents();

    @Query("SELECT distinct proj from Project as proj left join fetch proj.students as stu where proj.id = :id")
    Project findProjectWithAssociatedStudentsByProjectId(@Param(value = "id") Long projectId);

    @Query("SELECT distinct proj from Project as proj left join fetch proj.students as stu where proj.name = :name")
    Project findProjectWithAssociatedStudentsByProjectName(@Param(value = "name") String projectName);


}
