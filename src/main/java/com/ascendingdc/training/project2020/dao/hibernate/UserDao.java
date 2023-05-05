package com.ascendingdc.training.project2020.dao.hibernate;

import com.ascendingdc.training.project2020.entity.Role;
import com.ascendingdc.training.project2020.entity.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    User getUserByEmail(String email);
    User getUserById(Long Id);
    User getUserByCredentials(String email, String password) throws Exception;
    User addRole(User user, Role role);
    int delete(User u);
    List<User> findAllUsers();
    User getUserByName(String username);
}
