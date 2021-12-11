package edu.goncharova.services;

import edu.goncharova.domain.TaxiType;
import edu.goncharova.command.RideOrderCommand;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.TaxiTypeDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Service for getting all taxitypes to show them to user
 * @see RideOrderCommand
 */
public class RideOrderService {
    private final static Logger LOGGER = LogManager.getLogger(RideOrderService.class);
    private final static RideOrderService RIDE_ORDER_SERVICE = new RideOrderService();
    private final IDAOFactory daoFactory;
    private RideOrderService(){
        daoFactory= DAOFactory.getInstance();
    }

    /**
     *
     * @return Instance of this class
     */
    public static RideOrderService getRideOrderService(){
        return RIDE_ORDER_SERVICE;
    }

    /**
     *
     * @return List of all TaxiTypes
     * @throws DAOException Re-throws DAOExceptions from TaxiTypeDAO
     * @see TaxiTypeDAO#findAll()
     */
    public List<TaxiType> getAllTaxiTypes() throws DAOException {
        TaxiTypeDAO taxiTypeDAO = daoFactory.getTaxiTypeDAO();
        return taxiTypeDAO.findAll();
    }


}
