package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.UserDao;
import com.ascendingdc.training.project2020.daoimpl.repository.UserRepository;
import com.ascendingdc.training.project2020.entity.Role;
import com.ascendingdc.training.project2020.entity.User;
import com.ascendingdc.training.project2020.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("userSpringDataJPADao")
public class UserDaoSpringDataJPAImpl implements UserDao {

    private Logger logger = LoggerFactory.getLogger(UserDaoSpringDataJPAImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent())
            user = optionalUser.get();
        return user;
    }

    @Override
    public User getUserByCredentials(String email, String password) throws UserNotFoundException {
        User user = userRepository.findByEmailAndPassword(email, password);
        if(user == null)
            throw new UserNotFoundException("can't find user record with email="+email + ", password="+password);

        return user;
    }

    @Override
    public User addRole(User user, Role role) {
        user.addRole(role);
        User updateduser = userRepository.save(user);
        return updateduser;
    }

    @Override
    public boolean delete(User user) {
        boolean deleteResult = false;
        try {
            userRepository.delete(user);
            deleteResult = true;
        } catch (IllegalArgumentException iae) {
            logger.error("Delete User failed, input User={}, error={}", user, iae.getMessage());
        }
        return deleteResult;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public User getUserByName(String username) {
        User user = userRepository.findByName(username);
        return user;
    }
}
