package com.mysql.demo.config;

import com.mysql.demo.common.DbTypeEm;
import com.mysql.demo.handler.DbContextHandler;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DbRouting extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHandler.getDb().orElse(DbTypeEm.db0);
    }

}