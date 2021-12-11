package edu.goncharova.dao;


import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.domain.ClientType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClientTypeDAOTest {
    private static List<ClientType> allClientTypes;
    @Before
    public void initDatabase() throws SQLException {
        allClientTypes= TableCreator.initClientTypeTable();
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @After
    public void dropTable() throws SQLException {
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP_DATABASE = "DROP TABLE clienttype";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP_DATABASE);
        ps.execute();
    }

    @Test
    public void findAll_ReturnAllClientTypes_Excluding_First() throws DAOException {
        final List<ClientType> RIGHT_ANSWER = allClientTypes.subList(1, allClientTypes.size());
        ClientTypeDAO clientTypeDAO = DAOFactory.getInstance().getClientTypeDAO();
        assertEquals(RIGHT_ANSWER,clientTypeDAO.findAll());
    }

    @Test
    public void findDiscountByMoneySpent_ReturnsRightValue_WhenNoMoneySpent() throws DAOException {
        final int RIGHT_ANSWER = 0;
        final double MONEY_SPENT=0;
        ClientTypeDAO clientTypeDAO = DAOFactory.getInstance().getClientTypeDAO();
        assertEquals(RIGHT_ANSWER,clientTypeDAO.findDiscountByMoneySpent(MONEY_SPENT));
    }

    @Test
    public void findDiscountByMoneySpent_ReturnsRightValue_WhenSomeMoneySpent() throws DAOException {
        final int RIGHT_ANSWER = 3;
        final double MONEY_SPENT=2000;
        ClientTypeDAO clientTypeDAO = DAOFactory.getInstance().getClientTypeDAO();
        assertEquals(RIGHT_ANSWER,clientTypeDAO.findDiscountByMoneySpent(MONEY_SPENT));
    }

    @Test
    public void findDiscountByMoneySpent_ReturnsRightValue_WhenMoneySpentIsEdgeValue() throws DAOException {
        final int RIGHT_ANSWER = 3;
        final double MONEY_SPENT=1000;
        ClientTypeDAO clientTypeDAO = DAOFactory.getInstance().getClientTypeDAO();
        assertEquals(RIGHT_ANSWER,clientTypeDAO.findDiscountByMoneySpent(MONEY_SPENT));
    }
    @Test
    public void create_AddsClientTypeToDatabase() throws DAOException {
        final ClientType clientType = new ClientType(25,"admin",30000.0);
        clientType.setClientTypeId(5);
        ClientTypeDAO clientTypeDAO = DAOFactory.getInstance().getClientTypeDAO();
        clientTypeDAO.create(clientType);
        allClientTypes.add(clientType);
        assertEquals(allClientTypes.subList(1,allClientTypes.size()),clientTypeDAO.findAll());
    }

    @Test
    public void update_ChangesRecordInDatabase()throws DAOException{
        final ClientType clientType = new ClientType(11,"user",11000);
        clientType.setClientTypeId(4);
        ClientTypeDAO clientTypeDAO = DAOFactory.getInstance().getClientTypeDAO();
        clientTypeDAO.update(clientType);
        allClientTypes.remove(3);
        allClientTypes.add(clientType);
        assertEquals(allClientTypes.subList(1,allClientTypes.size()),clientTypeDAO.findAll());
    }



}
