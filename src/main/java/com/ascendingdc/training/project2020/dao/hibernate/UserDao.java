package com.ascendingdc.training.project2020.dao.hibernate;

import com.ascendingdc.training.project2020.entity.Role;
import com.ascendingdc.training.project2020.entity.User;
import com.ascendingdc.training.project2020.exception.UserNotFoundException;

import java.util.List;

public interface UserDao {
    User save(User user);
    User getUserByEmail(String email);
    User getUserById(Long id);
    User getUserByCredentials(String email, String password) throws UserNotFoundException;
    User addRole(User user, Role role);
    boolean delete(User user);
    List<User> findAllUsers();
    User getUserByName(String username);
}
