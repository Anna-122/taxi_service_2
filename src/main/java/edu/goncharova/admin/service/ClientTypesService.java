package edu.goncharova.admin.service;

import edu.goncharova.dao.ClientTypeDAO;
import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.domain.ClientType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.command.ClientTypesCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Service for getting all ClientType objects from database as a List
 * @see ClientTypesCommand
 */
public class ClientTypesService {
    private final static Logger LOGGER = LogManager.getLogger(ClientTypesService.class);
    private final static ClientTypesService CLIENT_TYPES_SERVICE = new ClientTypesService();
    private IDAOFactory daoFactory = DAOFactory.getInstance();
    private ClientTypesService(){
        daoFactory = DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static ClientTypesService getClientTypesService(){
        return CLIENT_TYPES_SERVICE;
    }

    /**
     * @return All ClientTypes from database
     * @throws DAOException Re-throws DAOException from ClientTypeDAO
     * @see ClientTypeDAO
     */
    public List<ClientType> getAllClientTypes() throws DAOException{
        ClientTypeDAO clientTypeDAO = daoFactory.getClientTypeDAO();
        List<ClientType> clientTypes = clientTypeDAO.findAll();
        return clientTypes;
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
