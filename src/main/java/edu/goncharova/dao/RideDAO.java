package edu.goncharova.dao;

import edu.goncharova.domain.Client;
import edu.goncharova.domain.Ride;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.transactions.ConnectionWrapper;
import edu.goncharova.transactions.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RideDAO {
    private final static Logger LOGGER = LogManager.getLogger(RideDAO.class);
    private final static String SQL_INSERT = "insert into ride(driverId,clientId,taxiId,cost,distance,rideStart,rideFinish)" +
            " values(?,?,?,?,?,?,?)";
    private final static String SQL_GET_MONEY_FOR_CLIENT = "select sum(cost) from ride where clientid=?";
    private final static String SQL_SELECT_ALL_CLIENTS_RIDES = "select * from ride where clientid=? order by rideid limit ?, ?";
    private final static String SQL_FIND_SIZE = "select count(*) from ride where clientid=? ";

    public boolean create(Ride entity) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            try (PreparedStatement statement = con.preparedStatement(SQL_INSERT)) {
                statement.setInt(1, entity.getDriverId());
                statement.setInt(2, entity.getClientId());
                statement.setInt(3, entity.getTaxiId());
                statement.setDouble(4, entity.getCost());
                statement.setDouble(5, entity.getDistance());
                statement.setDate(6, entity.getRideStart());
                statement.setDate(7, entity.getRideFinish());
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

    public double getMoneySpentForClient(Client client) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_GET_MONEY_FOR_CLIENT);
                statement.setInt(1, client.getClientId());
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getDouble("sum(cost)");
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                con.close();
            }
            return 0.0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public List<Ride> findRidesForClient(Client client, int from, int limit) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_SELECT_ALL_CLIENTS_RIDES);
                statement.setInt(1, client.getClientId());
                statement.setInt(2, from);
                statement.setInt(3, limit);
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                List<Ride> rides = new ArrayList<>();
                while (resultSet.next()) {
                    Ride ride = new Ride(resultSet.getInt("driverid"), resultSet.getInt("clientId"),
                            resultSet.getInt("taxiid"), resultSet.getDouble("cost"),
                            resultSet.getDouble("distance"), resultSet.getDate("ridestart"),
                            resultSet.getDate("ridefinish"));
                    ride.setRideId(resultSet.getInt("rideid"));
                    rides.add(ride);
                }
                return rides;
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

    public int findSize(Client client) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_FIND_SIZE);
                statement.setInt(1, client.getClientId());
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                int size = 0;
                if (resultSet.next()) {
                    size = resultSet.getInt(1);
                }
                return size;
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
}
