package com.ascendingdc.training.project2020.daoimpl.hibernate;

import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDao;
import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDetailDao;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.DepartmentDetail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentDtoDetailDaoHibernateTest extends AbstractDaoHibernateTest {
    private Logger logger = LoggerFactory.getLogger(DepartmentDtoDetailDaoHibernateTest.class);

    private static DepartmentDetailDao departmentDetailDao;
    private static DepartmentDao departmentDao;

    private String deptName;
    private DepartmentDetail departmentDetail;
    private Department department;

    @BeforeAll
    public static void setupOnce() {
        departmentDetailDao = new DepartmentDetailDaoHibernateImpl();
        departmentDao = new DepartmentDaoHibernateImpl();
    }

    @BeforeEach
    public void setup() {
        deptName = "HR-Test1";

        department = createDepartmentEntity(deptName, "Fairfax, VA");

        departmentDetail = createDepartmentDetailEntity(1000, 200000, "DD description");

        department.setDepartmentDetail(departmentDetail);
        departmentDetail.setDepartment(department);

        departmentDao.save(department);
//        departmentDetailDao.save(dd);
    }

    @AfterEach
    public void teardown() {
        departmentDao.delete(department);
//        departmentDetailDao.delete(dd);
    }

    @Test
    public void getDepartmentDetailsTest() {;
        List<DepartmentDetail> departmentDetailList = departmentDetailDao.getDepartmentDetails();
        assertEquals(5, departmentDetailList.size());
    }
}
