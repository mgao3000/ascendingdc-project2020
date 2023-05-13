package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.UserDao;
import com.ascendingdc.training.project2020.entity.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserDaoSpringDataJPATest {
    private Logger logger = LoggerFactory.getLogger(UserDaoSpringDataJPATest.class);

    @Autowired
    @Qualifier("userSpringDataJPADao")
    private UserDao userDao;


    @BeforeEach
    public void setupEach() {

    }

    @AfterEach
    public void teardownEach() {

    }

    @Test
    public void testFindAllUsers() {
        List<User> userList = userDao.findAllUsers();
        displayUserList(userList);
    }

    private void displayUserList(List<User> userList) {
        logger.info("===== the total number of retrieved Users = {}", userList.size());
        int index = 1;
        for(User user : userList) {
            logger.info("=== user No.{}, user={}", index++, user);
        }
    }

}
