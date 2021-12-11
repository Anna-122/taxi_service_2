package edu.goncharova.admin.command;

import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.dao.DAOFactory;
import edu.goncharova.domain.ClientType;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddClientTypeCommandTest {
    private static List<ClientType> clientTypeList;

    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @BeforeClass
    public static void initDatabase() throws SQLException {
        clientTypeList = TableCreator.initClientTypeTable();
    }

    @AfterClass
    public static void cleanDatabase() throws SQLException {
        TableCleaner.cleanClientTypeTable();
    }

    @Test
    public void execute_AddsClientType_WhenRightData() throws ServletException, IOException, DAOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        double moneyspent = 100000;
        String name = "new";
        int discount = 50;
        ClientType clientType = new ClientType(discount, name, moneyspent);
        clientType.setClientTypeId(clientTypeList.size() + 1);
        clientTypeList.add(clientType);
        when(httpServletRequest.getParameter("moneyspent")).thenReturn(String.valueOf(moneyspent));
        when(httpServletRequest.getParameter("name")).thenReturn(name);
        when(httpServletRequest.getParameter("discount")).thenReturn(String.valueOf(discount));
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("controller?command=clienttypes")).thenReturn(requestDispatcher);
        Command command = CommandFactory.getInstance().getCommand(CommandFactory.ADD_CLIENT_TYPE);
        command.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessage", "ClientType added");
        Assert.assertEquals(clientTypeList.subList(1, clientTypeList.size()), DAOFactory.getInstance().getClientTypeDAO().findAll());
    }

    @Test
    public void execute_GivesError_WhenBadNumbers() throws ServletException, IOException, DAOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(httpServletRequest.getParameter("moneyspent")).thenReturn("not a number");
        when(httpServletRequest.getParameter("name")).thenReturn("name");
        when(httpServletRequest.getParameter("discount")).thenReturn(String.valueOf(10));
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("controller?command=clienttypes")).thenReturn(requestDispatcher);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.ADD_CLIENT_TYPE + ".jsp")).thenReturn(requestDispatcher);
        Command command = CommandFactory.getInstance().getCommand(CommandFactory.ADD_CLIENT_TYPE);
        command.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessage", "Moneyspent and discount should be a number");
    }

    @Test
    public void execute_GivesError_WhenNegativeNumbers() throws ServletException, IOException, DAOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(httpServletRequest.getParameter("moneyspent")).thenReturn("10");
        when(httpServletRequest.getParameter("name")).thenReturn("name");
        when(httpServletRequest.getParameter("discount")).thenReturn("-1");
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("controller?command=clienttypes")).thenReturn(requestDispatcher);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.ADD_CLIENT_TYPE + ".jsp")).thenReturn(requestDispatcher);
        Command command = CommandFactory.getInstance().getCommand(CommandFactory.ADD_CLIENT_TYPE);
        command.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessage", "Discount and money spent should be positive");
    }

    @Test
    public void execute_GivesError_WhenEmptyName() throws ServletException, IOException, DAOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(httpServletRequest.getParameter("moneyspent")).thenReturn("10");
        when(httpServletRequest.getParameter("name")).thenReturn(null);
        when(httpServletRequest.getParameter("discount")).thenReturn("1");
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("controller?command=clienttypes")).thenReturn(requestDispatcher);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.ADD_CLIENT_TYPE + ".jsp")).thenReturn(requestDispatcher);
        Command command = CommandFactory.getInstance().getCommand(CommandFactory.ADD_CLIENT_TYPE);
        command.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessage", "You should fill all the fields");
    }

    @Test
    public void execute_GivesError_WhenDiscountGreaterThan100() throws ServletException, IOException, DAOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(httpServletRequest.getParameter("moneyspent")).thenReturn("10");
        when(httpServletRequest.getParameter("name")).thenReturn(null);
        when(httpServletRequest.getParameter("discount")).thenReturn("105");
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("controller?command=clienttypes")).thenReturn(requestDispatcher);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.ADD_CLIENT_TYPE + ".jsp")).thenReturn(requestDispatcher);
        Command command = CommandFactory.getInstance().getCommand(CommandFactory.ADD_CLIENT_TYPE);
        command.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessage", "Discount should be smaller than 100");
    }

}
