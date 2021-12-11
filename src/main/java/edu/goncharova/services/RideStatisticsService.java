package edu.goncharova.services;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.RideDAO;
import edu.goncharova.domain.Client;
import edu.goncharova.domain.Driver;
import edu.goncharova.domain.Ride;
import edu.goncharova.domain.Taxi;
import edu.goncharova.command.RideCommand;
import edu.goncharova.command.RideStatisticsCommand;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.List;

/**
 * Service for calculating different data about rides
 *
 * @see RideCommand
 * @see RideStatisticsCommand
 */
public class RideStatisticsService {
    private final static Logger LOGGER = LogManager.getLogger(RideStatisticsService.class);
    private final static RideStatisticsService RIDE_STATISTICS_SERVICE = new RideStatisticsService();
    private final IDAOFactory daoFactory;

    private RideStatisticsService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static RideStatisticsService getRideStatisticsService() {
        return RIDE_STATISTICS_SERVICE;
    }

    /**
     * @param client Client entity to calculate money it spent
     * @return Money the client spent
     * @throws DAOException Re-throws exception from RideDAO
     * @see RideDAO#getMoneySpentForClient(Client)
     */
    public double getClientsSpentMoney(Client client) throws DAOException {
        RideDAO rideDAO = daoFactory.getRideDAO();
        return rideDAO.getMoneySpentForClient(client);
    }

    /**
     * @param client     Client
     * @param driver     Driver
     * @param taxi       Taxi
     * @param cost       Cost
     * @param distance   Distance
     * @param rideStart  Time when Ride started
     * @param rideFinish Time when Ride finished
     * @return true if recorded successfully
     * @throws DAOException Re-throws DAOException from putRideToDatabase
     * @see #putRideToDatabase(Ride)
     */
    public boolean putRideToDatabase(Client client, Driver driver, Taxi taxi, double cost, double distance,
                                     Date rideStart, Date rideFinish) throws DAOException {
        Ride ride = new Ride(driver.getDriverId(), client.getClientId(), taxi.getTaxiId(), cost, distance, rideStart, rideFinish);
        return putRideToDatabase(ride);
    }

    /**
     * @param ride Ride to put to database
     * @return true if created record successfully
     * @throws DAOException Re-throws DAOException from RideDAO
     * @see RideDAO#create(Ride)
     * @see #putRideToDatabase(Client, Driver, Taxi, double, double, Date, Date)
     */
    public boolean putRideToDatabase(Ride ride) throws DAOException {
        RideDAO rideDAO = daoFactory.getRideDAO();
        return rideDAO.create(ride);
    }

    /**
     * @param client Client
     * @param from   Number of rides to skip
     * @param limit  Number of rides to display
     * @return Specific number of client's rides as java.util.List
     * @throws DAOException Re-throws DAOException from RideDAO
     * @see RideDAO#findRidesForClient(Client, int, int)
     */
    public List<Ride> getClientsRides(Client client, int from, int limit) throws DAOException {
        RideDAO rideDAO = daoFactory.getRideDAO();
        return rideDAO.findRidesForClient(client, from, limit);
    }

    /**
     * @param client Client to show statistics for
     * @return Total number of rides for specific client
     * @throws DAOException Re-throws DAOException from RideDAO
     * @see RideDAO#findSize(Client)
     */
    public int getTableSize(Client client) throws DAOException {
        RideDAO rideDAO = daoFactory.getRideDAO();
        return rideDAO.findSize(client);
    }
}
