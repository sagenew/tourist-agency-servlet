package com.tourism.model.dao.impl.mapper;

import com.tourism.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("users.id"));
        user.setUsername(rs.getString("users.username"));
        user.setPassword(rs.getString("users.password"));
        user.setFullName(rs.getString("users.full_name"));
        user.setEmail(rs.getString("users.email"));
        user.setBio(rs.getString("users.bio"));
        user.setDiscount(rs.getDouble("users.discount"));
        user.setEnabled(rs.getBoolean("users.enabled"));
        return user;
    }

    @Override
    public User makeUnique(Map<Long, User> cache, User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
