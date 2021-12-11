package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.domain.Client;
import edu.goncharova.domain.Driver;
import edu.goncharova.domain.User;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DeleteServiceTest {
    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void deleteUser_DeletesUserClientAndDriver() throws SQLException, DAOException {
        List<User> userList = TableCreator.initUserTable();
        List<Client> clientList = TableCreator.initClientTable();
        List<Driver> driverList = TableCreator.initDriverTable();
        int idToDelete = userList.get(0).getUserId();
        DeleteService.getDeleteService().delete(idToDelete);
        userList.remove(0);
        Client client = clientList.remove(0);
        Driver driver = driverList.remove(0);
        DAOFactory daoFactory = DAOFactory.getInstance();
        assertTrue(userList.equals(daoFactory.getUserDAO().findLimitedAmount(0, userList.size())) &&
                daoFactory.getClientDAO().findClientByUserId(idToDelete) == null &&
                daoFactory.getDriverDAO().findByUserId(idToDelete) == null);
        TableCleaner.cleanDriverTable();
        TableCleaner.cleanClientTable();
        TableCleaner.cleanUserTable();
    }
}
