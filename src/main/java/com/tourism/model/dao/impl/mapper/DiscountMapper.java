package com.tourism.model.dao.impl.mapper;

import com.tourism.model.entity.Discount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountMapper {
    public Discount extractFromResultSet(ResultSet rs) throws SQLException {
        double step = rs.getDouble("discount.step");
        double threshold = rs.getDouble("discount.threshold");

        return Discount.builder()
                .step(step)
                .threshold(threshold)
                .build();
    }
}
