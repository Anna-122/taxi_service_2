package edu.goncharova.transactions;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static edu.goncharova.configuration.DatabaseConfig.*;

public class MySQLConnectionPool implements ConnectionPool {
    private final static Logger LOGGER = LogManager.getLogger(MySQLConnectionPool.class);
    private static final MySQLConnectionPool connectionPool = new MySQLConnectionPool();
    private static PoolingDataSource<PoolableConnection> dataSource;

    private MySQLConnectionPool() {
        initDataSource();
    }

    public static ConnectionPool getInstance() {
        return connectionPool;
    }

    private void initDataSource() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(connectionFactory, null);

        ObjectPool<PoolableConnection> connectionPool =
                new GenericObjectPool<>(poolableConnectionFactory);

        poolableConnectionFactory.setPool(connectionPool);

        dataSource =
                new PoolingDataSource<>(connectionPool);
    }

    public Connection getConnection() throws SQLException {
        LOGGER.info("Giving a connection");
        return dataSource.getConnection();
    }

}
