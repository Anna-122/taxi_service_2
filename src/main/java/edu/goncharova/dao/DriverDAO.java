package edu.goncharova.dao;

import edu.goncharova.domain.Driver;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.transactions.ConnectionWrapper;
import edu.goncharova.transactions.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDAO {
    private final static Logger LOGGER = LogManager.getLogger(DriverDAO.class);
    private final static String SQL_SELECT_BY_USERID = "select * from driver where userid=?";
    private final static String SQL_SELECT_BY_ID = "select * from driver where driverid=?";
    private final static String SQL_DELETE_BY_USERID = "delete from driver where userid=?";
    private final static String SQL_INSERT_DRIVER = "insert into driver(userid) values(?)";

    public DriverDAO() {

    }

    public Driver findByUserId(int userid) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_SELECT_BY_USERID);
                statement.setInt(1, userid);
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Driver driver = new Driver(userid);
                    driver.setDriverId(resultSet.getInt("driverid"));
                    return driver;
                }
                return null;
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public Driver findById(int id) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_SELECT_BY_ID);
                statement.setInt(1, id);
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Driver driver = new Driver(resultSet.getInt("userid"));
                    driver.setDriverId(id);
                    return driver;
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return null;
    }

    public boolean delete(Driver entity) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            try (PreparedStatement statement = con.preparedStatement(SQL_DELETE_BY_USERID)) {
                statement.setInt(1, entity.getUserId());
                LOGGER.debug("Statement to execute {}", statement.toString());
                return statement.execute();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public boolean create(Driver entity) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            try (PreparedStatement statement = con.preparedStatement(SQL_INSERT_DRIVER)) {
                statement.setInt(1, entity.getUserId());
                LOGGER.debug("Statement to execute {}", statement.toString());
                return statement.execute();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }
}
