package com.tourism.model.dao;

import java.util.Optional;

public interface GenericDao<T> {
    Optional<T> findById(long id);
    void create(T entity);
    void update(T entity);
    void delete(long id);
}
