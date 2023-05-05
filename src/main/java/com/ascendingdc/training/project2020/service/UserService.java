package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dto.UserDto;
//import com.ascendingdc.training.project2020.entity.User;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto getUserById(Long userid);

    UserDto getUserByCredentials(String email, String password);

    List<UserDto> getAllUsers();


}
