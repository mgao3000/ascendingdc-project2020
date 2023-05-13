package com.ascendingdc.training.project2020.daoimpl.repository;

import com.ascendingdc.training.project2020.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ascendingdc.training.project2020.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password) throws UserNotFoundException;

    User findByName(String name);
}
