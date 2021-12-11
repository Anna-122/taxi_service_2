package edu.goncharova.admin.service;

import edu.goncharova.domain.ClientType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ClientTypesServiceTest {

    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void getAllClientTypes_ReturnsAllClientType() throws DAOException, SQLException {
        List<ClientType> allClientTypes = TableCreator.initClientTypeTable();
        assertEquals(allClientTypes.subList(1, allClientTypes.size()), ClientTypesService.getClientTypesService().getAllClientTypes());
        TableCleaner.cleanClientTypeTable();
    }
}
