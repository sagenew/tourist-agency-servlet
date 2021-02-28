package com.tourism.model.dao.impl;

import com.tourism.model.dao.DaoConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoConnection implements DaoConnection {
    private static final Logger log = LogManager.getLogger();
    private final Connection connection;
    private boolean inTransaction;

    JDBCDaoConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
            inTransaction = true;
        } catch (SQLException e) {
            log.error("Can not disable auto commit", e);
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            inTransaction = false;
        } catch (SQLException e) {
            log.error("Can not commit transaction", e);
        }
    }

    @Override
    public void rollback() {
        try {
            log.debug("Try to rollback transaction");
            connection.rollback();
            inTransaction = false;
            connection.setAutoCommit(true);
            log.debug("Transaction rollback successfully");
        } catch (SQLException e) {
            log.error("Can not rollback transaction", e);
        }
    }

    @Override
    public void close() {
        if (inTransaction) {
            rollback();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("Can not close connection", e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
