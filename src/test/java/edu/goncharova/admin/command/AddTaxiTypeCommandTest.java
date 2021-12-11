package edu.goncharova.admin.command;

import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.dao.DAOFactory;
import edu.goncharova.domain.TaxiType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;

public class AddTaxiTypeCommandTest {
    private static List<TaxiType> taxiTypeList;

    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @BeforeClass
    public static void initDatabase() throws SQLException {
        taxiTypeList = TableCreator.initTaxiTypeTable();
    }

    @AfterClass
    public static void cleanDatabase() throws SQLException {
        TableCleaner.cleanTaxiTypeTable();
    }

    @Test
    public void execute_AddsTaxiType_WhenRightData() throws ServletException, IOException, DAOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        double fare = 3.0;
        String name = "new";
        TaxiType taxiType = new TaxiType(fare, name);
        taxiType.setTaxiTypeId(taxiTypeList.size() + 1);
        taxiTypeList.add(taxiType);
        when(httpServletRequest.getParameter("fare")).thenReturn(String.valueOf(fare));
        when(httpServletRequest.getParameter("name")).thenReturn(name);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("controller?command=taxitypes")).thenReturn(requestDispatcher);
        Command command = CommandFactory.getInstance().getCommand(CommandFactory.ADD_TAXI_TYPE);
        command.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessage", "TaxiType added");
        Assert.assertEquals(taxiTypeList, DAOFactory.getInstance().getTaxiTypeDAO().findAll());
    }

    @Test
    public void execute_GivesError_WhenBadNumbers() throws ServletException, IOException, DAOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(httpServletRequest.getParameter("fare")).thenReturn("not a number");
        when(httpServletRequest.getParameter("name")).thenReturn("name");
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("controller?command=taxitypes")).thenReturn(requestDispatcher);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.ADD_TAXI_TYPE + ".jsp")).thenReturn(requestDispatcher);
        Command command = CommandFactory.getInstance().getCommand(CommandFactory.ADD_TAXI_TYPE);
        command.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessage", "Fare should be a number");
    }

    @Test
    public void execute_GivesError_WhenNegativeNumbers() throws ServletException, IOException, DAOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(httpServletRequest.getParameter("fare")).thenReturn("-1");
        when(httpServletRequest.getParameter("name")).thenReturn("name");
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("controller?command=taxitypes")).thenReturn(requestDispatcher);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.ADD_TAXI_TYPE + ".jsp")).thenReturn(requestDispatcher);
        Command command = CommandFactory.getInstance().getCommand(CommandFactory.ADD_TAXI_TYPE);
        command.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessage", "Fare should be a positive number");
    }

    @Test
    public void execute_GivesError_WhenEmptyName() throws ServletException, IOException, DAOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(httpServletRequest.getParameter("fare")).thenReturn("1");
        when(httpServletRequest.getParameter("name")).thenReturn(null);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("controller?command=taxitypes")).thenReturn(requestDispatcher);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.ADD_TAXI_TYPE + ".jsp")).thenReturn(requestDispatcher);
        Command command = CommandFactory.getInstance().getCommand(CommandFactory.ADD_TAXI_TYPE);
        command.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessage", "You should fill all the fields");
    }

}
