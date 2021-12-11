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


public class UpdateTaxiTypeServiceTest {


    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @Test
    public void updateTaxiType_UpdatesTaxiType() throws DAOException, SQLException {
        List<TaxiType> taxiTypeList = TableCreator.initTaxiTypeTable();
        TaxiType taxiType = new TaxiType(3, "cab");
        taxiType.setTaxiTypeId(taxiTypeList.size());
        taxiTypeList.set(taxiTypeList.size() - 1, taxiType);
        UpdateTaxiTypeService.getTaxiTypeService().updateTaxiType(taxiType);
        assertEquals(taxiTypeList, DAOFactory.getInstance().getTaxiTypeDAO().findAll());
        TableCleaner.cleanTaxiTypeTable();
    }
}
