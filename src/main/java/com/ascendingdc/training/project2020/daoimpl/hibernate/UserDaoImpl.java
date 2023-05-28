/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.daoimpl.hibernate;

import com.ascendingdc.training.project2020.dao.hibernate.UserDao;
import com.ascendingdc.training.project2020.entity.Role;
import com.ascendingdc.training.project2020.entity.User;
import com.ascendingdc.training.project2020.exception.UserNotFoundException;
import com.ascendingdc.training.project2020.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository("userDaoHibernateImpl")
public class UserDaoImpl implements UserDao {
    private Logger logger=LoggerFactory.getLogger(getClass());

    @Override
    public User save(User user) {
        Transaction transaction = null;
        boolean isSuccess = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }
        catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error("error saving User record",e);
        }finally {
            session.close();
        }

        if (isSuccess) logger.debug(String.format("The user %s was inserted into the table.", user.toString()));

        return user;
    }

    @Override
    public User getUserById(Long Id) {
//        String hql = "FROM User as u where u.id = :id";
        String hql = "FROM User as u join fetch u.roles where u.id = :id";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("id", Id);
            return query.uniqueResult();
        }
    }


    @Override
    public User getUserByEmail(String email) {
//        String hql = "FROM User as u where lower(u.email) = :email";
        String hql = "FROM User as u join fetch u.roles where lower(u.email) = :email";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase());

            return query.uniqueResult();
        }
    }

    @Override
    public User getUserByCredentials(String email, String password) throws UserNotFoundException {
//        String hql = "FROM User as u where (lower(u.email) = :email or lower(u.name) =:email) and u.password = :password";
//        String hql = "FROM User as u join fetch u.roles where (lower(u.email) = :email or lower(u.name) =:email) and u.password = :password";
        String hql = "FROM User as u join fetch u.roles where lower(u.email) = :email and u.password = :password";
        logger.debug(String.format("User email: %s, password: %s", email, password));

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase().trim());
            query.setParameter("password", password);

            return query.uniqueResult();
        }
        catch (Exception e){
            logger.error("error: {}", e.getMessage());
            throw new UserNotFoundException("can't find user record with email="+email + ", password="+password);
        }
    }


    @Override
    public User addRole(User user, Role role) {
        Set<Role> roleSet;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = s.beginTransaction();
        try {
            roleSet = user.getRoles();
            roleSet.add(role);
            user.setRoles(roleSet);
            s.saveOrUpdate(user);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("session exception, try again");
            if(transaction != null)
                transaction.rollback();
        } finally {
            s.close();
        }
        return user;
    }

    @Override
    public boolean delete(User user) {
        String hql = "DELETE FROM User as u where u.id=:Id";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        boolean deleteResult = false;
        try {
            Query<User> query = session.createQuery(hql);
            query.setParameter("Id", user.getId());
            int deleteResultIntValue = query.executeUpdate();
            transaction.commit();
            if(deleteResultIntValue > 0)
                deleteResult = true;
        }catch (HibernateException he){
            if(transaction != null)
                transaction.rollback();
            logger.error("error",he);
        } finally{
            session.close();
        }
        return deleteResult;
    }

    @Override
    public List<User> findAllUsers() {
//        String hql = "FROM User";
        String hql = "select distinct user FROM User as user left join fetch user.roles";
        List<User> userList = new ArrayList<User>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql);
            userList = query.list();
        }
        return userList;
    }

    @Override
    public User getUserByName(String username) {
//        String hql = "FROM User as u where u.name = :username";   //roles will be selected if FetchType is EAGER
        String hql = "FROM User as u join fetch u.roles where u.name = :username";
        User user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("username", username);
            user = query.uniqueResult();
        }
        return user;
    }
}
