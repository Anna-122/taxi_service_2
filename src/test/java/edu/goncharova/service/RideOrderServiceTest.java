package edu.goncharova.service;

import edu.goncharova.domain.TaxiType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.services.RideOrderService;
import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RideOrderServiceTest {
    @BeforeClass
    public static void changeDatabaseConnector(){
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void findAll_ReturnsAllTaxiTypes() throws SQLException, DAOException {
        List<TaxiType> taxiTypes= TableCreator.initTaxiTypeTable();
        assertEquals(taxiTypes, RideOrderService.getRideOrderService().getAllTaxiTypes());
        TableCleaner.cleanTaxiTypeTable();
    }
}
