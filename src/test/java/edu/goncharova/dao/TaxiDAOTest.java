package edu.goncharova.dao;

import edu.goncharova.domain.Driver;
import edu.goncharova.domain.Taxi;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.domain.User;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TaxiDAOTest {
    private static List<User> allUsers;
    private static List<Driver> allDrivers;
    private static List<TaxiType> allTaxiTypes;
    private static List<Taxi> allTaxies;



    @Before
    public void initTaxiTable() throws SQLException {
        allUsers = TableCreator.initUserTable();
        allDrivers = TableCreator.initDriverTable();
        allTaxiTypes = TableCreator.initTaxiTypeTable();
        allTaxies = TableCreator.initTaxiTable();
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }
    @After
    public void dropTable() throws SQLException {
        Connection connection = TestConnectionPool.getInstance().getConnection();

        String SQL_DROP = "DROP TABLE taxi";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
        SQL_DROP = "DROP TABLE taxitype";
         ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
        String SQL_DROP_USER = "DROP TABLE user";
        ps = connection.prepareStatement(SQL_DROP_USER);
        ps.execute();
        SQL_DROP = "DROP TABLE driver";
        ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
    }


    //create
    @Test
    public void findSize_ReturnsCorrectSize() throws DAOException {
        for(int i=1;i<=allTaxiTypes.size();i++) {
            final int taxitypeid = i;
            long RIGHT_SIZE = allTaxies.stream().filter(taxi -> taxi.getTaxiTypeId()>=taxitypeid).count();
            TaxiDAO taxiDAO = DAOFactory.getInstance().getTaxiDAO();
            assertEquals(RIGHT_SIZE, taxiDAO.findNumberOfSpecifiedTaxiType(allTaxiTypes.get(taxitypeid-1)));
        }
    }

    @Test
    public void findByCarNumber_ReturnsTaxi_WhenCarNumberExists() throws DAOException {
        final Taxi TAXI = allTaxies.get(0);
        TaxiDAO taxiDAO = DAOFactory.getInstance().getTaxiDAO();
        Assert.assertEquals(TAXI,taxiDAO.findByCarNumber(TAXI.getCarNumber()));
    }
    @Test
    public void findByCarNumber_ReturnsNull_WhenThereIsNoTaxiWithSuchCarNumber() throws DAOException{
        final String carNumber = "AB0000BA";
        TaxiDAO taxiDAO = DAOFactory.getInstance().getTaxiDAO();
        assertNull(taxiDAO.findByCarNumber(carNumber));
    }
    @Test
    public void findById_ReturnsTaxi_WhenIdExists() throws DAOException {
        final Taxi TAXI = allTaxies.get(0);
        TaxiDAO taxiDAO = DAOFactory.getInstance().getTaxiDAO();
        Assert.assertEquals(TAXI,taxiDAO.findById(TAXI.getTaxiId()));
    }
    @Test
    public void findById_ReturnsNull_WhenThereIsNoTaxiWithSuchCarNumber() throws DAOException{
        final int ID = 15;
        TaxiDAO taxiDAO = DAOFactory.getInstance().getTaxiDAO();
        assertNull(taxiDAO.findById(ID));
    }
    @Test
    public void create_AddsTaxiToDatabase() throws DAOException {
        final Taxi TAXI = new Taxi(3,1,"AB1238BB");
        TAXI.setTaxiId(5);
        TaxiDAO taxiDAO = DAOFactory.getInstance().getTaxiDAO();
        taxiDAO.create(TAXI);
        Assert.assertEquals(TAXI,taxiDAO.findByCarNumber(TAXI.getCarNumber()));
    }

}
