package com.book.dao;

import com.book.entity.Paymethod;
import com.book.util.HibernateUtility;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PaymentDAO {
    /*Hàm lấy dữ liệu từ database
     * Đầu vào: không
     * Đầu ra: một list các thực thể hiện có trong database, null nếu không có dữ liệu
     * */
    public static List<Paymethod> getAll() {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();

        List<Paymethod> categories = null;
        try {
            // Create query
            Query query = session.createQuery("from Paymethod ", Paymethod.class);

            // Return result List
            categories = query.getResultList();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return categories;
    }

    public static void save(Paymethod paymethod) {
        Session session = HibernateUtility.getSessionFactory().openSession();
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

    public static List<Paymethod> find(String name) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Paymethod> paymethods = null;
        try {
            // Create query string
            String queryString = "from Paymethod where name like :name";

            // Create query
            Query query = session.createQuery(queryString, Paymethod.class);
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

    public static Paymethod find(int id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Paymethod paymethod = session.load(Paymethod.class,id);
        return paymethod;
    }
}
