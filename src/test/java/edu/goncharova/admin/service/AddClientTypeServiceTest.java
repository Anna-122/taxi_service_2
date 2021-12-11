package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
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

public class AddClientTypeServiceTest {
    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void addsClientType() throws DAOException, SQLException {
        List<ClientType> allClientTypes = TableCreator.initClientTypeTable();
        ClientType clientType = new ClientType(30, "best", 50000.0);
        clientType.setClientTypeId(allClientTypes.size() + 1);
        AddClientTypeService.getAddClientTypeService().addClientType(clientType);
        allClientTypes.add(clientType);
        assertEquals(allClientTypes.subList(1, allClientTypes.size()), DAOFactory.getInstance().getClientTypeDAO().findAll());
        TableCleaner.cleanClientTypeTable();
    }

}
