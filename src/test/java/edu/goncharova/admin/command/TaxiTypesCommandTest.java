package edu.goncharova.admin.command;

import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.domain.TaxiType;
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

public class TaxiTypesCommandTest {
    private static List<TaxiType> taxiTypeList;
    @BeforeClass
    public static void changeDatabaseConnector(){
        TransactionManager.setConnectionPool(TestConnectionPool.getInstance());
    }
    @BeforeClass
    public static void initDatabase() throws SQLException {
        taxiTypeList = TableCreator.initTaxiTypeTable();
    }

    @Test
    public void execute_GivesAllClientTypes() throws ServletException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(httpServletRequest.getRequestDispatcher(CommandFactory.TAXI_TYPES +".jsp")).thenReturn(requestDispatcher);
        Command taxiTypesCommand = CommandFactory.getInstance().getCommand(CommandFactory.TAXI_TYPES);
        taxiTypesCommand.execute(httpServletRequest,httpServletResponse);
        verify(httpServletRequest).setAttribute("taxitypes", taxiTypeList);
    }
    @AfterClass
    public static void cleanDatabase() throws SQLException {
        TableCleaner.cleanTaxiTypeTable();
    }
}
