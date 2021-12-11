package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.dao.AdminDAO;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.domain.Admin;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.command.LoginCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Service for authorization into admin interface
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
     * @param email    Email
     * @param password Password (not encrypted)
     * @return Admin object if there is an admin in database with such email and password, null otherwise
     * @throws DAOException Re-throws DAOException from AdminDAO
     * @see AdminDAO#findByEmail(String)
     */
    public Admin getAdmin(String email, String password) throws DAOException, SQLException {
        AdminDAO adminDAO = daoFactory.getAdminDAO();
        Admin admin = adminDAO.findByEmail(email);
        if (admin == null) {
            return null;
        }
        LOGGER.info(email + " tried to login into admin.");
        return (password.equals(admin.getPassword())) ? admin : null;
    }

    public void setDaoFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
