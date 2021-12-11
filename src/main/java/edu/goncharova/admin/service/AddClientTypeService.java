package edu.goncharova.admin.service;

import edu.goncharova.dao.ClientTypeDAO;
import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.domain.ClientType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.command.AddClientTypeCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service for adding ClientType to database
 *
 * @see AddClientTypeCommand
 */
public class AddClientTypeService {
    private final static Logger LOGGER = LogManager.getLogger(AddClientTypeService.class);
    private final static AddClientTypeService ADD_CLIENT_TYPE_SERVICE = new AddClientTypeService();
    private final IDAOFactory daoFactory;

    private AddClientTypeService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static AddClientTypeService getAddClientTypeService() {
        return ADD_CLIENT_TYPE_SERVICE;
    }

    /**
     * @param clientType ClientType to add
     * @return true if successfully added clientType to database
     * @throws DAOException Re-throws DAOException from ClientTypeDAO method
     * @see ClientTypeDAO#create(ClientType)
     */
    public boolean addClientType(ClientType clientType) throws DAOException {
        ClientTypeDAO clientTypeDAO = daoFactory.getClientTypeDAO();
        clientTypeDAO.create(clientType);
        return true;
    }

}
