package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.DriverDAO;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.domain.Driver;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.exceptions.DriverRegistrationException;
import edu.goncharova.admin.command.RegisterDriverCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service for registering user as driver
 *
 * @see RegisterDriverCommand
 */
public class RegisterDriverService {
    private final static Logger LOGGER = LogManager.getLogger(RegisterDriverService.class);
    private final static RegisterDriverService REGISTER_DRIVER_SERVICE = new RegisterDriverService();
    private final IDAOFactory daoFactory;

    private RegisterDriverService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static RegisterDriverService getRegisterDriverService() {
        return REGISTER_DRIVER_SERVICE;
    }

    /**
     * @param userId id of specific user which was registered as driver
     * @return true if registration is successful
     * @throws DAOException                Re-throws DAOException from DriverDAO
     * @throws DriverRegistrationException if this user is already a driver
     * @see DriverDAO#create(Driver)
     * @see DriverDAO#findByUserId(int)
     */
    public boolean registerDriver(int userId) throws DAOException, DriverRegistrationException {
        DriverDAO driverDAO = daoFactory.getDriverDAO();
        if (driverDAO.findByUserId(userId) != null) {
            throw new DriverRegistrationException("User is already a driver");
        }
        return driverDAO.create(new Driver(userId));
    }
}
