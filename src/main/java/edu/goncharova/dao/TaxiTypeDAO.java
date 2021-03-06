package edu.goncharova.dao;

import edu.goncharova.domain.TaxiType;
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

public class TaxiTypeDAO {
    private final static Logger LOGGER = LogManager.getLogger(TaxiTypeDAO.class);
    private final static String SQL_FIND_ALL = "select * from taxitype";
    private final static String SQL_SELECT_BY_ID = "select * from taxitype where taxitypeid=?";
    private final static String SQL_SELECT_BY_NAME = "select * from taxitype where taxitypename=?";
    private final static String SQL_INSERT_TAXITYPE = "insert into taxitype(fare,taxitypename) values (?,?)";
    private final static String SQL_UPDATE_TAXITYPE = "update taxitype set fare=?, taxitypename=? where taxitypeid=?";

    public List<TaxiType> findAll() throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement=null;
            ResultSet resultSet=null;
            try {
                statement = con.preparedStatement(SQL_FIND_ALL);
                LOGGER.debug("Statement to execute {}",statement.toString());
                resultSet = statement.executeQuery();
                List<TaxiType> result = new ArrayList<>();
                while(resultSet.next()){
                    TaxiType taxiType = new TaxiType(resultSet.getDouble("fare"),resultSet.getString("taxitypename"));
                    taxiType.setTaxiTypeId(resultSet.getInt("taxitypeid"));
                    result.add(taxiType);
                }
                return result;
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            }
            finally {
                if(resultSet!=null)resultSet.close();
                if(statement!=null)statement.close();
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public TaxiType findById(int id) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement=null;
            ResultSet resultSet=null;
            try {
                statement = con.preparedStatement(SQL_SELECT_BY_ID);
                statement.setInt(1, id);
                LOGGER.debug("Statement to execute {}",statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    TaxiType taxiType = new TaxiType(resultSet.getDouble("fare"),
                            resultSet.getString("taxitypename"));
                    taxiType.setTaxiTypeId(id);
                    return taxiType;
                }
            } catch (SQLException e){
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if(resultSet!=null)resultSet.close();
                if(statement!=null)statement.close();
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return null;
    }

    public TaxiType findByName(String name) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement=null;
            ResultSet resultSet=null;
            try {
                statement = con.preparedStatement(SQL_SELECT_BY_NAME);
                statement.setString(1, name);
                LOGGER.debug("Statement to execute {}",statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    TaxiType taxiType = new TaxiType(resultSet.getDouble("fare"),
                            name);
                    taxiType.setTaxiTypeId(resultSet.getInt("taxitypeid"));
                    return taxiType;
                }
            } catch (SQLException e){
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if(resultSet!=null)resultSet.close();
                if(statement!=null)statement.close();
                con.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return null;
    }

    public boolean create(TaxiType entity) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            try (PreparedStatement statement = con.preparedStatement(SQL_INSERT_TAXITYPE)) {
                statement.setDouble(1, entity.getFare());
                statement.setString(2, entity.getTaxiTypeName());
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

    public boolean update(TaxiType entity) throws DAOException {
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            ResultSet resultSet=null;
            try (PreparedStatement statement = con.preparedStatement(SQL_UPDATE_TAXITYPE)) {
                statement.setDouble(1, entity.getFare());
                statement.setString(2, entity.getTaxiTypeName());
                statement.setInt(3, entity.getTaxiTypeId());
                LOGGER.debug("Statement to execute {}", statement.toString());
                statement.execute();
                return false;
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
