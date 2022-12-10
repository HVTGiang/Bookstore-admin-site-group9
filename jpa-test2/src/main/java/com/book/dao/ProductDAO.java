package com.book.dao;

import com.book.entity.Product;
import com.book.util.HibernateUtility;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO {
    public static void save(Product product) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void update(Product product) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void delete(int id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
            }
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static Product getProductByID(int id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Product product = session.load(Product.class, id);
        return product;
    }

    public static List<Product> getAll() {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Product> products = null;
        try {
            // Create query
            final String sqlString = "from Product ct where active=true ";
            Query query = session.createQuery(sqlString);
            products = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    public Product getLatestProduct() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Product product = new Product();
        List<Product> products = null;
        try {
            final String sqlString = "Select p from Product p  where active=true order by p.id desc";
            Query query = session.createQuery(sqlString);
            products = query.list();
            product = products.get(0);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }

    public static List<Product> searchByname(String name) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Product> books = null;
        try {
            // Create query string
            String queryString = "from Product where name like :name and active=true";

            // Create query
            Query query = session.createQuery(queryString, Product.class);
            query.setParameter("name", "%" + name + "%");

            // Return result List
            books = query.list();
        } catch (HibernateError error) {
            System.err.println(error);
        } finally {
            session.close();
        }
        return books;
    }

    public static List<Product> getProductByCategoryID(int id) {
        // open session
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Product> products = null;
        try {
            // Create query
            final String sqlString = "select ct from Product ct where idCategory = :cID and active=true ";
            Query query = session.createQuery(sqlString);
            query.setParameter("cID", id);
            products = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    public static List<Product> get5LastestProduct() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        List<Product> products = null;

        try {
            final String sqlString = "Select p from Product p order by p.id desc";
            Query query = session.createQuery(sqlString);
            products = query.setMaxResults(5).list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }
}
