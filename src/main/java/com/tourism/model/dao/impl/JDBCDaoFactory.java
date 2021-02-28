package com.tourism.model.dao.impl;

import com.tourism.model.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private static final Logger log = LogManager.getLogger();
    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao(DaoConnection connection) {
        return new JDBCUserDao(connection.getConnection());
    }

    @Override
    public TourDao createTourDao(DaoConnection connection) {
        return new JDBCTourDao(connection.getConnection());
    }

    @Override
    public OrderDao createOrderDao(DaoConnection connection) {
        return new JDBCOrderDao(connection.getConnection()); }

    @Override
    public DiscountDao createDiscountDao(DaoConnection connection) {
        return new JDBCDiscountDao(connection.getConnection());
    }

    @Override
    public DaoConnection getConnection() {
        try {
            return new JDBCDaoConnection(dataSource.getConnection());
        } catch (SQLException e) {
            log.error("Can not get connection to database", e);
            throw new RuntimeException(e);
        }
    }
}
