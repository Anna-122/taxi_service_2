package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.TaxiTypeDAO;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.command.TaxiTypesCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 *
 * @see TaxiTypesCommand
 */
public class TaxiTypesService {
    private final static Logger LOGGER = LogManager.getLogger(TaxiTypesService.class);
    private final static TaxiTypesService TAXI_TYPES_SERVICE = new TaxiTypesService();
    private IDAOFactory daoFactory;
    private TaxiTypesService(){
        daoFactory = DAOFactory.getInstance();
    }

    /**
     *
     * @return Instance of this class
     */
    public static TaxiTypesService getTaxiTypesService(){
        return TAXI_TYPES_SERVICE;
    }

    /**
     *
     * @return List of all TaxiTypes in database
     * @throws DAOException Re-throws DAOException from TaxiTypeDAO
     * @see TaxiTypeDAO
     */
    public List<TaxiType> getAllTaxiTypes() throws DAOException{
        TaxiTypeDAO taxiTypeDAO = daoFactory.getTaxiTypeDAO();
        List<TaxiType> taxiTypes = taxiTypeDAO.findAll();
        return taxiTypes;
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
