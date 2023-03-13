package com.ascendingdc.training.project2020.daoimpl.repository;

import com.ascendingdc.training.project2020.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Transactional
    @Modifying
    @Query("update Employee em set em.address= :address where em.name= :name")
    int updateAddressByName(@Param(value = "address") String address,
                            @Param(value="name") String name);

    Long deleteByName(String name);

    Employee findByName(String name);

    @Query("SELECT em from Employee as em left join fetch em.department where em.id= :id")
    Employee findEmployeeWithDepartmentByEmployeeId(@Param(value = "id") Long employeeId);

}
