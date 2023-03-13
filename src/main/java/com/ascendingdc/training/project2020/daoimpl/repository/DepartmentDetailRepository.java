package com.ascendingdc.training.project2020.daoimpl.repository;

import com.ascendingdc.training.project2020.entity.DepartmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentDetailRepository extends JpaRepository<DepartmentDetail, Long> {

    @Modifying
    @Query("delete from DepartmentDetail as dd where dd.id = :id")
    int deleteDepartmentDetailUsingId(Long id);

}
