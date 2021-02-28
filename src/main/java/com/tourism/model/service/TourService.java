package com.tourism.model.service;

import com.tourism.controller.dto.TourDTO;
import com.tourism.model.dao.DaoConnection;
import com.tourism.model.dao.DaoFactory;
import com.tourism.model.dao.TourDao;
import com.tourism.model.entity.Tour;
import com.tourism.util.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class TourService {
    private static final Logger log = LogManager.getLogger();
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public List<Tour> findAllToursPageable(int page, int size, String sortCol, String sortDir) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao toutDao = daoFactory.createTourDao(connection);
            return toutDao.findAllPageable(page, size, sortCol, sortDir);
        } catch (DaoException e) {
            log.warn("Can not get all tours", e);
            return Collections.emptyList();
        }
    }

    public Tour findTourById(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            return tourDao.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("Invalid tour id: " + id));
        } catch (DaoException e) {
            log.warn("Can not find tour with id: " + id);
        }
        return null;
    }

    public void createTour(TourDTO tourDTO) {
        Tour tour = Tour.builder()
                .name(tourDTO.getName())
                .type(tourDTO.getType())
                .price(tourDTO.getPrice())
                .groupSize(tourDTO.getGroupSize())
                .hotel(tourDTO.getHotel())
                .description(tourDTO.getDescription())
                .isHot(false)
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            tourDao.create(tour);
        } catch (DaoException e) {
            log.warn("Can not create tour", e);
        }
    }

    public void updateTour(long id, TourDTO tourDTO) {
        Tour tour = Tour.builder()
                .id(id)
                .name(tourDTO.getName())
                .type(tourDTO.getType())
                .price(tourDTO.getPrice())
                .groupSize(tourDTO.getGroupSize())
                .hotel(tourDTO.getHotel())
                .description(tourDTO.getDescription())
                .build();
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            tourDao.update(tour);
        } catch (DaoException e) {
            log.warn("Can not update tour " + tour.getName(), e);
        }
    }

    public void deleteTour(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            tourDao.delete(id);
        } catch (DaoException e) {
            log.warn("Can not delete tour", e);
        }
    }

    public void markAsHot(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            connection.beginTransaction();

            Tour tour = findTourById(id);
            tour.switchHot();
            tourDao.updateHot(tour);

            connection.commit();
        } catch (DaoException e) {
            log.warn("Can not mark hot tour", e);
        }
    }

    public long getNumberOfRecords() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TourDao tourDao = daoFactory.createTourDao(connection);
            return tourDao.getNumberOfRecords();
        } catch (DaoException e) {
            log.warn("Can not get number of tours", e);
        }
        return 0;
    }
}
