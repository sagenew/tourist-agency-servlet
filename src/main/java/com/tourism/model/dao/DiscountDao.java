package com.tourism.model.dao;

import com.tourism.model.entity.Discount;

import java.util.Optional;

public interface DiscountDao {
    void set(Discount discount);
    Optional<Discount> get();
}
