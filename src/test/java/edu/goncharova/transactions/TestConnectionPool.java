package edu.goncharova.transactions;

import edu.goncharova.configuration.DatabaseTestConfig;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnectionPool implements ConnectionPool {
    private final static ConnectionPool connectionPool = new TestConnectionPool();
    private final DataSource dataSource;
    private static final String DB_CONNECTION = DatabaseTestConfig.DATABASE_URL_TEST;
    private static final String DB_USER = DatabaseTestConfig.DATABASE_USER_TEST;
    private static final String DB_PASSWORD = DatabaseTestConfig.DATABASE_PASSWORD_TEST;

    private TestConnectionPool(){
        dataSource = JdbcConnectionPool.create(DB_CONNECTION, DB_USER, DB_PASSWORD);
    }
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
        //return dataSource.getConnection();
    }

    public static ConnectionPool getInstance() {
        return connectionPool;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
