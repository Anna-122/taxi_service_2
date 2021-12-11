package edu.goncharova.service;

import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.dao.IDAOFactory;
import edu.goncharova.dao.UserDAO;
import edu.goncharova.domain.User;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.services.LoginService;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.*;

import java.sql.SQLException;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    @Before
    public void initDatabase() throws SQLException {
        TableCreator.initUserTable();
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @After
    public void dropTable() throws SQLException {
        TableCleaner.cleanUserTable();
    }

    @Test
    public void getUser_returnsNull_whenBadData_mockingDAO() throws DAOException {
        final String TEST_EMAIL = "abs@gmail.com", TEST_PASSWORD = "qweerty";
        final User RIGHT_ANSWER = new User(TEST_EMAIL, TEST_PASSWORD, null, null);
        IDAOFactory daoFactory = mock(IDAOFactory.class);
        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.findByEmail(TEST_EMAIL)).thenReturn(RIGHT_ANSWER);
        when(daoFactory.getUserDAO()).thenReturn(userDAO);
        LoginService loginService = LoginService.getLoginService();
        loginService.setDaoFactory(daoFactory);
        User answer = loginService.getUser(TEST_EMAIL, TEST_PASSWORD + "1");
        assertNull(answer);
    }
}
