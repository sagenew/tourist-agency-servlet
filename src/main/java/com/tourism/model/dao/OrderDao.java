package com.tourism.model.dao;

import com.tourism.model.entity.Order;
import com.tourism.model.entity.User;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> findAllPageable(int page, int size, String sortCol, String sortDir);
    long getNumberOfRecords();
    void updateOrdersDiscountByUser(User user);
}
