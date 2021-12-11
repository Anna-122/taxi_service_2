package edu.goncharova.service;


import edu.goncharova.domain.Client;
import edu.goncharova.domain.Taxi;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.domain.User;
import edu.goncharova.exceptions.CostCalculationException;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.services.CostCalculationService;
import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CostCalculationServiceTest {

    @BeforeClass
    public static void changeDatabaseConnector(){
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }
    @Test
    public void getClient_ReturnsClient_WhenRightData() throws SQLException, DAOException {
        List<User> userList = TableCreator.initUserTable();
        List<Client> clientList = TableCreator.initClientTable();
        assertEquals(clientList.get(0), CostCalculationService.getCostCalculationService().getClient(userList.get(0).getUserId()));
        TableCleaner.cleanClientTable();
        TableCleaner.cleanUserTable();
    }
    @Test
    public void getClient_ReturnsNull_WhenBadData() throws SQLException, DAOException {
        List<User> userList = TableCreator.initUserTable();
        List<Client> clientList = TableCreator.initClientTable();
        assertNull(CostCalculationService.getCostCalculationService().getClient(userList.size()+2));
        TableCleaner.cleanClientTable();
        TableCleaner.cleanUserTable();
    }

    @Test
    public void getFare_GetsFareRight() throws SQLException, CostCalculationException, DAOException {
        List<TaxiType> taxiTypes = TableCreator.initTaxiTypeTable();
        assertEquals(taxiTypes.get(0).getFare(),
                CostCalculationService.getCostCalculationService().getFare(taxiTypes.get(0).getTaxiTypeId()),0.01);
        TableCleaner.cleanTaxiTypeTable();
    }

    @Test
    public void getDriveCost_CalculatesRight_WithoutDiscount() throws SQLException, CostCalculationException, DAOException {
        TableCreator.initUserTable();
        List<TaxiType> taxiTypes = TableCreator.initTaxiTypeTable();
        TableCreator.initDriverTable();
        List<Taxi> taxies = TableCreator.initTaxiTable();
        assertEquals(20,CostCalculationService.getCostCalculationService().getDriveCost(taxies.get(0),10,0),
                0.01);
        TableCleaner.cleanTaxiTable();
        TableCleaner.cleanTaxiTypeTable();
        TableCleaner.cleanDriverTable();
        TableCleaner.cleanUserTable();
    }
    @Test
    public void getDriveCost_CalculatesRight_WithDiscount() throws SQLException, CostCalculationException, DAOException {
        TableCreator.initUserTable();
        List<TaxiType> taxiTypes = TableCreator.initTaxiTypeTable();
        TableCreator.initDriverTable();
        List<Taxi> taxies = TableCreator.initTaxiTable();
        assertEquals(18,CostCalculationService.getCostCalculationService().getDriveCost(taxies.get(0),10,10),
                0.01);
        TableCleaner.cleanTaxiTable();
        TableCleaner.cleanTaxiTypeTable();
        TableCleaner.cleanDriverTable();
        TableCleaner.cleanUserTable();
    }
}
