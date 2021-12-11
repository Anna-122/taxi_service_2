package edu.goncharova.dao;

import edu.goncharova.domain.*;
import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RideDAOTest {
    private static List<Ride> allRidesList;
    private static List<Ride> firstClientRides;


    @Before
    public void initTable() throws SQLException {
        List<User> allUsersList = TableCreator.initUserTable();
        List<Client> allClientsList = TableCreator.initClientTable();
        List<Driver> allDriversList = TableCreator.initDriverTable();
        List<TaxiType> allTaxiTypesList = TableCreator.initTaxiTypeTable();
        List<Taxi> allTaxiesList = TableCreator.initTaxiTable();
        allRidesList = TableCreator.initRideTable();
        firstClientRides = allRidesList.subList(0, allRidesList.size() - 2);
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @After
    public void dropTable() throws SQLException {
        TableCleaner.cleanRideTable();
        TableCleaner.cleanTaxiTable();
        TableCleaner.cleanTaxiTypeTable();
        TableCleaner.cleanDriverTable();
        TableCleaner.cleanClientTable();
        TableCleaner.cleanUserTable();
    }
    @Ignore
    @Test
    public void create_AddsRideToDatabase() throws DAOException {
        final Ride RIDE = new Ride(1, 3, 1, 38.0, 27.0,
                new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        RIDE.setRideId(allRidesList.size() + 1);
        RideDAO rideDAO = DAOFactory.getInstance().getRideDAO();
        rideDAO.create(RIDE);
        List<Ride> rides = new ArrayList<>();
        rides.add(RIDE);
        Client client = new Client(3);
        client.setClientId(3);
        assertEquals(rides, rideDAO.findRidesForClient(client, 0, 1));
    }

    @Ignore
    @Test
    public void getMoneySpentForClient() throws DAOException {
        final double MONEY_SPENT = firstClientRides.stream().mapToDouble(Ride::getCost).sum();
        RideDAO rideDAO = DAOFactory.getInstance().getRideDAO();
        Client client = new Client(1);
        client.setClientId(1);
        assertEquals(MONEY_SPENT, rideDAO.getMoneySpentForClient(client), 0.01);
    }

    @Ignore
    @Test
    public void getMoneySpentForClient_ReturnsZero_WhenNoRides() throws DAOException {
        final double MONEY_SPENT = 0.0;
        RideDAO rideDAO = DAOFactory.getInstance().getRideDAO();
        Client client = new Client(3);
        client.setClientId(3);
        assertEquals(MONEY_SPENT, rideDAO.getMoneySpentForClient(client), 0.01);
    }

    @Ignore
    @Test
    public void findRidesForClient_ReturnsAllRides() throws DAOException {
        RideDAO rideDAO = DAOFactory.getInstance().getRideDAO();
        Client client = new Client(1);
        client.setClientId(1);
        assertEquals(firstClientRides, rideDAO.findRidesForClient(client, 0, 3));
    }

    @Ignore
    @Test
    public void findRidesForClient_SkipsRides() throws DAOException {
        RideDAO rideDAO = DAOFactory.getInstance().getRideDAO();
        Client client = new Client(1);
        client.setClientId(1);
        assertEquals(firstClientRides.subList(1, 3), rideDAO.findRidesForClient(client, 1, 3));
    }

    @Ignore
    @Test
    public void findRidesForClient_LimitsAmount() throws DAOException {
        RideDAO rideDAO = DAOFactory.getInstance().getRideDAO();
        Client client = new Client(1);
        client.setClientId(1);
        assertEquals(firstClientRides.subList(0, 2), rideDAO.findRidesForClient(client, 0, 2));
    }

    @Ignore
    @Test
    public void findSize_GivesCorrectSize_WhenNonZero() throws DAOException {
        RideDAO rideDAO = DAOFactory.getInstance().getRideDAO();
        Client client = new Client(1);
        client.setClientId(1);
        assertEquals(firstClientRides.size(), rideDAO.findSize(client));
    }

    @Ignore
    @Test
    public void findRidesForClient_ReturnsNullWhenNoRides() throws DAOException {
        RideDAO rideDAO = DAOFactory.getInstance().getRideDAO();
        Client client = new Client(4);
        client.setClientId(4);
        assertEquals(new ArrayList<>(), rideDAO.findRidesForClient(client, 0, 1));
    }

    @Ignore
    @Test
    public void findSize_GivesZero_WhenNoRidesForClient() throws DAOException {
        RideDAO rideDAO = DAOFactory.getInstance().getRideDAO();
        Client client = new Client(4);
        client.setClientId(4);
        assertEquals(0, rideDAO.findSize(client));
    }
}
