package com.danamon.hibernate.dao;

import com.danamon.hibernate.config.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class CrudDaoImpl<T, ID> implements CrudDao<T, ID> {

    private final Class<T> clazz;

    public CrudDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clazz = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T save(T entity) {
        Session session = HibernateConfig.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Serializable id = session.save(entity);
            T savedEntity = session.get(clazz, id);
            session.getTransaction().commit();;
            return savedEntity;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public T findById(ID id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            T entity = session.get(clazz, (Serializable) id);
            if (entity == null) throw new RuntimeException(String.format("%s not found", clazz.getSimpleName()));
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            CriteriaQuery<T> all = cq.select(root);
            Query<T> query = session.createQuery(all);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(T entity) {
        Session session = HibernateConfig.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteById(ID id) {
        Session session = HibernateConfig.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            T entity = session.get(clazz, (Serializable) id);
            if (entity == null) throw new RuntimeException(String.format("%s not found", clazz.getSimpleName()));
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }
}
