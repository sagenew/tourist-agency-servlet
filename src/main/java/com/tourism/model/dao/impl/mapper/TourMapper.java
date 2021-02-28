package com.tourism.model.dao.impl.mapper;

import com.tourism.model.entity.Tour;
import com.tourism.model.entity.enums.HotelType;
import com.tourism.model.entity.enums.TourType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TourMapper implements ObjectMapper<Tour> {
    @Override
    public Tour extractFromResultSet(ResultSet rs) throws SQLException {
        Tour tour = new Tour();
        tour.setId(rs.getLong("tours.id"));
        tour.setName(rs.getString("tours.name"));

        String typeStr = rs.getString("tours.type");
        tour.setType(typeStr != null ? TourType.valueOf(typeStr) : null);

        tour.setPrice(rs.getDouble("tours.price"));
        tour.setGroupSize(rs.getInt("tours.group_size"));

        String hotelStr = rs.getString("tours.hotel");
        tour.setHotel(hotelStr != null ? HotelType.valueOf(hotelStr) : null);

        tour.setDescription(rs.getString("tours.description"));
        tour.setHot(rs.getBoolean("tours.hot"));
        return tour;
    }

    @Override
    public Tour makeUnique(Map<Long, Tour> cache, Tour tour) {
        cache.putIfAbsent(tour.getId(), tour);
        return cache.get(tour.getId());
    }
}
