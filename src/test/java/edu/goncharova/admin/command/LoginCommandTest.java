package edu.goncharova.admin.command;

import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.domain.Admin;
import edu.goncharova.tableworkers.TableCleaner;
import edu.goncharova.tableworkers.TableCreator;
import edu.goncharova.transactions.TestConnectionPool;
import edu.goncharova.transactions.TransactionManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;


public class LoginCommandTest {
    private static List<Admin> adminList;

    @BeforeClass
    public static void changeDatabaseConnector() {
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }

    @BeforeClass
    public static void initDatabase() throws SQLException {
        adminList = TableCreator.initAdminTable();
    }

    @Test
    public void execute_FailsLogin_WhenBadData() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("admin")).thenReturn(null);
        when(httpServletRequest.getParameter("email")).thenReturn("bademail@gmail.com");
        when(httpServletRequest.getParameter("password")).thenReturn("password");
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.LOGIN + ".jsp")).thenReturn(requestDispatcher);
        Command loginCommand = CommandFactory.getInstance().getCommand("adminLogin");
        loginCommand.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessageLogin", "Login or password incorrect");
    }

    @Test
    public void execute_FailsLogin_WhenEmailIsNull() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("admin")).thenReturn(null);
        when(httpServletRequest.getParameter("email")).thenReturn(null);
        when(httpServletRequest.getParameter("password")).thenReturn("password");
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.LOGIN + ".jsp")).thenReturn(requestDispatcher);
        Command loginCommand = CommandFactory.getInstance().getCommand(CommandFactory.ADMIN_LOGIN);
        loginCommand.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessageLogin", "You should fill email");
    }

    @Test
    public void execute_FailsLogin_WhenPasswordIsNull() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("admin")).thenReturn(null);
        when(httpServletRequest.getParameter("email")).thenReturn("email@gmail.com");
        when(httpServletRequest.getParameter("password")).thenReturn(null);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.LOGIN + ".jsp")).thenReturn(requestDispatcher);
        Command loginCommand = CommandFactory.getInstance().getCommand(CommandFactory.ADMIN_LOGIN);
        loginCommand.execute(httpServletRequest, httpServletResponse);
        verify(httpServletRequest).setAttribute("errorMessageLogin", "You should fill password");
    }

    @Test
    public void execute_LogsIn_WhenGoodData() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("admin")).thenReturn(null);
        when(httpServletRequest.getParameter("email")).thenReturn(adminList.get(0).getEmail());
        when(httpServletRequest.getParameter("password")).thenReturn(adminList.get(0).getPassword());
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);
        Command loginCommand = CommandFactory.getInstance().getCommand(CommandFactory.ADMIN_LOGIN);
        loginCommand.execute(httpServletRequest, httpServletResponse);
        verify(httpSession).setAttribute("admin", adminList.get(0));
    }

    @Test
    public void execute_FailsLogIn_WhenAlreadyLoggedIn() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("admin")).thenReturn(adminList.get(0));
        when(httpServletRequest.getParameter("email")).thenReturn(adminList.get(0).getEmail());
        when(httpServletRequest.getParameter("password")).thenReturn(adminList.get(0).getPassword());
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher("index.jsp")).thenReturn(requestDispatcher);
        Command loginCommand = CommandFactory.getInstance().getCommand(CommandFactory.ADMIN_LOGIN);
        loginCommand.execute(httpServletRequest, httpServletResponse);
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
    }

    @AfterClass
    public static void cleanDatabase() throws SQLException {
        TableCleaner.cleanAdminTable();
    }
}
