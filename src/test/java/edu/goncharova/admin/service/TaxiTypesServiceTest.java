package edu.goncharova.admin.service;

import edu.goncharova.domain.TaxiType;
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


public class TaxiTypesServiceTest {

    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void getAllTaxiTypes_ReturnsAllTaxiTypes() throws DAOException, SQLException {
        List<TaxiType> taxiTypes = TableCreator.initTaxiTypeTable();
        assertEquals(taxiTypes, TaxiTypesService.getTaxiTypesService().getAllTaxiTypes());
        TableCleaner.cleanTaxiTypeTable();
    }
}
