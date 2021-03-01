package com.tourism.model.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Class used to parse object from ResultSet
 *
 * @param <T> object that will be mapped
 */
public interface ObjectMapper<T> {
    /**
     * Extract object from result set
     */
    T extractFromResultSet(ResultSet rs) throws SQLException;

    /**
     * Make object unique using map
     */
    T makeUnique(Map<Long, T> cache, T object);
}
