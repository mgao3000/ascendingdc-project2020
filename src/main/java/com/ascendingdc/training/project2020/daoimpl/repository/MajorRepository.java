package com.ascendingdc.training.project2020.daoimpl.repository;

import com.ascendingdc.training.project2020.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {

    Long deleteByName(String name);

    Major findByName(String name);

    @Query("SELECT distinct m from Major as m left join fetch m.students as stu left join fetch stu.projects")
    List<Major> findAllMajorsWithStudentsAndProjects();

    @Query("SELECT distinct m from Major as m left join fetch m.students as stu left join fetch stu.projects where m.id = :id")
    Major findMajorWithStudentsAndProjectsByMajorId(@Param(value = "id") Long majorId);

    @Query("SELECT distinct m from Major as m left join fetch m.students as stu left join fetch stu.projects where m.name = :name")
    Major findMajorWithStudentsAndProjectsByMajorName(@Param(value = "name") String majorName);



}
