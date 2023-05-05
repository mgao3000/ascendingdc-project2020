package com.ascendingdc.training.project2020.controller;

//import com.ascendingdc.training.project2020.entity.Role;
//import com.ascendingdc.training.project2020.entity.User;
import com.ascendingdc.training.project2020.dto.RoleDto;
import com.ascendingdc.training.project2020.dto.UserDto;
import com.ascendingdc.training.project2020.service.RoleService;
import com.ascendingdc.training.project2020.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserRoleController {
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

//    @GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<User>> findAllUsers() {
//        List<User> userList = userService.getAllUsers();
//
//        displayUsers(userList);
//
//        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
//    }

    @GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> findAllUsers() {
        List<UserDto> userList = userService.getAllUsers();

        displayUsers(userList);

        return userList;
    }

//    @GetMapping(value="/users/{email}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public User findUserByEmailAndName(@PathVariable("email") String email, @PathVariable("name") String name) {
//        User user = userService.getUserByCredentials(email, )
//
//        displayUser(1, user);
//
//        return user;
//    }

    @GetMapping(value="/users/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto findUserByEmail(@PathVariable("email") String email) {
        UserDto userDto = userService.getUserByEmail(email);

        displayUser(1, userDto);

        return userDto;
    }

    @PostMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.save(userDto);
        return savedUserDto;
    }

    @PutMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateUser(@RequestBody UserDto userDto) {
        UserDto updatedUserDto = userService.save(userDto);
        return updatedUserDto;
    }

    @GetMapping(value="/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDto> finsAllRoles() {
        return roleService.getAllRoles();
    }

    private void displayUsers(List<UserDto> userDtoList) {
        int index = 1;
        for(UserDto UserDto : userDtoList) {
            displayUser(index++, UserDto);
        }
    }

    private void displayUser(int i, UserDto userDto) {
        logger.info("======= User No.{} ========", i);
        logger.info("\t user.id={}", userDto.getId());
        logger.info("\t user.name={}", userDto.getName());
        logger.info("\t user.firstName={}", userDto.getFirstName());
        logger.info("\t user.lastName={}", userDto.getLastName());
        logger.info("\t user.email={}", userDto.getEmail());
        logger.info("\t user.secretKey={}", userDto.getSecretKey());
        Set<RoleDto> roleDtoSet = userDto.getRoleDtoSet();
        displayRoles(roleDtoSet);
    }

    private void displayRoles(Set<RoleDto> roleDtoSet) {
        int index = 1;
        for(RoleDto roleDto : roleDtoSet) {
            displayRole(index++, roleDto);
        }
    }

    private void displayRole(int i, RoleDto roleDto) {
        logger.info("\t######## Role No.{} ##########", i);
        logger.info("\t\t roleDto.id={}", roleDto.getId());
        logger.info("\t\t roleDto.name={}", roleDto.getName());
        logger.info("\t\t roleDto.allAllowedResource={}", roleDto.getAllowedResource());
    }

}
