package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.DriverDAO;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.TaxiDAO;
import edu.goncharova.domain.Driver;
import edu.goncharova.domain.Taxi;
import edu.goncharova.exceptions.AddCarException;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.command.AddCarCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service for adding car of specific driver to database
 *
 * @see AddCarCommand
 */
public class AddCarService {
    private final static Logger LOGGER = LogManager.getLogger(AddCarService.class);
    private final static AddCarService ADD_CAR_SERVICE = new AddCarService();
    private final IDAOFactory daoFactory;

    private AddCarService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static AddCarService getAddCarService() {
        return ADD_CAR_SERVICE;
    }

    /**
     * @param userId Id of user to find driver with the same userid
     * @return Driver if there is a driver with such userid or null if there isn't
     * @throws DAOException Re-throws DAOException from DriverDAO method
     * @see DriverDAO
     */
    public Driver findDriverByUserId(int userId) throws DAOException {
        DriverDAO driverDAO = daoFactory.getDriverDAO();
        return driverDAO.findByUserId(userId);
    }

    /**
     * @param taxi Taxi to add to database
     * @return true if created taxi successfully
     * @throws DAOException    Re-throws DAOException from TaxiDAO method
     * @throws AddCarException If there is already a car with such carNumber
     * @see TaxiDAO#create(Taxi)
     */
    public boolean addCar(Taxi taxi) throws DAOException, AddCarException {
        TaxiDAO taxiDAO = daoFactory.getTaxiDAO();
        if (taxiDAO.findByCarNumber(taxi.getCarNumber()) == null) {
            taxiDAO.create(taxi);
        } else {
            throw new AddCarException("There is already a car with such car number");
        }
        return true;
    }

}
