package com.book.dao;

import com.book.entity.User;
import com.book.util.HibernateUtility;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    /*Hàm lấy dữ liệu từ database
     * Đầu vào: không
     * Đầu ra: một list các thực thể hiện có trong database, null nếu không có dữ liệu
     * */
    public static List<User> getAll() {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<User> userList = new ArrayList<>();
        try {
            // Create query
            Query query = session.createQuery("from User where active = true", User.class);

            // Query
            userList = query.list();

        } catch (HibernateError error) {
            System.err.println(error);
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userList;
    }

    /*Hàm thêm dữ liệu
     * Đầu vào: một thực thể được tạo ở đâu đó ở các tầng trên
     * Đầu ra: một giá trị bool cho biết kết quả của việc thêm, true - thành công, false - thất bại
     * */
    public static Boolean save(User user) {
        Boolean flag = false;
        Session session = HibernateUtility.getSessionFactory().openSession();
        try {
            if (user != null) {
                // Bắt đầu transaction
                session.beginTransaction();

                // Thêm dữ liệu vào bảng
                session.persist(user);
                // Đánh dấu là thành công
                flag = true;

                // Commit transaction
                session.getTransaction().commit();
            } else {

            }
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            System.err.println(e);
            flag = false;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    /*Hàm cập nhật dữ liệu
     * Đầu vào: một thực thể được tạo ở đâu đó ở các tầng trên
     * Đầu ra: một giá trị bool cho biết kết quả của việc cập nhật, true - thành công, false - thất bại
     * */
    public static Boolean update(User user) {
        Boolean flag = false;
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        try {
            if (user != null) {
                // Bắt đầu transaction
                transaction.begin();

                // Cập nhật dữ liệu vào bảng
                session.update(user);
                // Đánh dấu là thành công
                flag = true;

                // Commit transaction
                transaction.commit();
            } else {

            }
        } catch (RuntimeException e) {
            System.err.println(e);
            if (transaction != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    public static User find(int id) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        User user = null;
        try {
            if (id != 0) {
                // Return result List
                user = session.load(User.class, id);
            }
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return user;
    }

    /*Hàm tìm dữ liệu theo tên
     * Đầu vào: chuỗi chứa tên cần tìm
     * Đầu ra: một cái thực thể tìm được, là null nếu không tìm thấy
     * */
    public static List<User> searchByName(String name) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<User> users = null;
        try {
            // Create query string
            String queryString = "from User where name like :name and active=true";

            // Create query
            Query query = session.createQuery(queryString, User.class);
            query.setParameter("name", "%" + name + "%");

            // Return result List
            users = query.list();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return users;
    }

    public static List<User> findByRole(int id) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<User> users = null;
        try {
            // Create query string
            String queryString = "from User where isRole = :role and active = true";

            // Create query
            Query query = session.createQuery(queryString, User.class);
            query.setParameter("role", id);

            // Return result List
            users = query.list();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return users;
    }

    public static List<User> findByPhone(String phone) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<User> users = null;
        try {
            // Create query string
            String queryString = "from User where phone like :phone and active=true";

            // Create query
            Query query = session.createQuery(queryString, User.class);
            query.setParameter("phone", phone);

            // Return result List
            users = query.list();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return users;
    }

    public static List<User> findByEmail(String email) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<User> users = null;
        try {
            // Create query string
            String queryString = "from User where email like :email and active=true";

            // Create query
            Query query = session.createQuery(queryString, User.class);
            query.setParameter("email", email);

            // Return result List
            users = query.list();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return users;
    }
}
