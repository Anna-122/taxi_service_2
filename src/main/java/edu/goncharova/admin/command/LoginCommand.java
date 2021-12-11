package edu.goncharova.admin.command;

import edu.goncharova.command.Command;
import edu.goncharova.command.CommandFactory;
import edu.goncharova.domain.Admin;
import edu.goncharova.exceptions.DAOException;
import edu.goncharova.admin.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginCommand implements Command {
    private final static Logger LOGGER = LogManager.getLogger(edu.goncharova.command.LoginCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("admin") != null) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email == null) {
            request.setAttribute("errorMessageLogin", "You should fill email");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            LOGGER.info("Empty email");
        }
        if (password == null) {
            request.setAttribute("errorMessageLogin", "You should fill password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            LOGGER.info("Empty password");
        }
        LoginService loginService = LoginService.getLoginService();
        try {
            Admin admin = loginService.getAdmin(email, password);
            if (admin != null) {
                LOGGER.info("User {} logged into admin.", email);
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                LOGGER.info("User {} couldn't log into admin.", email);
                request.setAttribute("errorMessageLogin", "Login or password incorrect");
                request.getRequestDispatcher(CommandFactory.LOGIN + ".jsp").forward(request, response);
            }
        } catch (DAOException | SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

