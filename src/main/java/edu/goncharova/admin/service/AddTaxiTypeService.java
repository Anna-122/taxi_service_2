package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.TaxiTypeDAO;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.command.AddTaxiTypeCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *  Service for adding specific TaxiType to database
 *  @see AddTaxiTypeCommand
 */
public class AddTaxiTypeService {
    private final static Logger LOGGER = LogManager.getLogger(AddTaxiTypeService.class);
    private final static AddTaxiTypeService ADD_TAXI_TYPE_SERVICE = new AddTaxiTypeService();
    private IDAOFactory daoFactory;
    private AddTaxiTypeService(){
        daoFactory= DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static AddTaxiTypeService getAddTaxiTypeService(){
        return ADD_TAXI_TYPE_SERVICE;
    }

    /**
     * @param taxiType TaxiType to add to database
     * @return true if successfully added taxiType to database
     * @throws DAOException Re-throws DAOException from TaxiTypeDAO method
     * @see TaxiTypeDAO#create(TaxiType)
     */
    public boolean addTaxiType(TaxiType taxiType)throws DAOException{
        TaxiTypeDAO taxiTypeDAO = daoFactory.getTaxiTypeDAO();
        taxiTypeDAO.create(taxiType);
        return true;
    }

}
