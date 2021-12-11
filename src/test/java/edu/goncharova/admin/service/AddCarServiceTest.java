package edu.goncharova.admin.service;

import edu.goncharova.domain.Driver;
import edu.goncharova.domain.Taxi;
import edu.goncharova.exceptions.AddCarException;
import edu.goncharova.exceptions.DAOException;
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
import static org.junit.Assert.assertNull;

public class AddCarServiceTest {

    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void findDriverByUserId_ReturnsCorrectDriver() throws DAOException, SQLException {
        TableCreator.initUserTable();
        List<Driver> allDrivers = TableCreator.initDriverTable();
        TableCreator.initTaxiTypeTable();
        TableCreator.initTaxiTable();
        Driver driver = allDrivers.get(0);
        assertEquals(driver, AddCarService.getAddCarService().findDriverByUserId(driver.getUserId()));
    }

    @Test
    public void findDriverByUserId_ReturnsNull_WhenThereIsNoSuchDriver() throws DAOException, SQLException {
        TableCreator.initUserTable();
        TableCreator.initDriverTable();
        TableCreator.initTaxiTypeTable();
        TableCreator.initTaxiTable();
        assertNull(AddCarService.getAddCarService().findDriverByUserId(100));
    }


    @Test(expected = AddCarException.class)
    public void addCar_ThrowsException_WhenThereExistsThisCar() throws DAOException, SQLException, AddCarException {
        TableCreator.initUserTable();
        TableCreator.initDriverTable();
        TableCreator.initTaxiTypeTable();
        List<Taxi> allTaxies = TableCreator.initTaxiTable();
        Taxi taxi = new Taxi(1, 1, allTaxies.get(0).getCarNumber());
        taxi.setTaxiId(allTaxies.size() + 1);
        AddCarService.getAddCarService().addCar(taxi);

    }

    @After
    public void cleanTables() throws SQLException {
        TableCleaner.cleanTaxiTable();
        TableCleaner.cleanTaxiTypeTable();
        TableCleaner.cleanDriverTable();
        TableCleaner.cleanUserTable();
    }
}
