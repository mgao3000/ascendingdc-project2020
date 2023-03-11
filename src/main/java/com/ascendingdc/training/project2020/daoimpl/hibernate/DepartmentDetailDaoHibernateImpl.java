package com.ascendingdc.training.project2020.daoimpl.hibernate;

import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDetailDao;
import com.ascendingdc.training.project2020.entity.DepartmentDetail;
import com.ascendingdc.training.project2020.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DepartmentDetailDaoHibernateImpl implements DepartmentDetailDao {
    private Logger logger = LoggerFactory.getLogger(DepartmentDetailDaoHibernateImpl.class);

    @Override
    public List<DepartmentDetail> getDepartmentDetails() {
        String hql = "From DepartmentDetail as dd left join fetch dd.department";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(hql);
            List<DepartmentDetail> departmentDetails = query.list();
            return departmentDetails;
        }
    }

    @Override
    public DepartmentDetail getDepartmentDetailById(Long id) {
        String hql = "From DepartmentDetail as dd left join fetch dd.department where dd.id=:id";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try(Session session = sessionFactory.openSession()) {
            Query<DepartmentDetail> query = session.createQuery(hql);
            query.setParameter("id", id);
            DepartmentDetail departmentDetail = query.uniqueResult();
            return departmentDetail;
        }
    }

    @Override
    public DepartmentDetail save(DepartmentDetail departmentDetail) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.save(departmentDetail);
            transaction.commit();
            session.close();
            return departmentDetail;
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            logger.error("fail to insert departmentDetail record, error={}", e.getMessage());
            session.close();
        }
        return null;
    }

    @Override
    public boolean delete(DepartmentDetail departmentDetail) {
        Transaction transaction = null;
        int deleteCount = 0;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.delete(departmentDetail);
            transaction.commit();
            session.close();
            deleteCount = 1;
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            logger.error("fail to delete departmentDetail record, error={}", e.getMessage());
            session.close();
        }
        return deleteCount > 0 ? true : false;
    }
}
