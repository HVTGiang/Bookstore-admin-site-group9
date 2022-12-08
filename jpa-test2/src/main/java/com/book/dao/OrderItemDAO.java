package com.book.dao;

import com.book.entity.OrderEntity;
import com.book.entity.OrderitemEntity;
import com.book.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderItemDAO {
    public static void save(OrderitemEntity orderItem){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            session.save(orderItem);
            session.getTransaction().commit();
        }
        catch (RuntimeException e){
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    public static List<OrderitemEntity> orderItemList(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderitemEntity> orderItems =  null;
        try {
            // Create query
            final String sqlString = "select o from OrderitemEntity o where o.idOrder = :id";

            Query query = session.createQuery(sqlString);
            query.setParameter("id", id);
            orderItems = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderItems;
    }

    public static List<OrderitemEntity> orderItemListByProduct(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderitemEntity> orderItems =  null;
        try {
            // Create query
            final String sqlString = "select o from OrderitemEntity o where o.idProduct = :id";

            Query query = session.createQuery(sqlString);
            query.setParameter("id", id);
            orderItems = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderItems;
    }

    public static List<OrderitemEntity> getAll() {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<OrderitemEntity> orderitemEntities = null;
        try {
            // Create query
            final String sqlString = "select ct from OrderitemEntity ct";
            Query query = session.createQuery(sqlString);
            orderitemEntities = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return orderitemEntities;
    }
}
