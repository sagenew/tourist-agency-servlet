package com.tourism.model.dao.impl;

import com.tourism.model.dao.UserDao;
import com.tourism.model.dao.impl.mapper.OrderMapper;
import com.tourism.model.dao.impl.mapper.TourMapper;
import com.tourism.model.dao.impl.mapper.UserMapper;
import com.tourism.model.entity.Order;
import com.tourism.model.entity.Tour;
import com.tourism.model.entity.User;
import com.tourism.model.entity.enums.Authority;
import com.tourism.util.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao {
    public static final Logger log = LogManager.getLogger();
    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("database");

    JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.user.find.by_id"))) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            Map<Long, User> userMap = extractUsersFromResultSet(rs);
            return userMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException("Can not find user by id", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.user.find.by_username"))) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            Map<Long, User> userMap = extractUsersFromResultSet(rs);
            return userMap.values().stream().findAny();
        } catch (SQLException e) {
            throw new DaoException("Can not find user by username", e);
        }
    }

    @Override
    public List<User> findAllPageable(int page, int size) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.user.find.all.pageable"))) {
            ps.setLong(1, size);
            ps.setLong(2, (long) size * page);
            ResultSet rs = ps.executeQuery();

            Map<Long, User> userMap = extractUsersFromResultSet(rs);

            return new ArrayList<>(userMap.values());
        } catch (SQLException e) {
            throw new DaoException("Can not find all users", e);
        }
    }

    @Override
    public void create(User user) {
        try (PreparedStatement ps =
                     connection.prepareStatement(rb.getString("query.user.create"), Statement.RETURN_GENERATED_KEYS)) {
            fillUserStatement(user, ps);
            ps.setBoolean(7, user.isEnabled());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
            try (PreparedStatement authorityPS = connection.prepareStatement(rb.getString("query.authority.create"))) {
                for (Authority authority : user.getAuthorities()) {
                    authorityPS.setLong(1, user.getId());
                    authorityPS.setString(2, authority.name());
                    authorityPS.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can not create user", e);
        }
    }

    @Override
    public void update(User user) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.user.update"))) {
            fillUserStatement(user, ps);
            ps.setLong(7, user.getId());
            ps.executeUpdate();

            try (PreparedStatement authorityDeletePS = connection.prepareStatement(rb.getString("query.authority.delete.by_id"))) {
                authorityDeletePS.setLong(1, user.getId());
                authorityDeletePS.executeUpdate();
            }
            try (PreparedStatement authorityInsertPS = connection.prepareStatement(rb.getString("query.authority.create"))) {
                for (Authority authority : user.getAuthorities()) {
                    authorityInsertPS.setLong(1, user.getId());
                    authorityInsertPS.setString(2, authority.name());
                    authorityInsertPS.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can not update user", e);
        }
    }

    public void updateEnabled(long id, boolean enabled) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.user.update-enabled"))) {
            ps.setBoolean(1, enabled);
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not update user registration status", e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.user.delete"))) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not delete user", e);
        }
    }

    @Override
    public long getNumberOfRecords() {
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(rb.getString("query.user.count.rows"));
            if (rs.next()) return rs.getLong(1);
            return 0;
        } catch (SQLException e) {
            throw new DaoException("Can not get numbers of records", e);
        }
    }
    
    private void fillUserStatement(User user, PreparedStatement ps) throws SQLException {
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getFullName());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getBio());
        ps.setDouble(6, user.getDiscount());
    }

    private Map<Long, User> extractUsersFromResultSet(ResultSet rs) throws SQLException {
        Map<Long, User> userMap = new LinkedHashMap<>();
        Map<Long, Tour> tourMap = new LinkedHashMap<>();
        Map<Long, Order> orderMap = new LinkedHashMap<>();

        UserMapper userMapper = new UserMapper();
        TourMapper tourMapper = new TourMapper();
        OrderMapper orderMapper = new OrderMapper();

        while (rs.next()) {
            User user = userMapper.extractFromResultSet(rs);
            userMapper.makeUnique(userMap, user);
        }

        for (User user : userMap.values()) {
            try (PreparedStatement userAuthoritiesPS
                         = connection.prepareStatement(rb.getString("query.user.join.authorities"))) {
                userAuthoritiesPS.setLong(1, user.getId());
                ResultSet userAuthoritiesResultSet = userAuthoritiesPS.executeQuery();

                while (userAuthoritiesResultSet.next()) {
                    Authority authority = Authority.valueOf(userAuthoritiesResultSet.getString("user_authorities.authorities"));
                    user.getAuthorities().add(authority);
                }
            }
            try (PreparedStatement ordersPS = connection.prepareStatement(rb.getString("query.user.join.orders+tours"))) {
                ordersPS.setLong(1, user.getId());
                ResultSet userOrdersResultSet = ordersPS.executeQuery();

                while (userOrdersResultSet.next()) {
                    Order order = orderMapper.extractFromResultSet(userOrdersResultSet);
                    Tour tour = tourMapper.extractFromResultSet(userOrdersResultSet);

                    order = orderMapper.makeUnique(orderMap, order);
                    tour = tourMapper.makeUnique(tourMap, tour);

                    if ((order.getId() != 0) && !user.getOrders().contains(order)) {
                        order.setUser(user);
                        order.setTour(tour);
                        user.getOrders().add(order);
                    }
                }
            }
        }
        return userMap;
    }
}
