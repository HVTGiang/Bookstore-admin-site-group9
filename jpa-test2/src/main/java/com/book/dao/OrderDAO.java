package com.book.dao;

import com.book.entity.OrderEntity;
import com.book.entity.PaymethodEntity;
import com.book.entity.ProductEntity;
import com.book.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO {
    public static List<OrderEntity> getAll() {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderEntity> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from OrderEntity ct";
            Query query = session.createQuery(sqlString);
            orders = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return orders;
    }

    public static void save(OrderEntity order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static List<OrderEntity> findByStatus(int status) {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderEntity> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from OrderEntity ct where status = :status";
            Query query = session.createQuery(sqlString);
            query.setParameter("status", status);
            orders = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }

    public static OrderEntity find(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        OrderEntity order = session.load(OrderEntity.class,id);
        session.close();
        return order;
    }

    public static void update(OrderEntity order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }

    public static List<OrderEntity> findByCustomer(int customerID) {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderEntity> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from OrderEntity ct where idUser = :id";
            Query query = session.createQuery(sqlString);
            query.setParameter("id", customerID);
            orders = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }

    public static List<OrderEntity> findByEmployee(int employeeID) {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderEntity> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from OrderEntity ct where idSeller = :id";
            Query query = session.createQuery(sqlString);
            query.setParameter("id", employeeID);
            orders = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }

    public static List<OrderEntity> get5LastestOrder(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderEntity> orderEntities = null;

//        int num = session.createQuery("select count(id) from Product p where active = 1");

        try{
            final String sqlString = "Select p from OrderEntity p order by p.id desc";
            Query query = session.createQuery(sqlString);
            orderEntities = query.setMaxResults(5).list();
        }
        catch(RuntimeException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderEntities;
    }
}
