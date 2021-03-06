package edu.goncharova.dao;

import edu.goncharova.domain.User;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserDAOTest {
    private static List<User> allUsers;
    @Before
    public void initDatabase() throws SQLException {
        allUsers = TableCreator.initUserTable();
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }
    @After
    public void dropTable() throws SQLException {
        TableCleaner.cleanUserTable();
    }
    @Test
    public void findSize_ReturnsCorrectSize() throws DAOException {
        final int RIGHT_SIZE = 4;
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        assertEquals(RIGHT_SIZE,userDAO.findSize());
    }
    @Test
    public void findLimitedAmount_ReturnsCorrectSize() throws DAOException{
        final int RIGHT_SIZE = 2;
        UserDAO userDAO=DAOFactory.getInstance().getUserDAO();
        assertEquals(RIGHT_SIZE,userDAO.findLimitedAmount(0,2).size());
    }

    @Test
    public void findLimitedAmount_ReturnsRightUsers_WhenSkippingSomeUsers() throws DAOException {
        final int FROM=3, LIMIT = 1;
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        assertEquals(allUsers.get(3),userDAO.findLimitedAmount(FROM,LIMIT).get(0));
    }
    @Test
    public void createsUser() throws DAOException {
        final String EMAIL = "olyer@gmail.com";
        final User user = new User(EMAIL,"olyer15","olexandr", "yermilov");

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        userDAO.create(user);
        User userFromDB = userDAO.findByEmail(EMAIL);
        user.setUserId(userFromDB.getUserId());
        assertEquals(user,userFromDB);
    }
    @Test
    public void deletesUser() throws DAOException {
        final String EMAIL = "olyer@gmail.com";
        final User user = new User(EMAIL,"olyer15","olexandr", "yermilov");
        final int RIGHT_SIZE = 4;

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        userDAO.create(user);
        user.setUserId(userDAO.findByEmail(EMAIL).getUserId());
        userDAO.delete(user.getUserId());
        assertEquals(RIGHT_SIZE, userDAO.findSize());
    }
    @Test
    public void findByEmail_ReturnsNull_WhenThereIsNoSuchUser() throws DAOException {
        final String EMAIL = "olyer@gmail.com";
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        assertNull(userDAO.findByEmail(EMAIL));
    }
}
