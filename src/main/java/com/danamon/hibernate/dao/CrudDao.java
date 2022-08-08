package com.danamon.hibernate.dao;

import java.util.List;

public interface CrudDao<T, ID> {

    T save(T entity);

    T findById(ID id);

    List<T> findAll();

    void update(T entity);

    void deleteById(ID id);

}
