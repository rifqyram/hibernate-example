package com.danamon.hibernate.dao;

import com.danamon.hibernate.config.HibernateConfig;
import com.danamon.hibernate.model.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class CustomerDao {

    public Customer save(Customer customer) {
        Session session = HibernateConfig.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Serializable id = session.save(customer);
            Customer savedCustomer = session.get(Customer.class, id);
            session.getTransaction().commit();
            return savedCustomer;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }

    public Customer findById(Integer id) {
        /*
         * Try With Resource digunakan pada session ini agar session menutup otomatis jika kode pada try catch selesai.
         * ini digunakan supaya lebih simple jika tidak ada proses transaksi.
         */

        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Customer customer = session.get(Customer.class, id);
            if (customer == null) throw new RuntimeException("Resource with id not found");
            return customer;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Customer> findAll() {
        /*
         * Criteria API digunakan untuk membuat Query secara programmatic dimana kita bisa memberikan beberapa logika didalamnya
         * https://www.tutorialspoint.com/hibernate/hibernate_criteria_queries.htm#:~:text=Hibernate%20provides%20alternate%20ways%20of,filtration%20rules%20and%20logical%20conditions.
         * */

        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
            Root<Customer> root = cq.from(Customer.class);
            CriteriaQuery<Customer> all = cq.select(root);

            Query<Customer> query = session.createQuery(all);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Customer update(Customer customer) {
        Customer currentCustomer = findById(customer.getId());

        Session session = HibernateConfig.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            setCustomerUpdate(customer, currentCustomer);
            session.update(currentCustomer);
            session.getTransaction().commit();
            return currentCustomer;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        Customer customer = findById(id);
        Session session = HibernateConfig.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }

    private void setCustomerUpdate(Customer customer, Customer currentCustomer) {
        currentCustomer.setName(customer.getName() == null ? currentCustomer.getName() : customer.getName());
        currentCustomer.setAddress(customer.getAddress() == null ? currentCustomer.getAddress() : customer.getAddress());
        currentCustomer.setBirthDate(customer.getBirthDate() == null ? currentCustomer.getBirthDate() : customer.getBirthDate());
        currentCustomer.setPhoneNumber(customer.getPhoneNumber() == null ? currentCustomer.getPhoneNumber() : customer.getPhoneNumber());
        currentCustomer.setStatus(customer.getStatus() == null ? currentCustomer.getStatus() : customer.getStatus());
    }

}
