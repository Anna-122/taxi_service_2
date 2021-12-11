package edu.goncharova.services;

import edu.goncharova.domain.Driver;
import edu.goncharova.domain.Taxi;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.command.CostCalculationCommand;
import edu.goncharova.command.RideCommand;
import edu.goncharova.command.RideStatisticsCommand;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service for getting Taxi from it's car number or id and determining it's driver
 *
 * @see RideCommand
 * @see RideStatisticsCommand
 * @see CostCalculationCommand
 */
public class TaxiIdentifierService {
    private final static Logger LOGGER = LogManager.getLogger(TaxiIdentifierService.class);
    private final static TaxiIdentifierService TAXI_IDENTIFIER_SERVICE = new TaxiIdentifierService();
    private final IDAOFactory daoFactory;

    private TaxiIdentifierService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static TaxiIdentifierService getTaxiIdentifierService() {
        return TAXI_IDENTIFIER_SERVICE;
    }

    /**
     * @param carNumber Car number of taxi
     * @return Taxi object if there is a taxi ith such car number, null otherwise
     * @throws DAOException Re-throws DAOExceptions from TaxiDAO
     * @see TaxiDAO#findByCarNumber(String)
     */
    public Taxi getTaxi(String carNumber) throws DAOException {
        TaxiDAO taxiDAO = daoFactory.getTaxiDAO();
        return taxiDAO.findByCarNumber(carNumber);
    }

    /**
     * @param taxi Taxi to find it's driver
     * @return Driver if there is a Driver with such id or null otherwise
     * @throws DAOException Re-throws DAOException from DriverDAO
     * @see DriverDAO#findById(int)
     */
    public Driver getDriver(Taxi taxi) throws DAOException {
        DriverDAO driverDAO = daoFactory.getDriverDAO();
        return driverDAO.findById(taxi.getDriverId());
    }

    /**
     * @param taxiId Id of taxi
     * @return Taxi if there is a Taxi with such id or null otherwise
     * @throws DAOException Re-throws DAOException from TaxiDAO
     */
    public Taxi getTaxi(int taxiId) throws DAOException {
        TaxiDAO taxiDAO = daoFactory.getTaxiDAO();
        return taxiDAO.findById(taxiId);
    }

    public TaxiType getTaxiTypeByName(String name) throws DAOException {
        TaxiTypeDAO taxiTypeDAO = daoFactory.getTaxiTypeDAO();
        return taxiTypeDAO.findByName(name);
    }
}
