package com.ascendingdc.training.project2020.daoimpl.hibernate;

import com.ascendingdc.training.project2020.dao.hibernate.DepartmentDao;
import com.ascendingdc.training.project2020.entity.Account;
import com.ascendingdc.training.project2020.entity.Department;
import com.ascendingdc.training.project2020.entity.Employee;
import com.ascendingdc.training.project2020.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentDaoHibernateImpl implements DepartmentDao {
    private Logger logger = LoggerFactory.getLogger(DepartmentDaoHibernateImpl.class);

    @Override
    public Department save(Department department) {
        Transaction transaction = null;
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
        Session session = HibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
//            session.save(department);
            session.saveOrUpdate(department);
            transaction.commit();
            session.close();
 //           return department;
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            logger.error("fail to insert record, error={}", e.getMessage());
            session.close();
        }
        return department;
    }

    @Override
    public Department update(Department department) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.update(department);
            transaction.commit();
            session.close();
            return department;
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            logger.error("fail to update department, error={}", e.getMessage());
            session.close();
        }
        return null;
    }

    @Override
    public boolean deleteByName(String deptName) {
        String hql = "DELETE Department where name = :deptName1";
        int deletedCount = 0;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            //Query<Department> query = session.createQuery(hql);
            //query.setParameter("deptName1", deptName);
            //deletedCount = query.executeUpdate();
            Department dept = getDepartmentByName(deptName);
            session.delete(dept);
            transaction.commit();
            session.close();
            deletedCount = 1;
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The department %s was deleted", deptName));

        return deletedCount >= 1 ? true : false;

    }

    @Override
    public boolean delete(Department dept) {
        String hql = "DELETE Department as dept where dept.id = :Id";
        int deleteCount = 0;
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
//            Query<Department> query = session.createQuery(hql);
//            query.setParameter("Id", dept.getId());
//            deleteCount = query.executeUpdate();
//            Department departmentToBeDeleted = getDepartmentByName(dept.getName());
            session.delete(dept);
            transaction.commit();
            session.close();
            deleteCount = 1;
        } catch(Exception e) {
            if(transaction != null)
                transaction.rollback();
            logger.error("Delete fails, e={}", e.getMessage());
            session.close();;
        }
        return (deleteCount > 0) ? true : false;

    }

    @Override
    public List<Department> getDepartments() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
//        String hql = "select distinct dept FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts";
///        String hql = "From Department";
//        String hql = "select distinct dept From Department as dept left join fetch dept.employees";
//        String hql = "select distinct dept From Department as dept left join fetch dept.departmentDetail left join fetch dept.employees as em left join fetch em.accounts";
        String hql = "select distinct dept From Department as dept left join fetch dept.employees as em left join fetch em.accounts";
//        String hql = "FROM Department as dept left join fetch dept.employees as em left join " +
//                "fetch em.accounts where lower(dept.name) = :name";
        Query<Department> query = session.createQuery(hql, Department.class);
        List<Department> departments = query.list();
//        for(Department department : departments) {
//            department.getEmployees();
//        }
        session.close();
        return departments;
//        return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<Department> query = session.createQuery(hql);
//            //return query.list();
//            //return query.list().stream().distinct().collect(Collectors.toList());
//            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//        }


    }

    @Override
    public List<Department> getDepartmentsWithoutJoinFetch() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
//        String hql = "FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts";
        String hql = "From Department";
//        String hql = "select distinct dept From Department as dept left join fetch dept.employees";
//        String hql = "select distinct dept From Department as dept left join fetch dept.departmentDetail left join fetch dept.employees as em left join fetch em.accounts";
        Query query = session.createQuery(hql);
        List<Department> departments = query.list();
        session.close();
        return departments;


    }

    @Override
    public Department getDepartmentById(Long id) {
        if (id == null) return null;
        String hql = "FROM Department as dept where dept.id = :id";
        //String hql = "FROM Department as dept where lower(dept.name) = :name";

//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        try (Session session = HibernateUtil.getSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("id", id);

            return query.uniqueResult();
        }
    }

    public List<Department> getDepts() {
        String hql = "FROM Department";
        //String hql = "FROM Department as dept where lower(dept.name) = :name";

//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        try (Session session = HibernateUtil.getSession()) {
            Query<Department> query = session.createQuery(hql);

            return query.list();
        }

    }

    @Override
    public List<Department> getDepartmentsWithChildren() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        String hql = "FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts";
//        String hql = "From Department";
//        String hql = "select distinct dept From Department as dept left join fetch dept.employees";
        Query query = session.createQuery(hql);
        List<Department> departments = query.list();
        session.close();
        return departments;
    }

    @Override
    public Department getDepartmentByName(String deptName) {

        if (deptName == null) return null;
        String hql = "FROM Department as dept left join fetch dept.employees as em left join " +
                "fetch em.accounts where lower(dept.name) = :name";
        //String hql = "FROM Department as dept where lower(dept.name) = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            return query.uniqueResult();
        }
    }

    @Override
    public Department getDepartmentEagerByDeptId(Long id) {
        if (id == null) return null;
        String hql = "FROM Department as dept left join fetch dept.employees as em left join " +
                "fetch em.accounts where dept.id = :id";
        //String hql = "FROM Department as dept where lower(dept.name) = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("id", id);

            return query.uniqueResult();
        }
    }

    @Override
    public List<Object[]> getDepartmentAndEmployeesByDeptName(String deptName) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
//        String hql = "FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts";
//        String hql = "From Department";
        String hql = "select distinct dept From Department as dept left join fetch dept.employees where dept.name=:name";
        Query query = session.createQuery(hql);
        query.setParameter("name", deptName.toLowerCase());
        List<Object[]> resultList = query.list();

        for (Object[] obj : resultList) {
            logger.debug(((Department)obj[0]).toString());
            logger.debug(((Employee)obj[1]).toString());
        }

        return resultList;
    }

    @Override
    public List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName) {
        if (deptName == null) return null;

        String hql = "FROM Department as dept " +
                "left join dept.employees as ems " +
                "left join ems.accounts as acnts " +
                "where lower(dept.name) = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            List<Object[]> resultList = query.list();

            for (Object[] obj : resultList) {
                logger.debug(((Department) obj[0]).toString());
                logger.debug(((Employee) obj[1]).toString());
                logger.debug(((Account) obj[2]).toString());
            }

            return resultList;
        }
    }

    @Override
    public List<Object[]> getDepartmentAndEmployees(String deptName) {
        if (deptName == null) return null;

        String hql = "FROM Department as dept left join dept.employees where lower(dept.name) = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            List<Object[]> resultList = query.list();

            for (Object[] obj : resultList) {
                logger.debug(((Department)obj[0]).toString());
                logger.debug(((Employee)obj[1]).toString());
            }

            return resultList;
        }
    }
}
