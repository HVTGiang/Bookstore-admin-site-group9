package com.book.dao;

import com.book.business.Category;
import com.book.entity.CategoryEntity;
import com.book.entity.DeliveryEntity;
import com.book.entity.PaymethodEntity;
import com.book.entity.ProductEntity;
import com.book.util.HibernateUtil;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DeliveryDAO {

    /*Hàm lấy dữ liệu từ database
     * Đầu vào: không
     * Đầu ra: một list các thực thể hiện có trong database, null nếu không có dữ liệu
     * */
    public static List<DeliveryEntity> getAll() {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<DeliveryEntity> deliveries = null;
        try {
            // Create query
            Query query = session.createQuery("from DeliveryEntity", DeliveryEntity.class);

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
    public static void save(DeliveryEntity delivery) {
        Session session = HibernateUtil.getSessionFactory().openSession();
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

    public static DeliveryEntity find(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        DeliveryEntity delivery = session.load(DeliveryEntity.class,id);
        return delivery;
    }

    public static List<DeliveryEntity> find(String name) {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<DeliveryEntity> deliveries = null;
        try {
            // Create query string
            String queryString = "from DeliveryEntity where name like :name";

            // Create query
            Query query = session.createQuery(queryString, DeliveryEntity.class);
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
