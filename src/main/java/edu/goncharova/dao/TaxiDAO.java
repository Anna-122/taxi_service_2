package edu.goncharova.dao;

import edu.goncharova.domain.Taxi;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.transactions.ConnectionWrapper;
import edu.goncharova.transactions.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxiDAO {
    private final static Logger LOGGER = LogManager.getLogger(TaxiDAO.class);
    private final static String SQL_SELECT_BY_CARNUMBER = "select * from taxi where carnumber=?";
    private final static String SQL_COUNT_SIZE = "select count(*) from taxi where taxitypeid >=?";
    private final static String SQL_SELECT_BY_TAXIID = "select * from taxi where taxiid=?";
    private final static String SQL_SELECT_BY_TAXITYPEID_SKIPPING = "select * from taxi where taxitypeid>=? order by taxiid limit ?,1";
    private final static String SQL_INSERT_TAXI = "insert into taxi(carnumber,taxitypeid,driverid) values(?,?,?)";

    public int findNumberOfSpecifiedTaxiType(TaxiType taxiType) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_COUNT_SIZE);
                statement.setInt(1, taxiType.getTaxiTypeId());
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
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
        return -1;
    }

    public Taxi findByCarNumber(String carNumber) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_SELECT_BY_CARNUMBER);
                statement.setString(1, carNumber);
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Taxi taxi = new Taxi(resultSet.getInt("driverid"),
                            resultSet.getInt("taxitypeid"), carNumber);
                    taxi.setTaxiId(resultSet.getInt("taxiid"));
                    return taxi;
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                con.close();
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public Taxi findById(int id) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_SELECT_BY_TAXIID);
                statement.setInt(1, id);
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Taxi taxi = new Taxi(resultSet.getInt("driverid"),
                            resultSet.getInt("taxitypeid"), resultSet.getString("carnumber"));
                    taxi.setTaxiId(id);
                    return taxi;
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                con.close();
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public Taxi findNthCar(int toSkip, TaxiType taxiType) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_SELECT_BY_TAXITYPEID_SKIPPING);
                statement.setInt(1, taxiType.getTaxiTypeId());
                statement.setInt(2, toSkip);
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Taxi taxi = new Taxi(resultSet.getInt("driverid"),
                            resultSet.getInt("taxitypeid"), resultSet.getString("carnumber"));
                    taxi.setTaxiId(resultSet.getInt("taxiid"));
                    return taxi;
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                con.close();
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public boolean create(Taxi entity) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            try (PreparedStatement statement = con.preparedStatement(SQL_INSERT_TAXI)) {
                statement.setString(1, entity.getCarNumber());
                statement.setInt(2, entity.getTaxiTypeId());
                statement.setInt(3, entity.getDriverId());
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
