package com.tourism.model.dao.impl;

import com.tourism.model.dao.DiscountDao;
import com.tourism.model.dao.impl.mapper.DiscountMapper;
import com.tourism.model.entity.Discount;
import com.tourism.util.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class JDBCDiscountDao implements DiscountDao {
    public static final Logger log = LogManager.getLogger();
    private final Connection connection;
    private final ResourceBundle rb = ResourceBundle.getBundle("database");

    JDBCDiscountDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void set(Discount discount) {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.discount.set"))) {
            fillDiscountStatement(discount, ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not set discount", e);
        }
    }

    @Override
    public Optional<Discount> get() {
        try (PreparedStatement ps = connection.prepareStatement(rb.getString("query.discount.get"))) {
            ResultSet rs = ps.executeQuery();
            return Optional.of(extractDiscountFromResultSet(rs));
        } catch (SQLException e) {
            throw new DaoException("Can not get discount", e);
        }
    }

    private void fillDiscountStatement(Discount discount, PreparedStatement ps) throws SQLException {
        ps.setDouble(1, discount.getStep());
        ps.setDouble(2, discount.getThreshold());

    }

    private Discount extractDiscountFromResultSet(ResultSet rs) throws SQLException {
        rs.next();
        return new DiscountMapper().extractFromResultSet(rs);
    }
}
