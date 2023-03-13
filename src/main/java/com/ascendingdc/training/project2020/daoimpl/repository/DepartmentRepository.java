package com.ascendingdc.training.project2020.daoimpl.repository;

import com.ascendingdc.training.project2020.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Transactional
    @Modifying
    @Query("delete from Department as dept where dept.id = :id")
    int deleteDepartmentUsingId(Long id);

    Long deleteByName(String name);

    Long removeByName(String name); //This method is the same as the above

    @Transactional
    @Modifying
    @Query("delete from Department as dept where dept.name = :name")
    int deleteDepartmentUsingName(String name);
    /**
     * The followings are examples
     */
//    List<Fruit> deleteByColor(String color);
//    List<Fruit> removeByColor(String color);


    @Query("FROM Department as dept join fetch dept.departmentDetail left join fetch dept.employees as em left join fetch em.accounts")
    List<Department> findAllDepartmentsWithChildren();

    @Query("FROM Department as dept join fetch dept.departmentDetail left join fetch dept.employees as em left join fetch em.accounts where dept.name = :name")
    Department findDepartmentWithChildrenByName(@Param("name") String name);

    @Query("FROM Department as dept join fetch dept.departmentDetail left join fetch dept.employees as em left join fetch em.accounts where dept.id = :id")
    Department findDepartmentWithChildrenById(@Param("id") Long deptId);

    Department findByName(String name);

    @Query("FROM Department as dept left join fetch dept.employees as em where dept.name = :name")
    Department findDepartmentWithAssociatedEmployeesByDepartmentName(@Param("name") String departmentName);

    @Query("FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts where dept.name = :name")
    Department findDepartmentWithAssociatedEmployeesAndAccountsByDepartmentName(@Param("name") String departmentName);


}
