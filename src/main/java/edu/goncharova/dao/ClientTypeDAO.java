package edu.goncharova.dao;

import edu.goncharova.domain.ClientType;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.transactions.ConnectionWrapper;
import edu.goncharova.transactions.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientTypeDAO {
    private final static Logger LOGGER = LogManager.getLogger(ClientTypeDAO.class);
    private final static String SQL_FIND_ALL = "select * from clienttype";
    private final static String SQL_SELECT_DISCOUNT_BY_MONEY_SPENT = "select max(discount) from clienttype where moneyspent<=?";
    private final static String SQL_INSERT_CLIENTTYPE = "insert into clienttype(discount,name,moneyspent) values(?,?,?)";
    private final static String SQL_UPDATE_CLIENTTYPE = "update clienttype set moneyspent=?, name=?, discount=? where clienttypeid=?";

    public int findDiscountByMoneySpent(double moneySpent) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_SELECT_DISCOUNT_BY_MONEY_SPENT);
                statement.setDouble(1, moneySpent);
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("max(discount)");
                }
                return 0;

            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public List<ClientType> findAll() throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(SQL_FIND_ALL);
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                List<ClientType> result = new ArrayList<>();
                while (resultSet.next()) {
                    ClientType clientType = new ClientType(resultSet.getInt("discount"), resultSet.getString("name"),
                            resultSet.getDouble("moneyspent"));
                    clientType.setClientTypeId(resultSet.getInt("clienttypeid"));
                    if (clientType.getName().equals("nodiscount")) continue;
                    result.add(clientType);
                }
                return result;
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public boolean create(ClientType entity) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            try (PreparedStatement statement = con.preparedStatement(SQL_INSERT_CLIENTTYPE)) {
                statement.setInt(1, entity.getDiscount());
                statement.setString(2, entity.getName());
                statement.setDouble(3, entity.getMoneySpent());
                LOGGER.debug("Statement to execute {}", statement.toString());
                return statement.execute();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public boolean update(ClientType entity) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            try (PreparedStatement statement = con.preparedStatement(SQL_UPDATE_CLIENTTYPE)) {
                statement.setDouble(1, entity.getMoneySpent());
                statement.setString(2, entity.getName());
                statement.setInt(3, entity.getDiscount());
                statement.setInt(4, entity.getClientTypeId());
                LOGGER.debug("Statement to execute {}", statement.toString());
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }
}
