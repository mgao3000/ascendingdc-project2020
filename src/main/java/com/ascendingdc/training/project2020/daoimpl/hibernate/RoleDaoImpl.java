/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.daoimpl.hibernate;

import com.ascendingdc.training.project2020.dao.hibernate.RoleDao;
import com.ascendingdc.training.project2020.entity.Role;
import com.ascendingdc.training.project2020.entity.User;
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

@Repository("roleDaoHibernateImpl")
public class RoleDaoImpl implements RoleDao {
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public Role getRoleByName(String name) {
        String hql = "FROM Role as r where lower(r.name) = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Role> query = session.createQuery(hql);
            query.setParameter("name", name.toLowerCase());

            return query.uniqueResult();
        }
    }

    @Override
    public Role save(Role role) {
        Transaction transaction = null;
        boolean isSuccess = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(role);
            transaction.commit();
        }
        catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error("error saving Role record",e);
        }finally {
            session.close();
        }
        return role;
    }

    @Override
    public boolean delete(Role role) {
        String hql = "DELETE FROM Role as role where role.id=:Id";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        boolean deleteResult = false;
        try {
            Query<User> query = session.createQuery(hql);
            query.setParameter("Id", role.getId());
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
    public List<Role> findAllRoles() {
        String hql = "FROM Role as role join fetch role.users";
        List<Role> roleList = new ArrayList<Role>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Role> query = session.createQuery(hql);
            roleList = query.list();
        }
        return roleList;
    }
}
