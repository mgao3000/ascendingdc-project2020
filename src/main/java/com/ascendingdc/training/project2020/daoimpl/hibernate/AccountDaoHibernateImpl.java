/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascendingdc.training.project2020.daoimpl.hibernate;

import com.ascendingdc.training.project2020.dao.hibernate.AccountDao;
import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Employee;
import com.ascendingdc.training.project2020.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoHibernateImpl implements AccountDao {
    private Logger logger=LoggerFactory.getLogger(getClass());

//    @Autowired
//    private SessionFactory sessionFactory;
//    @Autowired
//    private EmployeeDao employeeDao;

    @Override
    public Account save(Account account, Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
//            Employee employee = employeeDao.getEmployeeByName(employeeName);

            if (employee != null) {
                transaction = session.beginTransaction();
//                account.setEmployee(employee);
                employee.addAccount(account);
                session.save(account);
                transaction.commit();
                return account;
            }
            else {
                logger.debug(String.format("The employee [%s] doesn't exist.", employee.getName()));
            }
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Account> getAccounts() {
        String hql = "FROM Account as act left join fetch act.employee";

        try (Session session = HibernateUtil.getSession()) {
            Query<Account> query = session.createQuery(hql);
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }

    public Account getAccountById(Long id) {
        String hql = "FROM Account as act join fetch act.employee where act.id = :id";

        try (Session session = HibernateUtil.getSession()) {
            Query<Account> query = session.createQuery(hql);
            query.setParameter("id", id);

            return query.uniqueResult();
        }
    }
}
