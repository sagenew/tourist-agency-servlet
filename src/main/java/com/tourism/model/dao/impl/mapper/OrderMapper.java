package com.tourism.model.dao.impl.mapper;

import com.tourism.model.entity.Order;
import com.tourism.model.entity.enums.OrderStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements ObjectMapper<Order> {

    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("orders.id"));

        String statusStr = rs.getString("orders.status");
        order.setStatus(statusStr != null ? OrderStatus.valueOf(statusStr) : null);

        order.setPrice(rs.getDouble("orders.price"));
        order.setDiscount(rs.getDouble("orders.discount"));
        return order;
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order order) {
        cache.putIfAbsent(order.getId(), order);
        return cache.get(order.getId());
    }
}
