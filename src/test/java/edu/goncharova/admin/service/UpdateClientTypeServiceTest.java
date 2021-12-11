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


public class UpdateClientTypeServiceTest {
    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void updateClientType_UpdatesClientType() throws DAOException, SQLException {
        List<ClientType> clientTypeList = TableCreator.initClientTypeTable();
        ClientType clientType = new ClientType(30, "admin", 40000);
        clientType.setClientTypeId(clientTypeList.size());
        clientTypeList.set(clientTypeList.size() - 1, clientType);
        UpdateClientTypeService.getUpdateClientTypeService().updateClientType(clientType);
        assertEquals(clientTypeList.subList(1, clientTypeList.size()), DAOFactory.getInstance().getClientTypeDAO().findAll());
        TableCleaner.cleanClientTypeTable();
    }
}
