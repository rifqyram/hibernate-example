package com.danamon.hibernate.dao;

import com.danamon.hibernate.config.HibernateConfig;
import com.danamon.hibernate.model.Product;
import com.danamon.hibernate.model.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class ProductDao {

    public Product save(Product product) {
        Session session = HibernateConfig.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Serializable id = session.save(product);
            session.getTransaction().commit();
            return session.get(Product.class, id);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }

    public Product findById(Integer id) {
        /*
         * Try With Resource digunakan pada session ini agar session menutup otomatis jika kode pada try catch selesai.
         * ini digunakan supaya lebih simple jika tidak ada proses transaksi.
         */

        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Product product = session.get(Product.class, id);
            if (product == null) throw new RuntimeException("Resource with id not found");
            return product;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Product> findAll() {
        /*
         * Criteria API digunakan untuk membuat Query secara programmatic dimana kita bisa memberikan beberapa logika didalamnya
         * https://www.tutorialspoint.com/hibernate/hibernate_criteria_queries.htm#:~:text=Hibernate%20provides%20alternate%20ways%20of,filtration%20rules%20and%20logical%20conditions.
         * */

        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);
            CriteriaQuery<Product> all = cq.select(root);

            Query<Product> query = session.createQuery(all);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Product update(Product product) {
        Product currentProduct = findById(product.getId());

        Session session = HibernateConfig.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            setProductUpdate(product, currentProduct);
            session.update(currentProduct);
            session.getTransaction().commit();
            return currentProduct;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        Product product = findById(id);
        Session session = HibernateConfig.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }

    private void setProductUpdate(Product product, Product currentProduct) {
        currentProduct.setName(product.getName() == null ? currentProduct.getName() : product.getName());
        currentProduct.setPrice(product.getPrice() == null ? currentProduct.getPrice() : product.getPrice());
        currentProduct.setStock(product.getStock() == null ? currentProduct.getStock() : product.getStock());
    }

}
