package com.book.dao;

import com.book.entity.Delivery;
import com.book.util.HibernateUtility;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DeliveryDAO {

    /*Hàm lấy dữ liệu từ database
     * Đầu vào: không
     * Đầu ra: một list các thực thể hiện có trong database, null nếu không có dữ liệu
     * */
    public static List<Delivery> getAll() {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();

        List<Delivery> deliveries = null;
        try {
            // Create query
            Query query = session.createQuery("from Delivery", Delivery.class);

            // Return result List
            deliveries = query.getResultList();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return deliveries ;
    }

    /*Hàm thêm dữ liệu
     * Đầu vào: một thực thể được tạo ở đâu đó ở các tầng trên
     * Đầu ra: một giá trị bool cho biết kết quả của việc thêm, true - thành công, false - thất bại
     * */
    public static void save(Delivery delivery) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(delivery);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static Delivery getDeliveryByID(int id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Delivery delivery = session.load(Delivery.class,id);
        return delivery;
    }

    public static List<Delivery> getDeliveryByName(String name) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Delivery> deliveries = null;
        try {
            // Create query string
            String queryString = "from Delivery where name like :name";

            // Create query
            Query query = session.createQuery(queryString, Delivery.class);
            query.setParameter("name", name);

            // Return result List
            deliveries = query.list();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return deliveries;
    }
}
