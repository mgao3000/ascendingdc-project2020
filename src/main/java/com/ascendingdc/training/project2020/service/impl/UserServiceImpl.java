/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dao.hibernate.UserDao;
import com.ascendingdc.training.project2020.dto.UserDto;
import com.ascendingdc.training.project2020.entity.Role;
import com.ascendingdc.training.project2020.entity.User;
import com.ascendingdc.training.project2020.service.UserService;
import com.ascendingdc.training.project2020.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    public UserDto save(UserDto userDto) {
        User user = DtoAndEntityConvertUtil.convertUserDtoToUser(userDto);
        User saveduser =  userDao.save(user);
        UserDto saveduserDto = DtoAndEntityConvertUtil.convertUserToUserDto(saveduser);
        return saveduserDto;
    }

    public UserDto getUserByEmail(String email) {
        User user = userDao.getUserByEmail(email);
        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        return userDto;
    }

    public UserDto getUserById(Long userid) {
        User user = userDao.getUserById(userid);
        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        return userDto;
    }

    public UserDto getUserByCredentials(String email, String password) {
        UserDto userDto = null;
        try {
            User user = userDao.getUserByCredentials(email, password);
            userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDto;
    }

    public List<UserDto> getAllUsers() {
        List<User> userList = userDao.findAllUsers();
        List<UserDto> userDtoList = getUserDtoListByUserList(userList);
        return userDtoList;
    }

    private List<UserDto> getUserDtoListByUserList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userList) {
            UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    private void displayUsers(List<User> userList) {
        int index = 1;
        for(User user : userList) {
            displayUser(index++, user);
        }
    }

    private void displayUser(int i, User user) {
        logger.info("======= User No.{} ========", i);
        logger.info("\t user.id={}", user.getId());
        logger.info("\t user.name={}", user.getName());
        logger.info("\t user.firstName={}", user.getFirstName());
        logger.info("\t user.lastName={}", user.getLastName());
        logger.info("\t user.email={}", user.getEmail());
        logger.info("\t user.secretKey={}", user.getSecretKey());
        Set<Role> roleList = user.getRoles();
        displayRoles(roleList);
    }

    private void displayRoles(Set<Role> roleList) {
        int index = 1;
        for(Role role : roleList) {
            displayRole(index++, role);
        }
    }

    private void displayRole(int i, Role role) {
        logger.info("\t######## Role No.{} ##########", i);
        logger.info("\t\t role.id={}", role.getId());
        logger.info("\t\t role..name={}", role.getName());
        logger.info("\t\t role.allAllowedResource={}", role.getAllowedResource());
    }
}
