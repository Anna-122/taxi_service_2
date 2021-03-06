package edu.goncharova.command;

import edu.goncharova.exceptions.DAOException;
import edu.goncharova.exceptions.RegistrationException;
import edu.goncharova.exceptions.TransactionException;
import edu.goncharova.services.RegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegistrationCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        LOGGER.info("User typed next values: email :{} {} {}", email, name, surname);
        if (email == null || password == null || name == null || surname == null) {
            LOGGER.info("Tried to register with empty fields.");
            req.setAttribute("errorMessage", "All fields must be filled");
            req.getRequestDispatcher(CommandFactory.REGISTRATION + ".jsp").forward(req, resp);
            return;
        }
        RegistrationService registrationService = RegistrationService.getRegistrationService();
        try {
            registrationService.register(email, password, name, surname);
            LOGGER.info("Registered new user: email :{} {} {}", email, name, surname);
            req.setAttribute("justRegistered", true);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (RegistrationException e) {
            LOGGER.error(e.getMessage());
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher(CommandFactory.REGISTRATION + ".jsp").forward(req, resp);
        } catch (SQLException | TransactionException | DAOException e) {
            LOGGER.error(e.getMessage());
        }

    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
