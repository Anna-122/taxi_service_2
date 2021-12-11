package edu.goncharova.transactions;

import edu.goncharova.exceptions.TransactionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private final static Logger LOGGER = LogManager.getLogger(TransactionManager.class);
    private static final ThreadLocal<ConnectionWrapper> threadLocal = new ThreadLocal<>();
    private static ConnectionPool connectionPool = MySQLConnectionPool.getInstance();

    public static void setConnectionPool(ConnectionPool connectionPool) {
        TransactionManager.connectionPool = connectionPool;
    }

    private TransactionManager() {
    }

    public static void beginTransaction() throws SQLException, TransactionException {
        if (threadLocal.get() != null) throw new TransactionException();
        Connection connection = connectionPool.getConnection();
        ConnectionWrapper wrapper = new ConnectionWrapper(connection, true);
        threadLocal.set(wrapper);
    }

    public static void endTransaction() throws SQLException, TransactionException {
        if (threadLocal.get() == null)
            throw new TransactionException();
        ConnectionWrapper wrapper = threadLocal.get();
        wrapper.getConnection().close();
        threadLocal.set(null);
    }

    public static ConnectionWrapper getConnection() throws SQLException {
        if (threadLocal.get() == null) {
            Connection connection = connectionPool.getConnection();
            LOGGER.info("Connection given");
            return new ConnectionWrapper(connection, false);
        } else {
            return threadLocal.get();
        }
    }
}
