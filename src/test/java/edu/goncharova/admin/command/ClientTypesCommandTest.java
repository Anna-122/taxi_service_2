package edu.goncharova.admin.command;

import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.domain.ClientType;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;

public class ClientTypesCommandTest {
    private static List<ClientType> clientTypeList;
    @BeforeClass
    public static void changeDatabaseConnector(){
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }
    @BeforeClass
    public static void initDatabase() throws SQLException {
        clientTypeList = TableCreator.initClientTypeTable();
    }

    @Test
    public void execute_GivesAllClientTypes() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.CLIENT_TYPES+".jsp")).thenReturn(requestDispatcher);
        Command clientTypesCommand = CommandFactory.getInstance().getCommand(CommandFactory.CLIENT_TYPES);
        clientTypesCommand.execute(httpServletRequest,httpServletResponse);
        verify(httpServletRequest).setAttribute("clienttypes",clientTypeList.subList(1,clientTypeList.size()));
    }
    @AfterClass
    public static void cleanDatabase() throws SQLException {
        TableCleaner.cleanClientTypeTable();
    }
}
