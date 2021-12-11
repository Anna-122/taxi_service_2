package edu.goncharova.dao;

import edu.goncharova.exceptions.DAOException;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.domain.Admin;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AdminDAOTest {
    private static List<Admin> allAdmins;


    @Before
    public void initDatabase() throws SQLException {
        allAdmins = TableCreator.initAdminTable();
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @After
    public void dropTable() throws SQLException {
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP_DATABASE = "DROP TABLE admin";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP_DATABASE);
        ps.execute();
    }

    @Test
    public void findByEmail_returnsRightAdmin_WhenRightEmail() throws SQLException, DAOException {
        final String EMAIL = "anna.s.goncharova.2001@gmail.com";
        AdminDAO adminDAO = DAOFactory.getInstance().getAdminDAO();
        assertEquals(allAdmins.get(0), adminDAO.findByEmail(EMAIL));
    }

    @Test
    public void findByEmail_returnsNull_WhenWrongEmail() throws SQLException, DAOException {
        final String EMAIL = "anna.s1.goncharova.2001@gmail.com";
        AdminDAO adminDAO = DAOFactory.getInstance().getAdminDAO();
        assertNull(adminDAO.findByEmail(EMAIL));
    }
}
