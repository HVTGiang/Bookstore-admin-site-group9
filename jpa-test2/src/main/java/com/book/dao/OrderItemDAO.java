package com.book.dao;

import com.book.entity.Orderitem;
import com.book.util.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderItemDAO {
    public static void save(Orderitem orderItem){
        Session session = HibernateUtility.getSessionFactory().openSession();
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

    public static List<Orderitem> orderItemList(int id){
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Orderitem> orderItems =  null;
        try {
            // Create query
            final String sqlString = "select o from Orderitem o where o.idOrder = :id";

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

    public static List<Orderitem> orderItemListByProduct(int id){
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Orderitem> orderItems =  null;
        try {
            // Create query
            final String sqlString = "select o from Orderitem o where o.idProduct = :id";

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

    public static List<Orderitem> getAll() {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Orderitem> orderitemEntities = null;
        try {
            // Create query
            final String sqlString = " select ot from Orderitem ot";
            Query query = session.createQuery(sqlString);
            orderitemEntities = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderitemEntities;
    }
}
