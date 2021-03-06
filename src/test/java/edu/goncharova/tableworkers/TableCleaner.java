package edu.goncharova.tableworkers;

import edu.goncharova.transactions.TestConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TableCleaner {

    public static void cleanDriverTable() throws SQLException {
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP = "DROP TABLE driver";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
    }
    public static void cleanUserTable() throws SQLException {
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP = "DROP TABLE user";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
    }
    public static void cleanAdminTable() throws SQLException{
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP = "DROP TABLE admin";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
    }
    public static void cleanClientTable() throws SQLException{
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP = "DROP TABLE client";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
    }
    public static void cleanClientTypeTable() throws SQLException {
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP = "DROP TABLE clienttype";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
    }
    public static void cleanTaxiTypeTable() throws SQLException{
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP = "DROP TABLE taxitype";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
    }
    public static void cleanTaxiTable() throws SQLException {
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP = "DROP TABLE taxi";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
    }
    public static void cleanRideTable() throws SQLException{
        Connection connection = TestConnectionPool.getInstance().getConnection();
        String SQL_DROP = "DROP TABLE ride";
        PreparedStatement ps = connection.prepareStatement(SQL_DROP);
        ps.execute();
    }
    /*
        initUserTable();
        initDriverTable();
        initClientTable();
        initClientTypeTable();
        initTaxiTypeTable();
        initTaxiTable();
        initRideTable();
        initAdminTable();

        initAdminTable();
        initRideTable();
        initTaxiTable();
        initTaxiTypeTable();
        initClientTypeTable();
        initClientTable();
        initDriverTable();
        initUserTable();
     */
}
