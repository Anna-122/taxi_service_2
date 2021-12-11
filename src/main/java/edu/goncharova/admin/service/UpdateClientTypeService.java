package edu.goncharova.admin.service;

import edu.goncharova.dao.ClientTypeDAO;
import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.domain.ClientType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.command.UpdateClientTypeCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service for changing specific ClientType record in database
 * @see UpdateClientTypeCommand
 */
public class UpdateClientTypeService {
    private final static Logger LOGGER = LogManager.getLogger(UpdateClientTypeService.class);
    private final static UpdateClientTypeService UPDATE_CLIENT_TYPE_SERVICE = new UpdateClientTypeService();
    private final IDAOFactory daoFactory;
    private UpdateClientTypeService(){
        daoFactory= DAOFactory.getInstance();
    }

    /**
     *
     * @return Instance of this class
     */
    public static UpdateClientTypeService getUpdateClientTypeService(){
        return UPDATE_CLIENT_TYPE_SERVICE;
    }

    /**
     *
     * @param entity Specific ClientType to update
     * @return true if updated successfully
     * @throws DAOException Re-throws DAOException from ClientTypeDAO
     * @see ClientTypeDAO#update(ClientType)
     */
    public boolean updateClientType(ClientType entity) throws DAOException {
        ClientTypeDAO clientTypeDAO = daoFactory.getClientTypeDAO();
        clientTypeDAO.update(entity);
        return true;
    }
}
