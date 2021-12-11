package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
import edu.goncharova.domain.Driver;
import edu.goncharova.domain.User;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.exceptions.DriverRegistrationException;
import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class RegisterDriverServiceTest {

    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void registerDriver_RegistersNewDriver() throws SQLException, DriverRegistrationException, DAOException {
        List<User> userList = TableCreator.initUserTable();
        List<Driver> driverList = TableCreator.initDriverTable();
        Driver driver = new Driver(userList.get(3).getUserId());
        driver.setDriverId(driverList.size() + 1);
        RegisterDriverService.getRegisterDriverService().registerDriver(driver.getUserId());
        assertEquals(driver, DAOFactory.getInstance().getDriverDAO().findByUserId(driver.getUserId()));
    }

    @Test(expected = DriverRegistrationException.class)
    public void registerDriver_ThrowsExceptionIfUserIsADriver() throws SQLException, DriverRegistrationException, DAOException {
        List<User> userList = TableCreator.initUserTable();
        List<Driver> driverList = TableCreator.initDriverTable();
        Driver driver = new Driver(userList.get(0).getUserId());
        driver.setDriverId(driverList.size() + 1);
        RegisterDriverService.getRegisterDriverService().registerDriver(driver.getUserId());

    }

    @After
    public void cleanTables() throws SQLException {
        TableCleaner.cleanDriverTable();
        TableCleaner.cleanUserTable();
    }
}
