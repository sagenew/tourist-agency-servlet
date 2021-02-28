package com.tourism.model.dao;

import com.tourism.model.entity.Tour;

import java.util.List;

public interface TourDao extends GenericDao<Tour>{
    List<Tour> findAllPageable(int page, int size, String sortCol, String sortDir);
    long getNumberOfRecords();
    void updateHot(Tour tour);
}
