package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDao;
import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDetailDao;
import com.ascendingdc.training.project2020.daoimpl.hibernate.AbstractDaoHibernateTest;
import com.ascendingdc.training.project2020.daoimpl.hibernate.DepartmentDaoHibernateImpl;
import com.ascendingdc.training.project2020.daoimpl.hibernate.DepartmentDetailDaoHibernateImpl;
import com.ascendingdc.training.project2020.daoimpl.repository.DepartmentDetailRepository;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.DepartmentDetail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DepartmentDetailDaoSpringDataJPATest extends AbstractDaoHibernateTest {
    private Logger logger = LoggerFactory.getLogger(DepartmentDetailDaoSpringDataJPATest.class);

    @Autowired
    @Qualifier("departmentDetailSpringDataJPADao")
    private DepartmentDetailDao departmentDetailDao;

    @Autowired
    @Qualifier("departmentSpringDataJPADao")
    private DepartmentDao departmentDao;

    @Autowired
    private DepartmentDetailRepository departmentDetailRepository;


    private String deptName;
    private DepartmentDetail departmentDetail;

    private DepartmentDetail savedDepartmentDetail;
    private Department department;

    @BeforeAll
    public static void setupOnce() {
//        departmentDetailDao = new DepartmentDetailDaoHibernateImpl();
//        departmentDao = new DepartmentDaoHibernateImpl();
    }

    @BeforeEach
    public void setup() {
        deptName = "HR-Test1";

        department = createDepartmentEntity(deptName, "Fairfax, VA");

        departmentDetail = createDepartmentDetailEntity(1000, 200000, "DD description");

        department.setDepartmentDetail(departmentDetail);
        departmentDetail.setDepartment(department);

//        departmentDao.save(department);
        savedDepartmentDetail = departmentDetailDao.save(departmentDetail);
    }

    @AfterEach
    public void teardown() {
//        departmentDao.delete(department);
        departmentDetailDao.delete(departmentDetail);
//        departmentDetailDao.delete(savedDepartmentDetail);
//        departmentDetailRepository.delete(savedDepartmentDetail);
    }

    @Test
    public void getDepartmentDetailsTest() {;
        List<DepartmentDetail> departmentDetailList = departmentDetailDao.getDepartmentDetails();
        assertEquals(5, departmentDetailList.size());
    }

    @Test
    public void testGetDepartmentDetailById() {
        /**
         * First create a temp DepartmentDetail and save it
         */
        DepartmentDetail departmentDetail1 = createDepartmentDetailEntity(1111, 200000, "Department Detail Description test");
        DepartmentDetail savedDepartmentDetail = departmentDetailDao.save(departmentDetail1);
        assertNotNull(savedDepartmentDetail.getId(), "A saved DepartmentDetail should have a ID with NULL value");
        assertEquals(departmentDetail1.getDescription(), savedDepartmentDetail.getDescription(), "The description value of savedDepartmentDetail should be the same");

        /**
         * Then retrieve DepartmentDetail by id and check it
         */
        DepartmentDetail retrievedDepartmentDetail = departmentDetailDao.getDepartmentDetailById(savedDepartmentDetail.getId());
        assertEquals(departmentDetail1.getId(), savedDepartmentDetail.getId(), "The ID value of savedDepartmentDetail should be the same");
        assertEquals(departmentDetail1.getDescription(), savedDepartmentDetail.getDescription(), "The description value of savedDepartmentDetail should be the same");
        assertEquals(departmentDetail1.getSize(), savedDepartmentDetail.getSize(), "The size value of savedDepartmentDetail should be the same");
        assertEquals(departmentDetail1.getRevenue(), savedDepartmentDetail.getRevenue(), "The revenue value of savedDepartmentDetail should be the same");

        /**
         * Now delete the temp DepartmentDetail
         */
        boolean deleteResult = departmentDetailDao.delete(retrievedDepartmentDetail);
        if (deleteResult)
            logger.info("===== testSaveDepartmentDetails(), the new create departmentDetail1 with ID={}, size={}, Revenue={}, Description={} is deleted",
                    savedDepartmentDetail.getId(),
                    savedDepartmentDetail.getSize(),
                    savedDepartmentDetail.getRevenue(),
                    savedDepartmentDetail.getDescription());
        else
            logger.info("===== testSaveDepartmentDetails(), FAILED to delete the new create departmentDetail1 with Description={} ",
                    savedDepartmentDetail.getId(),
                    savedDepartmentDetail.getSize(),
                    savedDepartmentDetail.getRevenue(),
                    savedDepartmentDetail.getDescription());
    }

    @Test
    public void testSaveAndThenDeleteDepartmentDetails() {
        DepartmentDetail departmentDetail1 = createDepartmentDetailEntity(1111, 200000, "Department Detail Description test");
        DepartmentDetail savedDepartmentDetail = departmentDetailDao.save(departmentDetail1);
        assertNotNull(savedDepartmentDetail.getId(), "A saved DepartmentDetail should have a ID with NULL value");
        assertEquals(departmentDetail1.getDescription(), savedDepartmentDetail.getDescription(), "The description value of savedDepartmentDetail should be the same");
        logger.info("===== DepartmentDetail = {}", savedDepartmentDetail);
        logger.info("===== Saved DepartmentDetail = {}", savedDepartmentDetail);
        boolean deleteResult = departmentDetailDao.delete(savedDepartmentDetail);
        if (deleteResult)
            logger.info("===== testSaveDepartmentDetails(), the new create departmentDetail1 with Description={} is deleted",
                    savedDepartmentDetail.getDescription());
        else
            logger.info("===== testSaveDepartmentDetails(), FAILED to delete the new create departmentDetail1 with Description={} ",
                    savedDepartmentDetail.getDescription());
    }

}
