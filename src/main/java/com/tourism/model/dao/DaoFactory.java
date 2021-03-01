package com.tourism.model.dao;

import com.tourism.model.dao.impl.JDBCDaoFactory;

/**
 * Factory that return dao and connection to use with dao
 */
public abstract class DaoFactory {
    /**
     * Singleton instance
     */
    private static DaoFactory daoFactory;
    /**
     * Get JDBCDaoFactory singleton instance
     */
    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }

    public abstract UserDao createUserDao(DaoConnection connection);

    public abstract TourDao createTourDao(DaoConnection connection);

    public abstract OrderDao createOrderDao(DaoConnection connection);

    public abstract DiscountDao createDiscountDao(DaoConnection connection);

    public abstract DaoConnection getConnection();
}
