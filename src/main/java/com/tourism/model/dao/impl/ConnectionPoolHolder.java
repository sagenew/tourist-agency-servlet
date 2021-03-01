package com.tourism.model.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Connection pool holder class that handle connection using BasicDataSource
 */
class ConnectionPoolHolder {
    /**
     * Resource bundle with properties to connect to database
     */
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
    /**
     * Singleton instance
     */
    private static volatile DataSource dataSource;

    /**
     * @return Set up database connection using properties or return instance if present
     */
    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setUrl(resourceBundle.getString("database.url"));
                    ds.setUsername(resourceBundle.getString("database.user"));
                    ds.setPassword(resourceBundle.getString("database.password"));
                    ds.setDriverClassName(resourceBundle.getString("database.driver"));
                    dataSource = ds;
                }
            }
        }
        return dataSource;
    }
}
