package edu.goncharova.services;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.TaxiDAO;
import edu.goncharova.domain.Taxi;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.command.RideCommand;
import edu.goncharova.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Random;

/**
 * Service for getting free car for client
 *
 * @see RideCommand
 */
public class GetCarService {
    private final static Logger LOGGER = LogManager.getLogger(GetCarService.class);
    private final static GetCarService getCarService = new GetCarService();
    private IDAOFactory daoFactory;

    private GetCarService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static GetCarService getGetCarService() {
        return getCarService;
    }

    /**
     * Not implemented now, returns one of all of cars in database
     *
     * @param taxiType Type of taxi to get
     * @return Taxi record
     * @throws DAOException Re-throws DAOException from TaxiDAO
     * @see TaxiDAO#findById(int)
     */
    public Taxi getCar(TaxiType taxiType) throws DAOException {
        TaxiDAO taxiDAO = daoFactory.getTaxiDAO();
        int carNumber = taxiDAO.findNumberOfSpecifiedTaxiType(taxiType);
        int toSkip = new Random(new Date().getTime()).nextInt(carNumber + 1);
        return taxiDAO.findNthCar(toSkip, taxiType);
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
