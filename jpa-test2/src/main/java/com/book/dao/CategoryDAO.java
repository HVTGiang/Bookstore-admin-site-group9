package com.book.dao;

import com.book.admin.business.CategoryBS;
import com.book.entity.Category;
import com.book.util.HibernateUtility;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryDAO {

    /*Hàm lấy dữ liệu từ database
     * Đầu vào: không
     * Đầu ra: một list các thực thể hiện có trong database, null nếu không có dữ liệu
     * */
    public static List<Category> getAll() {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();

        List<Category> categories = null;
        try {
            // Create query
            Query query = session.createQuery("from Category", Category.class);

            // Return result List
            categories = query.getResultList();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return categories;
    }

    /*Hàm thêm dữ liệu
     * Đầu vào: một thực thể được tạo ở đâu đó ở các tầng trên
     * Đầu ra: một giá trị bool cho biết kết quả của việc thêm, true - thành công, false - thất bại
     * */
    public static Boolean save(CategoryBS category) {
        Boolean flag = false;
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        try {
            if (category != null) {
                // Bắt đầu transaction
                transaction.begin();

                // Thêm dữ liệu vào bảng
                session.persist(category);
                // Đánh dấu là thành công
                flag = true;

                // Commit transaction
                transaction.commit();
            } else {

            }
        } catch (RuntimeException e) {
            System.err.println(e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flag;
    }

    /*Hàm cập nhật dữ liệu
     * Đầu vào: một thực thể được tạo ở đâu đó ở các tầng trên
     * Đầu ra: một giá trị bool cho biết kết quả của việc cập nhật, true - thành công, false - thất bại
     * */
    public static Boolean update(Category category) {
        Boolean flag = false;
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        try {
            if (category != null) {
                // Bắt đầu transaction
                transaction.begin();

                // Cập nhật dữ liệu vào bảng
                session.update(category);
                // Đánh dấu là thành công
                flag = true;

                // Commit transaction
                transaction.commit();
            } else {

            }
        } catch (RuntimeException e) {
            System.err.println(e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flag;
    }

    /*Hàm tìm dữ liệu theo tên
     * Đầu vào: chuỗi chứa tên cần tìm
     * Đầu ra: một cái thực thể tìm được, là null nếu không tìm thấy
     * */
    public static List<Category> findByName(String name) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Category> categories = null;
        try {
            // Create query string
            String queryString = "from Category where name like :name";

            // Create query
            Query query = session.createQuery(queryString, Category.class);
            query.setParameter("name", "%" + name + "%");

            // Return result List
            categories = query.list();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return categories;
    }

    /*Hàm tìm dữ liệu theo id
     * Đầu vào: một số nguyên là id của dòng cần tìm
     * Đầu ra: một cái thực thể tìm được, là null nếu không tìm thấy
     * */
    public static Category findById(int id) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        Category category = null;
        try {
            if (id != 0) {
                // Return result List
                category = session.load(Category.class, id);
            }
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return category;
    }

    /*Hàm xóa (tạm) dữ liệu
     * Đầu vào: một thực thể được tạo ở đâu đó ở các tầng trên
     * Đầu ra: một giá trị bool cho biết kết quả của việc xóa, true - thành công, false - thất bại
     * */
    public static Boolean delete(Category category) {
        Boolean flag = false;
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction transaction = session.getTransaction();
        try {
            if (category != null) {
                // Bắt đầu transaction
                transaction.begin();

                // Cập nhật dữ liệu vào bảng
                // Chỗ này là cập nhật cái field active của thực thể bằng 1 nè
                session.update(category);

                // Đánh dấu là thành công
                flag = true;

                // Commit transaction
                transaction.commit();
            } else {

            }
        } catch (RuntimeException e) {
            System.err.println(e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return flag;
    }
}