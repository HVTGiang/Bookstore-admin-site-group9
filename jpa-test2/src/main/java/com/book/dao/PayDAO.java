package com.book.dao;

import com.book.business.Category;
import com.book.entity.CategoryEntity;
import com.book.entity.PaymethodEntity;
import com.book.entity.ProductEntity;
import com.book.util.HibernateUtil;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PayDAO {
    /*Hàm lấy dữ liệu từ database
     * Đầu vào: không
     * Đầu ra: một list các thực thể hiện có trong database, null nếu không có dữ liệu
     * */
    public static List<PaymethodEntity> getAll() {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<PaymethodEntity> categories = null;
        try {
            // Create query
            Query query = session.createQuery("from PaymethodEntity ", PaymethodEntity.class);

            // Return result List
            categories = query.getResultList();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return categories;
    }

    public static void save(PaymethodEntity paymethod) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        try {
            if (paymethod != null) {
                // Bắt đầu transaction
                transaction.begin();

                // Thêm dữ liệu vào bảng
                session.save(paymethod);
                // Đánh dấu là thành công

                // Commit transaction
                transaction.commit();
            }
        } catch (RuntimeException e) {
            System.err.println(e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    public static List<PaymethodEntity> find(String name) {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PaymethodEntity> paymethods = null;
        try {
            // Create query string
            String queryString = "from PaymethodEntity where name like :name";

            // Create query
            Query query = session.createQuery(queryString, PaymethodEntity.class);
            query.setParameter("name", name);

            // Return result List
            paymethods = query.list();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return paymethods;
    }

    public static PaymethodEntity find(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        PaymethodEntity paymethod = session.load(PaymethodEntity.class,id);
        return paymethod;
    }
}
