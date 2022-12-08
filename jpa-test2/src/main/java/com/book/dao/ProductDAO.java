package com.book.dao;

import com.book.entity.CategoryEntity;
import com.book.entity.ProductEntity;
import com.book.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO {
    public static void save(ProductEntity product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
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

    public static void update(ProductEntity product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            ProductEntity product = session.get(ProductEntity.class, id);
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

    public static ProductEntity findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ProductEntity product = session.load(ProductEntity.class,id);
        return product;
    }

    public static List<ProductEntity> getAll() {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ProductEntity> products = null;
        try {
            // Create query
            final String sqlString = "select ct from ProductEntity ct where active=false ";
            Query query = session.createQuery(sqlString);
            products = query.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    public ProductEntity getLatestProduct() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ProductEntity product = new ProductEntity();
        List<ProductEntity> products = null;
        try {
            final String sqlString = "Select p from ProductEntity p  where active=false order by p.id desc";
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

    public static List<ProductEntity> findByName(String name) {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ProductEntity> books = null;
        try {
            // Create query string
            String queryString = "from ProductEntity where name like :name and active=false";

            // Create query
            Query query = session.createQuery(queryString, ProductEntity.class);
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

    public static List<ProductEntity> findByCategoryID(int id) {
        // open session
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ProductEntity> products = null;
        try {
            // Create query
            final String sqlString = "select ct from ProductEntity ct where idCategory = :cID and active=false ";
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

    public static List<ProductEntity> get5LastestProduct(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ProductEntity> products = null;

        try{
            final String sqlString = "Select p from ProductEntity p order by p.id desc";
            Query query = session.createQuery(sqlString);
            products = query.setMaxResults(5).list();
        }
        catch(RuntimeException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }
}
