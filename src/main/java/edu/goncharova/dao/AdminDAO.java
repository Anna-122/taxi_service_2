package edu.goncharova.dao;

import edu.goncharova.domain.Admin;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.transactions.ConnectionWrapper;
import edu.goncharova.transactions.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    AdminDAO() {

    }

    private final static Logger LOGGER = LogManager.getLogger(AdminDAO.class);

    public Admin findByEmail(String email) throws DAOException {
        final String sql = "SELECT * FROM admin WHERE email = ?";
        try {
            ConnectionWrapper con = TransactionManager.getConnection();
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = con.preparedStatement(sql);
                statement.setString(1, email);
                LOGGER.debug("Statement to execute {}", statement.toString());
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    Admin admin = new Admin(email,
                            resultSet.getString("password"), resultSet.getString("name"),
                            resultSet.getString("surname"));
                    admin.setAdminId(resultSet.getInt("adminid"));

                    return admin;
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DAOException(e.getMessage());
            } finally {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                con.close();
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
