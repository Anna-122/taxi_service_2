package edu.goncharova.admin.service;

import edu.goncharova.dao.*;
import edu.goncharova.domain.Client;
import edu.goncharova.domain.Driver;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.exceptions.TransactionException;
import edu.goncharova.transactions.TransactionManager;
import edu.goncharova.admin.command.DeleteCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Service for deleting specific user record from database and related to it Client and Driver records
 *
 * @see DeleteCommand
 */
public class DeleteService {
    private final static Logger LOGGER = LogManager.getLogger(DeleteService.class);
    private final static DeleteService DELETE_SERVICE = new DeleteService();
    private final IDAOFactory daoFactory;

    private DeleteService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static DeleteService getDeleteService() {
        return DELETE_SERVICE;
    }

    /**
     * Deletes user and related to him client and driver entities from database
     *
     * @param idToDelete Id of user that admin tried to delete
     * @return true if deletes successfully
     * @throws DAOException Re-throws DAOException from UserDAO, ClientDAO or DriverDAO methods
     * @see UserDAO#delete(int)
     * @see ClientDAO#delete(Client)
     * @see DriverDAO#delete(Driver)
     */
    public boolean delete(int idToDelete) throws DAOException {
        UserDAO userDAO = daoFactory.getUserDAO();
        ClientDAO clientDAO = daoFactory.getClientDAO();
        DriverDAO driverDAO = daoFactory.getDriverDAO();
        try {
            TransactionManager.beginTransaction();
            clientDAO.delete(new Client(idToDelete));
            Driver driver = driverDAO.findByUserId(idToDelete);
            if (driver != null) {
                driverDAO.delete(driver);
            }
            userDAO.delete(idToDelete);
            TransactionManager.endTransaction();
        } catch (TransactionException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return true;
    }
}
