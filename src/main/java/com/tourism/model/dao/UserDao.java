package com.tourism.model.dao;

import com.tourism.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByUsername(String username);
    List<User> findAllPageable(int page, int size);
    long getNumberOfRecords();
    void updateEnabled(long id, boolean enabled);
}
