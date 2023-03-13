package com.ascendingdc.training.project2020.daoimpl.hibernate;

import com.ascendingdc.training.project2020.dao.hibernate.EmployeeDao;
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
public class EmployeeDaoHibernateImpl implements EmployeeDao {
    private Logger logger = LoggerFactory.getLogger(EmployeeDaoHibernateImpl.class);

    @Override
    public Employee save(Employee employee, Department dept) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            dept.addEmployee(employee);
            session.save(employee);
            transaction.commit();
            session.close();
            return employee;
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            logger.error("fail to insert Employee record, error={}", e.getMessage());
            session.close();
        }
        return null;
    }

    @Override
    public Integer updateEmployeeAddressByEmployeeName(String name, String address) {
        String hql = "UPDATE Employee as em set em.address = :address where em.name = :name";
        int updatedCount = 0;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("name", name);
            query.setParameter("address", address);

            transaction = session.beginTransaction();
            updatedCount = query.executeUpdate();
            transaction.commit();
            return updatedCount;
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The employee %s was updated, total updated record(s): %d", name, updatedCount));

        return updatedCount;
    }

    @Override
    public Employee update(Employee employee) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            session.close();
            return employee;
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            logger.error("fail to update Employee record, error={}", e.getMessage());
            session.close();
        }
        return null;
    }

    @Override
    public boolean deleteByName(String name) {
        Transaction transaction = null;
        int deletedCount = 0;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            Employee employee = getEmployeeByName(name);
            session.delete(employee);
            transaction.commit();
            session.close();
            deletedCount = 1;
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            logger.error("fail to delete Employee record, error={}", e.getMessage());
            session.close();
        }
        logger.debug(String.format("The employee %s was deleted", name));

        return deletedCount >= 1 ? true : false;
    }

    @Override
    public boolean delete(Employee employee) {
        Transaction transaction = null;
        int deletedCount = 0;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
            session.close();
            deletedCount = 1;
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
            logger.error("fail to delete Employee record, error={}", e.getMessage());
            session.close();
        }
        logger.debug(String.format("The employee %s was deleted", employee.getName()));

        return deletedCount >= 1 ? true : false;
    }

    @Override
    public List<Employee> getEmployees() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
//        String hql = "FROM Employee as emp left join fetch emp.employeeDetail left join fetch emp.accounts";
//        String hql = "From Employee as emp left join fetch emp.accounts";
        String hql = "From Employee as emp join fetch emp.department left join fetch emp.accounts";
//        String hql = "From Employee as emp left join fetch emp.employeeDetail";
        Query query = session.createQuery(hql);
        List<Employee> employees = query.list();
        session.close();
        return employees;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        if (id == null) return null;
        String hql = "FROM Employee as emp where emp.id = :id";
        //String hql = "FROM Employee as emp where lower(emp.name) = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("id", id);

            return query.uniqueResult();
        }
    }

    @Override
    public Employee getEmployeeByName(String employeeName) {
        if (employeeName == null) return null;
//        String hql = "FROM Employee as emp where lower(emp.name) = :name";
        //String hql = "FROM Employee as emp left join fetch emp.employeeDetail left join fetch emp.accounts where lower(emp.name) = :name";
        String hql = "FROM Employee as emp left join fetch emp.accounts where lower(emp.name) = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("name", employeeName);

            return query.uniqueResult();
        }
    }

    @Override
    public Employee getEmployeeAndDepartmentById(Long id) {
        if (id == null) return null;
        String hql = "FROM Employee as emp left join fetch emp.department where emp.id = :id";
        //String hql = "FROM Employee as emp where lower(emp.name) = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("id", id);

            return query.uniqueResult();
        }
    }
}
