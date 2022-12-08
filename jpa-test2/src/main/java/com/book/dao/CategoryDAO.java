package com.book.dao;

import com.book.business.Category;
import com.book.entity.CategoryEntity;
import com.book.util.HibernateUtil;
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
    public static List<CategoryEntity> getAll() {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<CategoryEntity> categories = null;
        try {
            // Create query
            Query query = session.createQuery("from CategoryEntity", CategoryEntity.class);

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
    public static Boolean InsertCategory(Category category) {
        Boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
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
    public static Boolean UpdateCategory(Category category) {
        Boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
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
    public static List<CategoryEntity> findCategory(String name) {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<CategoryEntity> categories = null;
        try {
            // Create query string
            String queryString = "from CategoryEntity where name like :name";

            // Create query
            Query query = session.createQuery(queryString, CategoryEntity.class);
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
    public static CategoryEntity findCategory(int id) {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        CategoryEntity category = null;
        try {
            if (id != 0) {
                // Return result List
                category = session.load(CategoryEntity.class, id);
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
    public static Boolean DeleteCategory(CategoryEntity category) {
        Boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
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