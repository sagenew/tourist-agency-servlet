package com.tourism.model.dao.impl;

import com.tourism.model.dao.TourDao;
import com.tourism.model.dao.impl.mapper.TourMapper;
import com.tourism.model.entity.Tour;
import com.tourism.util.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCTourDao implements TourDao {
    public static final Logger log = LogManager.getLogger();
    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("database");

    JDBCTourDao(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public Optional<Tour> findById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(
                rb.getString("query.tour.find.by_id"))) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            Map<Long, Tour> tourMap = extractToursFromResultSet(rs);
            return tourMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException("Can not find tour by id", e);
        }
    }

    @Override
    public List<Tour> findAllPageable(int page, int size, String sortCol, String sortDir) {
        try (Statement st = connection.createStatement()) {
            String sql = rb.getString("query.tour.find.all") +
                    " order by " + sortCol +
                    " " + sortDir + " " +
                    " limit " + size +
                    " offset " + (long) size * page;
            ResultSet rs = st.executeQuery(sql);
            Map<Long, Tour> tourMap = extractToursFromResultSet(rs);
            return new ArrayList<>(tourMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all tours pageable", e);
        }
    }

    @Override
    public void create(Tour tour) {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.tour.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillStatement(tour, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not create tour", e);
        }
    }

    @Override
    public void update(Tour tour) {
        try (PreparedStatement tourPS = connection.prepareStatement(rb.getString("query.tour.update"))) {
            tourPS.setString(1, tour.getName());
            tourPS.setString(2, tour.getType().name());
            tourPS.setDouble(3, tour.getPrice());
            tourPS.setInt(4, tour.getGroupSize());
            tourPS.setString(5, tour.getHotel().name());
            tourPS.setString(6, tour.getDescription());
            tourPS.setLong(7, tour.getId());
            tourPS.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not update tour", e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.tour.delete"))) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not delete tour", e);
        }
    }

    @Override
    public void updateHot(Tour tour) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.tour.update_hot"))) {
            ps.setBoolean(1, tour.isHot());
            ps.setLong(2, tour.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not mark hot tour", e);
        }
    }

    @Override
    public long getNumberOfRecords() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(rb.getString("query.tour.count.rows"));
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException("Can not get numbers of records", e);
        }
    }

    private void fillStatement(Tour tour, PreparedStatement ps) throws SQLException {
        ps.setString(1, tour.getName());
        ps.setString(2, tour.getType().name());
        ps.setDouble(3, tour.getPrice());
        ps.setInt(4, tour.getGroupSize());
        ps.setString(5, tour.getHotel().name());
        ps.setString(6, tour.getDescription());
        ps.setBoolean(7, tour.isHot());
    }

    private Map<Long, Tour> extractToursFromResultSet(ResultSet rs) throws SQLException {
        Map<Long, Tour> tourMap = new LinkedHashMap<>();
        TourMapper tourMapper = new TourMapper();
        while (rs.next()) {
            Tour tour = tourMapper.extractFromResultSet(rs);
            tourMapper.makeUnique(tourMap, tour);
        }
        return tourMap;
    }
}
