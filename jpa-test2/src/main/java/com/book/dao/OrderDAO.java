package com.book.dao;

import com.book.entity.Order;
import com.book.util.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO {
    public static List<Order> getAll() {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Order> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from Order ct";
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

    public static void save(Order order) {
        Session session = HibernateUtility.getSessionFactory().openSession();
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

    public static List<Order> findByStatus(int status) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Order> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from Order ct where status = :status";
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

    public static Order getOrderByIdOrder(int id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Order order = session.load(Order.class,id);
        session.close();
        return order;
    }

    public static void update(Order order) {
        Session session = HibernateUtility.getSessionFactory().openSession();
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

    public static List<Order> getOrderByIdCustomer(int customerID) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Order> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from Order ct where idUser = :id";
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

    public static List<Order> getOrderByIdEmployee(int employeeID) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Order> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from Order ct where idSeller = :id";
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

    public static List<Order> get5LastestOrder(){
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Order> orderEntities = null;

//        int num = session.createQuery("select count(id) from Product p where active = 1");

        try{
            final String sqlString = "Select p from Order p order by p.id desc";
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

    public static List<Order> getOrderByIdDelivery(int deliveryID) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Order> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from Order ct where idDelivery = :id";
            Query query = session.createQuery(sqlString);
            query.setParameter("id", deliveryID);
            orders = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }

    public static List<Order> getOrderByIdMethod(int payMethodID) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Order> orders = null;
        try {
            // Create query
            final String sqlString = "select ct from Order ct where idMethod = :id";
            Query query = session.createQuery(sqlString);
            query.setParameter("id", payMethodID);
            orders = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orders;
    }
}
