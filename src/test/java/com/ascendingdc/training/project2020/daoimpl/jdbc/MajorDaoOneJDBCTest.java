package com.ascendingdc.training.project2020.daoimpl.jdbc;

import com.ascendingdc.training.project2020.dao.jdbc.MajorDao;
import com.ascendingdc.training.project2020.dto.MajorDto;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MajorDaoOneJDBCTest {

    private static final Logger logger = LoggerFactory.getLogger(MajorDaoOneJDBCTest.class);

    private MajorDao majorDao;

    private String majorName;

    @BeforeAll
    public static void setupAll() {

    }

    @AfterAll
    public static void teardownAll() {

    }

    @BeforeEach
    public void setupEach() {
        majorDao = new MajorDaoJDBCImpl();
        majorName = "Biology100";
    }

    @AfterEach
    public void teardownEach() {
        majorDao.deleteByName(majorName);
        majorDao = null;
    }

    @Test
    public void testFindAllMajors() {
        List<MajorDto> majorList = majorDao.getMajors();
        assertNotNull(majorList, "the original major list should not be null");
        assertEquals(7, majorList.size(), "the original major list size should be 7");
    }

    @Test
    public void testSaveMajor() {
    //    String majorName = "Biology";
        MajorDto newMajor = createMajor(majorName);
        MajorDto savedMajor = majorDao.save(newMajor);
        assertNotNull(savedMajor.getId());
        assertEquals(majorName, savedMajor.getName());
        /*
         * Now clean up the saved Major from DB Major table
         */
//        boolean deleteSuccessfulFlag = majorDao.delete(savedMajor);
//        assertEquals(true, deleteSuccessfulFlag);
    }

    private MajorDto createMajor(String name) {
        MajorDto major = new MajorDto();
        major.setName(name);
        major.setDescription(name + "--description");
        return major;
    }

}
