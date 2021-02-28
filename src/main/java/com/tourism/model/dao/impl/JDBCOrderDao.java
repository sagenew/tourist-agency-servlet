package com.tourism.model.dao.impl;

import com.tourism.model.dao.OrderDao;
import com.tourism.model.dao.impl.mapper.OrderMapper;
import com.tourism.model.dao.impl.mapper.TourMapper;
import com.tourism.model.dao.impl.mapper.UserMapper;
import com.tourism.model.entity.Order;
import com.tourism.model.entity.Tour;
import com.tourism.model.entity.User;
import com.tourism.model.entity.enums.Authority;
import com.tourism.model.entity.enums.OrderStatus;
import com.tourism.util.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCOrderDao implements OrderDao {
    public static final Logger log = LogManager.getLogger();
    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("database");

    JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Order> findById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.order.find.by_id"))) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            Map<Long, Order> orderMap = extractOrdersFromResultSet(rs);
            return orderMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException("Can not find order", e);
        }
    }

    @Override
    public List<Order> findAllPageable(int page, int size, String sortCol, String sortDir) {
        try (Statement st = connection.createStatement()) {
            String sql = rb.getString("query.order.find.all") +
                    " order by " + sortCol +
                    " " + sortDir +
                    " limit " + size +
                    " offset " + (long) size * page;
            ResultSet rs = st.executeQuery(sql);
            Map<Long, Order> tourMap = extractOrdersFromResultSet(rs);
            return new ArrayList<>(tourMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all orders pageable", e);
        }
    }

    @Override
    public void create(Order order) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.order.create"))) {
            fillOrderStatement(order, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not create order", e);
        }
    }

    @Override
    public void update(Order order) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.order.update"))) {
            fillOrderStatement(order, ps);
            ps.setLong(6, order.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not update order", e);
        }
    }

    @Override
    public void updateOrdersDiscountByUser(User user) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.order.update-discount-by-user"))) {
            ps.setDouble(1, user.getDiscount());
            ps.setLong(2, user.getId());
            ps.setString(3, OrderStatus.PENDING.name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not update order", e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.order.delete"))) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not delete order", e);
        }
    }

    @Override
    public long getNumberOfRecords() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(rb.getString("query.order.count.rows"));
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException("Can not get number of orders", e);
        }
    }

    private void fillOrderStatement(Order order, PreparedStatement ps) throws SQLException {
        ps.setLong(1, order.getTour().getId());
        ps.setLong(2, order.getUser().getId());
        ps.setString(3, order.getStatus().name());
        ps.setDouble(4, order.getPrice());
        ps.setDouble(5, order.getDiscount());
    }

    private Map<Long, Order> extractOrdersFromResultSet(ResultSet rs) throws SQLException {
        Map<Long, Order> orderMap = new LinkedHashMap<>();
        Map<Long, User> userMap = new HashMap<>();
        Map<Long, Tour> tourMap = new HashMap<>();

        OrderMapper orderMapper = new OrderMapper();
        UserMapper userMapper = new UserMapper();
        TourMapper tourMapper = new TourMapper();

        while (rs.next()) {
            Order order = orderMapper.extractFromResultSet(rs);
            orderMapper.makeUnique(orderMap, order);
        }

        for (Order order : orderMap.values()) {
            try (PreparedStatement toursPS = connection.prepareStatement(rb.getString("query.order.join.tour"))) {
                toursPS.setLong(1, order.getId());
                ResultSet toursResultSet = toursPS.executeQuery();
                while (toursResultSet.next()) {
                    Tour tour = tourMapper.extractFromResultSet(toursResultSet);
                    tour = tourMapper.makeUnique(tourMap, tour);
                    if (tour.getId() != 0) {
                        order.setTour(tour);
                    }
                }
            }

            try (PreparedStatement usersPS = connection.prepareStatement(rb.getString("query.order.join.user"))) {
                usersPS.setLong(1, order.getId());
                ResultSet usersResultSet = usersPS.executeQuery();
                while (usersResultSet.next()) {
                    User user = userMapper.extractFromResultSet(usersResultSet);
                    user = userMapper.makeUnique(userMap, user);
                    if (usersResultSet.getString("user_authorities.authorities") != null) {
                        Authority authority = Authority
                                .valueOf(usersResultSet.getString("user_authorities.authorities"));
                        user.getAuthorities().add(authority);
                    }
                    if (user.getId() != 0) {
                        order.setUser(user);
                    }
                }
            }
        }
        return orderMap;
    }
}
