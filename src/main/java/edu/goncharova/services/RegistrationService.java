package edu.goncharova.services;


import edu.goncharova.dao.ClientDAO;
import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.UserDAO;
import edu.goncharova.domain.Client;
import edu.goncharova.domain.User;
import edu.goncharova.command.RegistrationCommand;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.exceptions.RegistrationException;
import edu.goncharova.exceptions.TransactionException;
import edu.goncharova.transactions.TransactionManager;
import edu.goncharova.utils.EncryptedPassword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Service for registration and adding user to database
 *
 * @see RegistrationCommand
 */
public class RegistrationService {
    private final static Logger LOGGER = LogManager.getLogger(RegistrationService.class);
    private final static RegistrationService registrationService = new RegistrationService();
    private IDAOFactory daoFactory;

    private RegistrationService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static RegistrationService getRegistrationService() {
        return registrationService;
    }

    /**
     * @param email    Email that user entered
     * @param password Password that user entered
     * @param name     Name
     * @param surname  Surname
     * @return true if registered successfully
     * @throws RegistrationException If there is already a user with same email and password
     * @throws SQLException          Re-throws SQLException from TransactionManager
     * @throws TransactionException  Re-throws TransactionException from TransactionManager
     * @throws DAOException          Re-throws DAO Exception from UserDAO | ClientDAO
     * @see TransactionManager
     * @see UserDAO#findByEmail(String)
     * @see UserDAO#create(User)
     * @see ClientDAO#create(Client)
     */
    public boolean register(String email, String password, String name, String surname) throws RegistrationException, SQLException, TransactionException, DAOException {
        UserDAO userDAO = daoFactory.getUserDAO();
        ClientDAO clientDAO = daoFactory.getClientDAO();
        TransactionManager.beginTransaction();
        String passwordEncrypted = EncryptedPassword.getSHA512(password);
        if (userDAO.findByEmail(email) == null) {
            User user = new User(email, passwordEncrypted, name, surname);
            try {
                userDAO.create(user);
                user = userDAO.findByEmail(email);
                LOGGER.info("Created new user : {}", user);
                Client client = new Client(user.getUserId());
                clientDAO.create(client);
                LOGGER.info("Created new client: {}", client);
                return true;
            } catch (DAOException e) {
                LOGGER.error(e.getMessage());
                throw new RegistrationException(e);
            } finally {
                TransactionManager.endTransaction();
            }
        } else {
            TransactionManager.endTransaction();
            throw new RegistrationException("This email is already occupied");
        }
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
