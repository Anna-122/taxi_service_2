package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.TaxiTypeDAO;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.command.UpdateTaxiTypeCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service for updating specific TaxiType record in database
 * @see UpdateTaxiTypeCommand
 */
public class UpdateTaxiTypeService {
    private final static Logger LOGGER = LogManager.getLogger(UpdateTaxiTypeService.class);
    private final static UpdateTaxiTypeService TAXI_TYPE_SERVICE = new UpdateTaxiTypeService();
    private final IDAOFactory daoFactory;
    private UpdateTaxiTypeService(){
        daoFactory= DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static UpdateTaxiTypeService getTaxiTypeService(){
        return TAXI_TYPE_SERVICE;
    }

    /**
     *
     * @param entity Specific TaxiType to update
     * @return true if updated successfully
     * @throws DAOException Re-throws DAOException from TaxiTypeDAO
     * @see TaxiTypeDAO#update(TaxiType)
     */
    public boolean updateTaxiType(TaxiType entity) throws DAOException {
        TaxiTypeDAO taxiTypeDAO = daoFactory.getTaxiTypeDAO();
        taxiTypeDAO.update(entity);
        return true;
    }
}
