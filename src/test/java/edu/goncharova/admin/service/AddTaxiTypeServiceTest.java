package edu.goncharova.admin.service;

import edu.goncharova.dao.DAOFactory;
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

public class AddTaxiTypeServiceTest {
    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void addTaxiType_AddsTaxiType() throws DAOException, SQLException {
        List<TaxiType> allTaxiTypes = TableCreator.initTaxiTypeTable();
        TaxiType taxiType = new TaxiType(3.0, "cab");
        taxiType.setTaxiTypeId(allTaxiTypes.size() + 1);
        allTaxiTypes.add(taxiType);
        AddTaxiTypeService.getAddTaxiTypeService().addTaxiType(taxiType);
        assertEquals(allTaxiTypes, DAOFactory.getInstance().getTaxiTypeDAO().findAll());
        TableCleaner.cleanTaxiTypeTable();
    }
}
