package edu.goncharova.services;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.UserDAO;
import edu.goncharova.domain.User;
import edu.goncharova.command.LoginCommand;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.utils.EncryptedPassword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;

/**
 * Service for authorization
 *
 * @see LoginCommand
 */
public class LoginService {
    private final static Logger LOGGER = LogManager.getLogger(LoginService.class);
    private final static LoginService loginService = new LoginService();
    private IDAOFactory daoFactory;

    private LoginService() {
        daoFactory = DAOFactory.getInstance();
    }

    /**
     * @return Instance of this class
     */
    public static LoginService getLoginService() {
        return loginService;
    }

    /**
     * @param email    Email that user entered
     * @param password Password that user entered (not encrypted)
     * @return User if there exists a user with such email and password, null otherwise
     * @throws DAOException Re-throws DAOException from UserDAO
     * @see UserDAO#findByEmail(String)
     */
    public User getUser(String email, String password) throws DAOException {
        UserDAO userDAO = daoFactory.getUserDAO();
        User user = userDAO.findByEmail(email);
        String passwordEncrypted = EncryptedPassword.getSHA512(password);
        if (user == null) {
            return null;
        }
        LOGGER.info("User " + email + " tried to login.");
        return (passwordEncrypted.equals(user.getPassword())) ? user : null;
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
