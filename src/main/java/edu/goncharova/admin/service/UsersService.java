package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.UserDAO;
import edu.goncharova.domain.User;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.command.UsersCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Service for getting specific amount of users to display it
 * @see UsersCommand
 */
public class UsersService {
    private final static Logger LOGGER = LogManager.getLogger(UsersService.class);
    private final static UsersService usersService = new UsersService();
    private IDAOFactory daoFactory;
    private UsersService(){
        daoFactory = DAOFactory.getInstance();
    }
    /**
     *
     * @return Instance of this class
     */
    public static UsersService getUsersService(){
        return usersService;
    }

    /**
     *
     * @return Total number of user records in database
     * @throws DAOException Re-throws DAOException from UserDAO
     * @see UserDAO#findSize()
     */
    public int getTableSize() throws DAOException {
        UserDAO userDAO = daoFactory.getUserDAO();
        return userDAO.findSize();
    }

    /**
     *
     * @param from How much records to skip
     * @param limit How much records should be returned
     * @return List of users to display
     * @throws DAOException Re-throws DAOException from UserDAO
     * @see UserDAO#findLimitedAmount(int, int)
     */
    public List<User> getUsers(int from, int limit) throws DAOException{
        UserDAO userDAO = daoFactory.getUserDAO();
        List<User> users = userDAO.findLimitedAmount(from,limit);
        return users;
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
