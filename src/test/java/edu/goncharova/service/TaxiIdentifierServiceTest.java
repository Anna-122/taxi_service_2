package edu.goncharova.service;

import edu.goncharova.domain.Driver;
import edu.goncharova.domain.Taxi;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.services.TaxiIdentifierService;
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

public class TaxiIdentifierServiceTest {
    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @After
    public void cleanTables() throws SQLException {
        TableCleaner.cleanTaxiTable();
        TableCleaner.cleanTaxiTypeTable();
        TableCleaner.cleanDriverTable();
        TableCleaner.cleanUserTable();
    }


    @Test
    public void getTaxi_ReturnsNull_WhenBadCarNumber() throws SQLException, DAOException {
        TableCreator.initUserTable();
        TableCreator.initDriverTable();
        TableCreator.initTaxiTypeTable();
        List<Taxi> taxiList = TableCreator.initTaxiTable();
        assertNull(TaxiIdentifierService.getTaxiIdentifierService().getTaxi(taxiList.get(0).getCarNumber() + "1"));
    }

    @Test
    public void getDriver_ReturnsDriver_WhenRightTaxi() throws SQLException, DAOException {
        TableCreator.initUserTable();
        List<Driver> driverList = TableCreator.initDriverTable();
        TableCreator.initTaxiTypeTable();
        List<Taxi> taxiList = TableCreator.initTaxiTable();
        assertEquals(driverList.get(0), TaxiIdentifierService.getTaxiIdentifierService().getDriver(taxiList.get(0)));
    }

    @Test
    public void getTaxi_ReturnsNull_WhenBadTaxiId() throws SQLException, DAOException {
        TableCreator.initUserTable();
        TableCreator.initDriverTable();
        TableCreator.initTaxiTypeTable();
        List<Taxi> taxiList = TableCreator.initTaxiTable();
        assertNull(TaxiIdentifierService.getTaxiIdentifierService().getTaxi(taxiList.get(0).getTaxiId() + 10));
    }

    @Test
    public void getTaxiTypeByName_ReturnsTaxiType_WhenRightName() throws SQLException, DAOException {
        TableCreator.initUserTable();
        TableCreator.initDriverTable();
        List<TaxiType> taxiTypes = TableCreator.initTaxiTypeTable();
        TableCreator.initTaxiTable();
        for (TaxiType taxiType : taxiTypes) {
            assertEquals(taxiType, TaxiIdentifierService.getTaxiIdentifierService().getTaxiTypeByName(taxiType.getTaxiTypeName()));
        }
    }
}
